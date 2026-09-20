package com.mc.flooringmastery.ui;

import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.dto.Product;
import com.mc.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryView {

    UserIO io;

    public FlooringMasteryView ( UserIO io ) {
        this.io = io;
    }

    public void displayHeader() {
        io.print("\no0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o");
        io.print("\nThis is a Flooring Mastery App!");
        io.print("\no0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o");
    }

    public int displayMenu() {
        int menuChoice;
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("*");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        menuChoice = io.readInt("\nPlease choose the option above by typing the number:", 1, 6);

        return menuChoice;
    }

    public void displayErrorMessage(String message) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("\n" + message);
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void displayExitMessage() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* * * * * * * * Thanks for using the app! * * * * * * * * * * * * * *");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public LocalDate getDate() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readLocalDate("\nPlease enter the date for an order in a format yyyy-MM-dd: ");
    }

    public String getCustomerName() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readString("* Enter the name: ");
    }

    public void displayAvailableStates(List<Tax> taxes) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* We accept orders in these states: ");
        for (Tax tax : taxes) {
            io.print("* " + tax.getState() + ", " + tax.getStateAbr());
        }
    }

    public String getState() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readString("* Enter the state abbreviation: ");
    }

    public void displayAvailableProducts(List<Product> products) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* We accept orders with these materials: ");
        for (Product product : products) {
            io.print("* " + product.getProductType());
        }
    }

    public String getProductType() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readString("* Enter the product type: ");
    }

    public BigDecimal getArea() {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readBigDecimal("* Enter the area (min 100): ");
    }

    public void displayCompleteOrder(Order order) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* * * * * * * * * Here is your complete order! * * * * * * * * * * * *");
        io.print("* Order name: " + order.getOrderName());
        io.print("* Order area: " + order.getArea());
        io.print("* Order state: " + order.getState());
        io.print("* Order product type: " + order.getProductType());
        io.print("* Order labour cost: " + order.getLabourCost());
        io.print("* Order material cost: " + order.getMaterialCost());
        io.print("* Order tax: " + order.getTax());
        io.print("* Order total: " + order.getTotal());
        io.print("* * * * * * * * * Here is your complete order! * * * * * * * * * * * *");
    }

    public boolean confirmation(String message) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* " + message);
        String answer = io.readString("* Please type Y/N: ");
        return answer.equalsIgnoreCase("Y");
    }

    public void displayOrderStatusMessage(String message) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* " + message);
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void displayAvailableDates(List<LocalDate> dates) {
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* Available dates:");
        for (LocalDate date : dates) {
            io.print("* " + date);
        }
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void displayAllOrdersForDate(LocalDate userDate, List<Order> orders) {
        if (orders.isEmpty()) {
            io.print("* No orders found for this date.");
            return;
        }
        io.print("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* Orders for the date " + userDate);
        for (Order order : orders) {
            io.print("*");
            io.print("* Order " + order.getOrderNumber());
            io.print("* Order name: " + order.getOrderName());
            io.print("* Order area: " + order.getArea());
            io.print("* Order state: " + order.getState());
            io.print("* Order product type: " + order.getProductType());
            io.print("* Order labour cost: " + order.getLabourCost());
            io.print("* Order material cost: " + order.getMaterialCost());
            io.print("* Order tax: " + order.getTax());
            io.print("* Order total: " + order.getTotal());
            io.print("*");
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        }
    }
}
