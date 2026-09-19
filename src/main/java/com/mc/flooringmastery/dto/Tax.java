package com.mc.flooringmastery.dto;

import java.math.BigDecimal;

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
}
