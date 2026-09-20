package com.mc.flooringmastery.service;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dao.order.OrderDao;
import com.mc.flooringmastery.dao.product.ProductDao;
import com.mc.flooringmastery.dao.tax.TaxDao;
import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.dto.Product;
import com.mc.flooringmastery.dto.Tax;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    OrderDao orderDao;
    TaxDao taxDao;
    ProductDao productDao;

    public FlooringMasteryServiceImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        return orderDao.getAllOrders(date);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException {

        Order order = orderDao.getOrder(date, orderNumber);

        if (order == null) {
            throw new FlooringMasteryDataValidationException(
                    "Order does not exist."
            );
        }

        return order;
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public Order createOrder(
            LocalDate date,
            String customerName,
            String state,
            String productTypeUser,
            BigDecimal area)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException {

        validateDateFuture(date);

        int orderNumber = getOrderNum();

        return buildOrder(
                orderNumber,
                customerName,
                state,
                productTypeUser,
                area
        );
    }

    @Override
    public Order addOrder(LocalDate date, Order order) throws
            FlooringMasteryPersistenceException {
        return orderDao.addOrder(date, order);
    }

    @Override
    public Order editOrder(
            LocalDate date,
            int orderNumber,
            String customerName,
            String state,
            String productTypeUser,
            BigDecimal area) throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {

        Order existingOrder = getOrder(date, orderNumber);
        Order editedOrder = buildOrder(
                existingOrder.getOrderNumber(),
                customerName,
                state,
                productTypeUser,
                area
        );
        return orderDao.editOrder(date, editedOrder);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException {

        getOrder(date, orderNumber);

        return orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public void validateDate(LocalDate date) {
        validateDateFuture(date);
    }

    @Override
    public void validateCustomer(String customerName) {
        validateCustomerName(customerName);
    }

    @Override
    public void validateState(String state) {
        getTaxForState(state);
    }

    @Override
    public void validateProduct(String productType) {
        getProduct(productType);
    }

    @Override
    public void validateOrderArea(BigDecimal area) {
        validateArea(area);
    }

    @Override
    public List<LocalDate> getAvailableOrderDates() throws FlooringMasteryPersistenceException {
        return orderDao.getAvailableOrderDates();
    }

    // BigDecimal tax; calc = (materialCost + labourCost) * (taxrate/100)
    private BigDecimal calculateTax(
            BigDecimal materialCost,
            BigDecimal labourCost,
            BigDecimal taxRate) {

        BigDecimal divider = new BigDecimal("100");

        BigDecimal res = materialCost
                .add(labourCost)
                .multiply(taxRate)
                .divide(divider);

        return res.setScale(2, RoundingMode.HALF_EVEN);
    }

    // BigDecimal labourCost; calc = area * labourCostPerSquareFoot
    private BigDecimal calculateLabourCost(BigDecimal area, BigDecimal labourCostPerSquareFoot) {
        BigDecimal res = area.multiply(labourCostPerSquareFoot);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }

    // BigDecimal materialCost; calc = area * costPerSquareFoot
    private BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        BigDecimal res = area.multiply(costPerSquareFoot);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }

    // BigDecimal total; calc = materialCost + labourCost + tax
    private BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax) {
        BigDecimal res = materialCost.add(labourCost).add(tax);
        res = res.setScale(2, RoundingMode.HALF_EVEN);
        return res;
    }

    private Tax getTaxForState(String state) {
        List<Tax> taxes = getAllTaxes();
        for (Tax tax : taxes) {
            if (tax.getStateAbr().equalsIgnoreCase(state)) {
                return tax;
            }
        }

        throw new FlooringMasteryDataValidationException(
                "State is not available."
        );
    }

    private Product getProduct(String productType) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                return product;
            }
        }

        throw new FlooringMasteryDataValidationException(
                "Product is not available."
        );
    }

    private void validateArea(BigDecimal userArea) {
        BigDecimal minArea = new BigDecimal("100");

        if (userArea == null || userArea.compareTo(minArea) < 0) {
            throw new FlooringMasteryDataValidationException(
                    "Area must be at least 100 square feet."
            );
        }
    }

    private void validateDateFuture(LocalDate date) {
        if (date == null || !date.isAfter(LocalDate.now())) {
            throw new FlooringMasteryDataValidationException(
                    "Order date must be in the future."
            );
        }

    }

    private void validateCustomerName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new FlooringMasteryDataValidationException("Customer name cannot be empty.");
        }

        if (!name.matches("[a-zA-Z0-9., ]+")) {
            throw new FlooringMasteryDataValidationException("Customer name contains invalid characters.");
        }
    }

    private int getOrderNum() {
//        List<Order> allOrders = orderDao.getAllOrders(date);
//
//        int highestOrderNumber = 0;
//
//        for (Order order : allOrders) {
//            if (order.getOrderNumber() > highestOrderNumber) {
//                highestOrderNumber = order.getOrderNumber();
//            }
//        }
//        return highestOrderNumber + 1;
        return orderDao.getHighestOrderNumber() + 1;
    }

    private Order buildOrder(
            int orderNumber,
            String customerName,
            String state,
            String productTypeUser,
            BigDecimal area) {

        validateArea(area);
        validateCustomerName(customerName);

        Tax tax = getTaxForState(state);
        Product product = getProduct(productTypeUser);

        BigDecimal taxRate = tax.getTaxRate();
        String stateAbr = tax.getStateAbr();
        String productType = product.getProductType();
        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal labourCostPerSquareFoot = product.getLabourCostPerSquareFoot();

        BigDecimal materialCost =
                calculateMaterialCost(area, costPerSquareFoot);

        BigDecimal labourCost =
                calculateLabourCost(area, labourCostPerSquareFoot);

        BigDecimal taxCalc =
                calculateTax(materialCost, labourCost, taxRate);

        BigDecimal total =
                calculateTotal(materialCost, labourCost, taxCalc);

        return new Order(
                orderNumber,
                customerName,
                stateAbr,
                taxRate,
                productType,
                area,
                costPerSquareFoot,
                labourCostPerSquareFoot,
                materialCost,
                labourCost,
                taxCalc,
                total
        );
    }
}
