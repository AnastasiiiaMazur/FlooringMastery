package com.mc.flooringmastery.test.service.stub;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dao.order.OrderDao;
import com.mc.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoStubImpl implements OrderDao {

    private Map<Integer, Order> orders = new HashMap<>();

    private LocalDate orderDate = LocalDate.of(2026, 12, 20);

    public OrderDaoStubImpl() {
        Order order = new Order(
                10,
                "Ada Lovelace",
                "WA",
                new BigDecimal("9.25"),
                "Tile",
                new BigDecimal("200"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("700.00"),
                new BigDecimal("830.00"),
                new BigDecimal("141.53"),
                new BigDecimal("1671.53")
        );

        orders.put(order.getOrderNumber(), order);
    }

    @Override
    public List<Order> getAllOrders(LocalDate date)
            throws FlooringMasteryPersistenceException {

        if (date.equals(orderDate)) {
            return new ArrayList<>(orders.values());
        }

        return new ArrayList<>();
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {

        if (date.equals(orderDate)) {
            return orders.get(orderNumber);
        }

        return null;
    }

    @Override
    public Order addOrder(LocalDate date, Order order)
            throws FlooringMasteryPersistenceException {

        orders.put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order editOrder(LocalDate date, Order order)
            throws FlooringMasteryPersistenceException {

        orders.put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {

        return orders.remove(orderNumber);
    }

    @Override
    public int getHighestOrderNumber()
            throws FlooringMasteryPersistenceException {

        return 10;
    }

    @Override
    public List<LocalDate> getAvailableOrderDates()
            throws FlooringMasteryPersistenceException {

        return List.of(orderDate);
    }

    @Override
    public List<Integer> getOrderNumbers(LocalDate date)
            throws FlooringMasteryPersistenceException {

        if (date.equals(orderDate)) {
            return new ArrayList<>(orders.keySet());
        }

        return new ArrayList<>();
    }
}
