package com.ip.crypto.model;

/**
 * Normalized crypto price value for the time interval.
 */
public class NormalizedCrypto {
    private String symbol;
    private String timeInterval;
    private double normalizedValue;

    public NormalizedCrypto(final String symbol, final String timeInterval, final double normalizedValue) {
        this.symbol = symbol;
        this.timeInterval = timeInterval;
        this.normalizedValue = normalizedValue;
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

    public double getNormalizedValue() {
        return this.normalizedValue;
    }

    public void setNormalizedValue(final double normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    @Override
    public String toString() {
        return "NormalizedCrypto{" +
                "symbol='" + symbol + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", normalizedValue=" + normalizedValue +
                '}';
    }
}
