package com.mc.flooringmastery.dto;

import java.math.BigDecimal;

public class Tax {

    private String state;
    private String stateAbr;
    private BigDecimal taxRate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbr() {
        return stateAbr;
    }

    public void setStateAbr(String stateAbr) {
        this.stateAbr = stateAbr;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
