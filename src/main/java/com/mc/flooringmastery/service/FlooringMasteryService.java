package com.mc.flooringmastery.service;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {

    List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException;

    Order addOrder(LocalDate date, Order order) throws
            FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException,
            FlooringMasteryDuplicateIdException;

    Order editOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException;
}
