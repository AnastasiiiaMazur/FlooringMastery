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

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public void setLabourCostPerSquareFoot(BigDecimal labourCostPerSquareFoot) {
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    //OrderNumber, CustomerName, State, TaxRate, ProductType, Area, CostPerSquareFoot,
    // LaborCostPerSquareFoot, MaterialCost, LaborCost, Tax, Total
    @Override
    public String toString() {
        return orderNumber +
                "::" + orderName +
                "::" + state +
                "::" + taxRate +
                "::" + productType +
                "::" + area +
                "::" + costPerSquareFoot +
                "::" + labourCostPerSquareFoot +
                "::" + materialCost +
                "::" + labourCost +
                "::" + tax +
                "::" + total;
    }

// equals()
    // toString()
    // hashCode()

}
