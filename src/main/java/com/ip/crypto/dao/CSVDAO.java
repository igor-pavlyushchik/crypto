package com.ip.crypto.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ip.crypto.model.BestNormalizedCrypto;
import com.ip.crypto.model.CryptoExtremes;
import com.ip.crypto.model.NormalizedCrypto;

/**
 * DAO to persist and get crypto information.
 * TODO: move queries to properties and introduce component CryptoQueries.
 */
@Repository
public class CSVDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public CSVDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate);
        this.insert.setTableName("CRYPTO_EXTREMES");
    }

    public void batchInsert(final List<Object[]> objects) {
        jdbcTemplate.batchUpdate("INSERT INTO CRYPTO_ENTRIES(timestamp, symbol, price) VALUES (?,?,?)", objects);
    }

    public void insertExtremes(final CryptoExtremes cryptoExtremes) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("symbol", cryptoExtremes.getSymbol());
        paramMap.put("time_interval", cryptoExtremes.getTimeInterval());
        paramMap.put("old_price", cryptoExtremes.getOldestPrice());
        paramMap.put("new_price", cryptoExtremes.getNewestPrice());
        paramMap.put("min_price", cryptoExtremes.getMinPrice());
        paramMap.put("max_price", cryptoExtremes.getMaxPrice());
        insert.execute(paramMap);
    }

    public List<NormalizedCrypto> selectNormalizedCryptos() {
        String sql = "SELECT symbol, time_interval, (max_price - min_price)/min_price as normalized_value FROM CRYPTO_EXTREMES ORDER BY (max_price - min_price)/min_price DESC";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new NormalizedCrypto(
                                rs.getString("symbol"),
                                rs.getString("time_interval"),
                                rs.getDouble("normalized_value")
                        )
        );
    }

    public List<CryptoExtremes> selectExtremes(final String symbol) {
        String sql = "SELECT symbol, time_interval, old_price, new_price, min_price, max_price FROM CRYPTO_EXTREMES where symbol = ?";
        return jdbcTemplate.query(
                sql,new Object[]{symbol},
                (rs, rowNum) ->
                        new CryptoExtremes(
                                rs.getString("symbol"),
                                rs.getString("time_interval"),
                                rs.getDouble("old_price"),
                                rs.getDouble("new_price"),
                                rs.getDouble("min_price"),
                                rs.getDouble("max_price")
                        )
        );
    }

    public BestNormalizedCrypto selectBestNormalizedCrypto(final LocalDate localDate) {
        String sql = "SELECT symbol, (MAX(price)- MIN(price))/MIN(price) AS normalized_price FROM CRYPTO_ENTRIES WHERE timestamp::date = ? GROUP BY symbol ORDER BY normalized_price DESC LIMIT 1";
        return jdbcTemplate.queryForObject(
                sql,new Object[]{localDate},
                (rs, rowNum) ->
                        new BestNormalizedCrypto(
                                rs.getString("symbol"),
                                rs.getDouble("normalized_price")
                        )
        );
    }
}
