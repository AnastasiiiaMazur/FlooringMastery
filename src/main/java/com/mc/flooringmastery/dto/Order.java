package com.mc.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private int orderNumber;
    private String orderName;
    private String state;
    private LocalDate date;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal area;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;


    public int getOrderNumber() {
        return orderNumber;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }


    // equals()
    // toString()
    // hashCode()

}
