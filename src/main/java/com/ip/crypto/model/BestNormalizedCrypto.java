package com.ip.crypto.model;

import java.util.Objects;

/**
 * Encapsulates the crypto with the best normalized value for the day.
 */
public class BestNormalizedCrypto {
    private String symbol;
    private double normalizedPrice;

    public BestNormalizedCrypto(final String symbol, final double normalizedPrice) {
        this.symbol = symbol;
        this.normalizedPrice = normalizedPrice;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public double getNormalizedPrice() {
        return this.normalizedPrice;
    }

    public void setNormalizedPrice(final double normalizedPrice) {
        this.normalizedPrice = normalizedPrice;
    }

    @Override
    public String toString() {
        return "BestNormalizedCrypto{" +
                "symbol='" + symbol + '\'' +
                ", normalizedPrice=" + normalizedPrice +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BestNormalizedCrypto that = (BestNormalizedCrypto)o;

        if (Double.compare(that.normalizedPrice, normalizedPrice) != 0) return false;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = symbol != null ? symbol.hashCode() : 0;
        temp = Double.doubleToLongBits(normalizedPrice);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }
}
