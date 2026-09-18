package com.mc.flooringmastery.dao.order;

import com.mc.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {

    List<Order> getAllOrders(LocalDate date);

    Order getOrder(LocalDate date, int orderNumber);

    Order addOrder(LocalDate date, Order order);

    Order editOrder(LocalDate date, Order order);

    Order removeOrder(LocalDate date, int orderNumber);
}
