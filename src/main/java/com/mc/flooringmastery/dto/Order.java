package com.mc.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private int orderNumber; // no setter
    private String orderName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal area;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;


    public Order(
            int orderNumber,
            String orderName,
            String state,
            BigDecimal taxRate,
            String productType,
            BigDecimal area,
            BigDecimal costPerSquareFoot,
            BigDecimal labourCostPerSquareFoot,
            BigDecimal materialCost,
            BigDecimal labourCost,
            BigDecimal tax,
            BigDecimal total
    ) {
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
        this.materialCost = materialCost;
        this.labourCost = labourCost;
        this.tax = tax;
        this.total = total;
    }

    public int getOrderNumber() {
        return orderNumber;
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

    public BigDecimal getTaxRate() {
        return taxRate;
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

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
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

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
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
