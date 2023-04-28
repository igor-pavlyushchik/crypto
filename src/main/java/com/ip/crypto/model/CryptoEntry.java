package com.ip.crypto.model;

/**
 * An instance encapsulating price of some crypto for one moment in time.
 */
public class CryptoEntry {
    private long timestamp;
    private String symbol;
    private double price;


    public CryptoEntry(final long timestamp, final String symbol, final double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CryptoEntry{" +
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
