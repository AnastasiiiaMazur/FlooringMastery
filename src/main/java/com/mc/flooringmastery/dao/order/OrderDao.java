package com.mc.flooringmastery.dao.order;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {

    List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException;

    Order addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;

    Order editOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException;

    int getHighestOrderNumber() throws FlooringMasteryPersistenceException;

    List<LocalDate> getAvailableOrderDates() throws FlooringMasteryPersistenceException;

    List<Integer> getOrderNumbers(LocalDate date) throws FlooringMasteryPersistenceException;
}
