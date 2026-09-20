package com.mc.flooringmastery.controller;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.service.FlooringMasteryDataValidationException;
import com.mc.flooringmastery.service.FlooringMasteryService;
import com.mc.flooringmastery.service.FlooringMasteryServiceImpl;
import com.mc.flooringmastery.ui.FlooringMasteryView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryController {

    private boolean isRunning = true;
    private int menuSelection;

    private FlooringMasteryView view;
    private FlooringMasteryService service;

    public FlooringMasteryController( FlooringMasteryView view, FlooringMasteryService service ) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.displayHeader();

        while (isRunning) {
            menuSelection = getMenuSelection();
            try {
                runMenuSelection(menuSelection);
            } catch (Exception ex) {
                System.out.println("Test");
            }
        }
    }

    private int getMenuSelection() {
        return view.displayMenu();
    }

    private void runMenuSelection(int menuSelection) {
        switch (menuSelection) {
            case 1: // display orders
                displayAllOrders();
                break;
            case 2: // add an order
                addOrder();
                break;
            case 3: // edit an order
                editOrder();
                break;
            case 4: // remove an order
                removeOrder();
                break;
            case 5: // backup data
                System.out.println("Not implemented in this version!");
                break;
            case 6: // quit
                isRunning = false;
                exitMessage();
                break;
        }
    }

    private void displayAllOrders() {
        try {
            // Get available order dates from Service
            List<LocalDate> dates = service.getAvailableOrderDates();
            if (dates.isEmpty()) {
                view.displayOrderStatusMessage("There are no orders available.");
                return;
            }

            // View displays available dates and asks for the date
            view.displayAvailableDates(dates);
            LocalDate userDate = dateValidation();

            // Service gets all orders for that date
            List<Order> orders = service.getAllOrders(userDate);

            // View displays the orders
            view.displayAllOrdersForDate(userDate, orders);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() {
        try {
            // Ask View for date
            LocalDate date = dateValidation();

            // Ask View for customer name
            String name = nameValidation();

            // Display available states and get state
            view.displayAvailableStates(service.getAllTaxes());
            String state = stateValidation();

            // Display available products and get product
            view.displayAvailableProducts(service.getAllProducts());
            String productType = productValidation();

            // Ask for area
            BigDecimal area = areaValidation();

            // service.createOrder(...)
            Order complete = service.createOrder(date, name, state, productType, area);

            // View displays completed order
            view.displayCompleteOrder(complete, "Here is your complete order!");

            // Ask for confirmation
            if (view.confirmation("Would you like to place this order?")) {
                service.addOrder(date, complete);
                view.displayOrderStatusMessage("Order was accepted successfully!");
            } else {
                view.displayOrderStatusMessage("Order cancelled!");
            }


        } catch (FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() {
        try {
            List<LocalDate> dates = service.getAvailableOrderDates();
            if (dates.isEmpty()) {
                view.displayOrderStatusMessage("There are no orders available.");
                return;
            }
            view.displayAvailableDates(dates);
            LocalDate userDate = dateValidation();

            Integer orderNumber = getOrderNumber(userDate);
            if (orderNumber == null) {
                return;
            }

            Order order = service.getOrder(userDate, orderNumber);
            view.displayCompleteOrder(order, "Order " + order.getOrderNumber());

            // name
            String name = view.getCustomerName();

            if (name.trim().isEmpty()) {
                name = order.getOrderName();
            } else {
                service.validateCustomer(name);
            }

            // state
            view.displayAvailableStates(service.getAllTaxes());
            String state = view.getState();

            if (state.trim().isEmpty()) {
                state = order.getState();
            } else {
                service.validateState(state);
            }

            // product type
            view.displayAvailableProducts(service.getAllProducts());
            String productType = view.getProductType();

            if (productType.trim().isEmpty()) {
                productType = order.getProductType();
            } else {
                service.validateProduct(productType);
            }

            // area
            BigDecimal area = view.getEditedArea();

            if (area == null) {
                area = order.getArea();
            } else {
                service.validateOrderArea(area);
            }

            Order newOrder = service.createEditedOrder(orderNumber, name, state, productType, area);
            view.displayCompleteOrder(newOrder, "Here is your updated order");

            if (view.confirmation("Would you like to update this order?")) {
                service.editOrder(userDate, orderNumber, name, state, productType, area);
                view.displayOrderStatusMessage("Order was updated successfully!");
            } else {
                view.displayOrderStatusMessage("Order update cancelled!");
            }

        } catch (FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() {
        try {
            List<LocalDate> dates = service.getAvailableOrderDates();
            if (dates.isEmpty()) {
                view.displayOrderStatusMessage("There are no orders available.");
                return;
            }
            view.displayAvailableDates(dates);
            LocalDate userDate = dateValidation();

            Integer num = getOrderNumber(userDate);

            if (num == null) {
                return;
            }

            Order order = service.getOrder(userDate, num);

            view.displayCompleteOrder(order, "Order " + order.getOrderNumber());

            if (view.confirmation("Would you like to delete this order?")) {
                service.removeOrder(userDate, order.getOrderNumber());
                view.displayOrderStatusMessage("Order was deleted successfully!");
            } else {
                view.displayOrderStatusMessage("Order deletion cancelled!");
            }

        } catch (FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exitMessage() { view.displayExitMessage(); }

    private LocalDate dateValidation() {
        LocalDate date;
        while (true) {
            try {
                date = view.getDate();
                service.validateDate(date);
                break;
            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return date;
    }

    private String nameValidation() {
        String name;

        while (true) {
            try {
                name = view.getCustomerName();
                service.validateCustomer(name);
                break;
            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        return name;
    }

    private String stateValidation() {
        String state;

        while (true) {
            try {
                state = view.getState();
                service.validateState(state);
                break;
            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        return state;
    }

    private String productValidation() {
        String productType;

        while (true) {
            try {
                productType = view.getProductType();
                service.validateProduct(productType);
                break;
            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        return productType;
    }

    private BigDecimal areaValidation() {
        BigDecimal area;

        while (true) {
            try {
                area = view.getArea();
                service.validateOrderArea(area);
                break;
            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

        return area;
    }

    private Integer getOrderNumber(LocalDate date) {
        List<Integer> orderNumbers = service.getAvailableOrdersNum(date);
        if (orderNumbers.isEmpty()) {
            view.displayOrderStatusMessage("There are no orders available for this date.");
            return null;
        }

        view.displayOrderNums(orderNumbers);

        int num;
        while (true) {
            num = view.getNum();

            if (orderNumbers.contains(num)) {
                break;
            }
            view.displayErrorMessage("Please select an available order number.");
        }

        return num;
    }
}
