package com.ip.crypto.model;

/**
 * An entity containing extremes like min/max price, oldest/newest price for one month for one crypto.
 */
public class CryptoExtremes {

    private String symbol;
    private String timeInterval;
    private double oldestPrice;
    private double newestPrice;
    private double minPrice;
    private double maxPrice;

    public CryptoExtremes() {
    }

    public CryptoExtremes(final String symbol, final String timeInterval, final double oldestPrice, final double newestPrice, final double minPrice, final double maxPrice) {
        this.symbol = symbol;
        this.timeInterval = timeInterval;
        this.oldestPrice = oldestPrice;
        this.newestPrice = newestPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getTimeInterval() {
        return this.timeInterval;
    }

    public void setTimeInterval(final String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public double getOldestPrice() {
        return this.oldestPrice;
    }

    public void setOldestPrice(final double oldestPrice) {
        this.oldestPrice = oldestPrice;
    }

    public double getNewestPrice() {
        return this.newestPrice;
    }

    public void setNewestPrice(final double newestPrice) {
        this.newestPrice = newestPrice;
    }

    public double getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(final double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(final double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "CryptoExtremes{" +
                "symbol='" + symbol + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", oldestPrice=" + oldestPrice +
                ", newestPrice=" + newestPrice +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
