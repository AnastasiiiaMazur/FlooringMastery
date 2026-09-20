package com.mc.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {

    private String state;
    private String stateAbr;
    private BigDecimal taxRate;

    public Tax(String state, String stateAbr, BigDecimal taxRate) {
        this.state = state;
        this.stateAbr = stateAbr;
        this.taxRate = taxRate;

    }
    public String getState() {
        return state;
    }

    public String getStateAbr() {
        return stateAbr;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tax)) return false;
        Tax tax = (Tax) o;
        return Objects.equals(getState(), tax.getState()) && Objects.equals(getStateAbr(), tax.getStateAbr()) && Objects.equals(getTaxRate(), tax.getTaxRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState(), getStateAbr(), getTaxRate());
    }

    @Override
    public String toString() {
        return "Tax{" +
                "state='" + state + '\'' +
                ", stateAbr='" + stateAbr + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}
