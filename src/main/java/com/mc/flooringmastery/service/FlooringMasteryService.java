package com.mc.flooringmastery.service;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.dto.Product;
import com.mc.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {

    List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException;

    List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException;

    List<Product> getAllProducts() throws FlooringMasteryPersistenceException;

    Order createOrder(
            LocalDate date,
            String customerName,
            String state,
            String productType,
            BigDecimal area
    ) throws FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException;

    Order addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;

    Order editOrder(LocalDate date,
                    int orderNumber,
                    String customerName,
                    String state,
                    String productTypeUser,
                    BigDecimal area)
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException;

    Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException;

    void validateDate(LocalDate date)
            throws FlooringMasteryDataValidationException;

    void validateCustomer(String customerName)
            throws FlooringMasteryDataValidationException;

    void validateState(String state)
            throws FlooringMasteryDataValidationException;

    void validateProduct(String productType)
            throws FlooringMasteryDataValidationException;

    void validateOrderArea(BigDecimal area)
            throws FlooringMasteryDataValidationException;

    List<LocalDate> getAvailableOrderDates() throws FlooringMasteryPersistenceException;

    List<Integer> getAvailableOrdersNum(LocalDate date) throws FlooringMasteryPersistenceException;

    Order createEditedOrder(int orderNumber, String customerName, String state, String productTypeUser, BigDecimal area);
}