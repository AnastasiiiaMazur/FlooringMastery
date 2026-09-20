package com.mc.flooringmastery.test.dao.order;

import com.mc.flooringmastery.dao.order.OrderDaoFileImpl;
import com.mc.flooringmastery.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoFileImplTest {

    OrderDaoFileImpl  orderDao;

    @BeforeEach
    void setUp() {
        File testDirectory = new File("TestOrders");

        if (!testDirectory.exists()) {
            testDirectory.mkdir();
        }

        File[] files = testDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        orderDao = new OrderDaoFileImpl();
        orderDao.setOrdersDirectory("TestOrders");
    }

    @Test
    void getAllOrders() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order order1 = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        Order order2 = new Order(
                2,
                "John Smith",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("150.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("772.50"),
                new BigDecimal("712.50"),
                new BigDecimal("137.36"),
                new BigDecimal("1622.36")
        );

        orderDao.addOrder(date, order1);
        orderDao.addOrder(date, order2);

        List<Order> orders = orderDao.getAllOrders(date);

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void getOrder() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order order1 = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        Order order2 = new Order(
                2,
                "John Smith",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("150.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("772.50"),
                new BigDecimal("712.50"),
                new BigDecimal("137.36"),
                new BigDecimal("1622.36")
        );

        orderDao.addOrder(date, order1);
        orderDao.addOrder(date, order2);

        Order retrievedOrder = orderDao.getOrder(date, 2);

        assertEquals(order2, retrievedOrder);
    }

    @Test
    void addOrder() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order order = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        orderDao.addOrder(date, order);

        Order retrievedOrder = orderDao.getOrder(date, 1);

        assertEquals(order, retrievedOrder);
    }

    @Test
    void editOrder() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order originalOrder = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        orderDao.addOrder(date, originalOrder);

        Order editedOrder = new Order(
                1,
                "Ada Lovelace Edited",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("200.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("1030.00"),
                new BigDecimal("950.00"),
                new BigDecimal("183.15"),
                new BigDecimal("2163.15")
        );

        orderDao.editOrder(date, editedOrder);

        Order retrievedOrder = orderDao.getOrder(date, 1);

        assertEquals(editedOrder, retrievedOrder);
        assertEquals(1, orderDao.getAllOrders(date).size());
    }

    @Test
    void removeOrder() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order order = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        orderDao.addOrder(date, order);

        Order removedOrder = orderDao.removeOrder(date, 1);

        assertEquals(order, removedOrder);
        assertNull(orderDao.getOrder(date, 1));
        assertEquals(0, orderDao.getAllOrders(date).size());
    }

    @Test
    void getHighestOrderNumber() {
        LocalDate date1 = LocalDate.of(2026, 12, 20);
        LocalDate date2 = LocalDate.of(2026, 12, 21);

        Order order1 = new Order(
                5,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        Order order2 = new Order(
                12,
                "John Smith",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("200.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("1030.00"),
                new BigDecimal("950.00"),
                new BigDecimal("183.15"),
                new BigDecimal("2163.15")
        );

        orderDao.addOrder(date1, order1);
        orderDao.addOrder(date2, order2);

        int highestOrderNumber = orderDao.getHighestOrderNumber();

        assertEquals(12, highestOrderNumber);
    }

    @Test
    void getAvailableOrderDates() {
        LocalDate date1 = LocalDate.of(2026, 12, 20);
        LocalDate date2 = LocalDate.of(2026, 12, 25);

        Order order1 = new Order(
                1,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        Order order2 = new Order(
                2,
                "John Smith",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("200.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("1030.00"),
                new BigDecimal("950.00"),
                new BigDecimal("183.15"),
                new BigDecimal("2163.15")
        );

        orderDao.addOrder(date1, order1);
        orderDao.addOrder(date2, order2);

        List<LocalDate> dates = orderDao.getAvailableOrderDates();

        assertEquals(2, dates.size());
        assertTrue(dates.contains(date1));
        assertTrue(dates.contains(date2));
    }

    @Test
    void getOrderNumbers() {
        LocalDate date = LocalDate.of(2026, 12, 20);

        Order order1 = new Order(
                3,
                "Ada Lovelace",
                "CA",
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("249.00"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("871.50"),
                new BigDecimal("1033.35"),
                new BigDecimal("476.21"),
                new BigDecimal("2381.06")
        );

        Order order2 = new Order(
                8,
                "John Smith",
                "WA",
                new BigDecimal("9.25"),
                "Wood",
                new BigDecimal("200.00"),
                new BigDecimal("5.15"),
                new BigDecimal("4.75"),
                new BigDecimal("1030.00"),
                new BigDecimal("950.00"),
                new BigDecimal("183.15"),
                new BigDecimal("2163.15")
        );

        orderDao.addOrder(date, order1);
        orderDao.addOrder(date, order2);

        List<Integer> orderNumbers = orderDao.getOrderNumbers(date);

        assertEquals(2, orderNumbers.size());
        assertEquals(3, orderNumbers.get(0));
        assertEquals(8, orderNumbers.get(1));
    }
}