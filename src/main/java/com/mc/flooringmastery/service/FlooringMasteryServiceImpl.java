package com.mc.flooringmastery.service;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dao.order.OrderDao;
import com.mc.flooringmastery.dao.product.ProductDao;
import com.mc.flooringmastery.dao.tax.TaxDao;
import com.mc.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    OrderDao orderDao;
    TaxDao taxDao;
    ProductDao productDao;

    FlooringMasteryServiceImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    //    private int orderNumber; unique
    //    private String orderName; user
    //    private String state; user + check with file
    //    private LocalDate date; user + check with file
    //    private BigDecimal taxRate; file
    //    private String productType; user + check with file
    //    private BigDecimal costPerSquareFoot; file
    //    private BigDecimal labourCostPerSquareFoot; file
    //    private BigDecimal materialCost; calc = area * costPerSquareFoot
    //    private BigDecimal area; user + min 100
    //    private BigDecimal labourCost; calc = area * labourCostPerSquareFoot
    //    private BigDecimal tax; calc = (materialCost + labourCost) * (taxrate/100)
    //    private BigDecimal total; calc = materialCost + labourCost + tax


    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        return List.of();
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override
    public Order addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException, FlooringMasteryDuplicateIdException {
        return null;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException {
        return null;
    }

    //    private BigDecimal tax; calc = (materialCost + labourCost) * (taxrate/100)
    private BigDecimal calculateTax(BigDecimal materialCost, BigDecimal labourCost, BigDecimal taxRate) {
        BigDecimal divider = new BigDecimal("100");
        BigDecimal res = (materialCost.add(labourCost)).multiply((taxRate.divide(divider, RoundingMode.HALF_EVEN))) ;
        return res;
    }

    //    private BigDecimal labourCost; calc = area * labourCostPerSquareFoot
    private BigDecimal calculateLabourCost(BigDecimal area, BigDecimal labourCostPerSquareFoot) {
        BigDecimal res = area.multiply(labourCostPerSquareFoot);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }

    //    private BigDecimal materialCost; calc = area * costPerSquareFoot
    private BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        BigDecimal res = area.multiply(costPerSquareFoot);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }

    //    private BigDecimal total; calc = materialCost + labourCost + tax
    private BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax) {
        BigDecimal res = materialCost.add(labourCost).add(tax);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }
}
