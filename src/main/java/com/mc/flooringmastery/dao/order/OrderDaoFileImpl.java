package com.mc.flooringmastery.dao.order;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OrderDaoFileImpl implements OrderDao {

    private String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
            "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    private static final String DELIMITER = "::";
    private HashMap<Integer, Order> orders = new HashMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        loadOrdersForDate(date);
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException {
        loadOrdersForDate(date);

        return orders.get(orderNumber);
    }

    @Override
    public Order addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException{
        loadOrdersForDate(date);
        orders.put(order.getOrderNumber(), order);
        writeOrders(getOrderFileName(date));

        return order;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException {
        loadOrdersForDate(date);
        orders.put(order.getOrderNumber(), order);
        writeOrders(getOrderFileName(date));

        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException {
        loadOrdersForDate(date);
        Order removedOrder = orders.remove(orderNumber);
        writeOrders(getOrderFileName(date));

        return removedOrder;
    }

    private String getOrderFileName(LocalDate date) {
        String orderFileDate = date.format(formatter);

        return "Orders/Orders_" + orderFileDate + ".txt";
    }

    private void writeOrders(String fileName) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }

        out.println(header);

        String orderAsText;

        for (Order currentOrder : orders.values()) {
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
        }

        out.flush();
        out.close();
    }

    private void loadOrders(String fileName) {
        orders.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load order data.", e);
        }

        String currentLine;
        Order currentOrder;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);

            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }

    private Order unmarshallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);

        Order order = new Order(
                Integer.parseInt(orderTokens[0]),
                orderTokens[1],
                orderTokens[2],
                new BigDecimal(orderTokens[3]),
                orderTokens[4],
                new BigDecimal(orderTokens[5]),
                new BigDecimal(orderTokens[6]),
                new BigDecimal(orderTokens[7]),
                new BigDecimal(orderTokens[8]),
                new BigDecimal(orderTokens[9]),
                new BigDecimal(orderTokens[10]),
                new BigDecimal(orderTokens[11])
        );

        return order;
    }

    private String marshallOrder(Order order) {
        return order.toString();
    }

    private void loadOrdersForDate(LocalDate date) {
        String fileName = getOrderFileName(date);
        File orderFile = new File(fileName);

        if (orderFile.exists()) {
            loadOrders(fileName);
        } else {
            orders.clear();
        }
    }
}

