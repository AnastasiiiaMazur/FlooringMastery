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
            runMenuSelection(menuSelection);
        }
    }

    private int getMenuSelection() {
        return view.displayMenu();
    }

    // call methods based on user selection
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

            LocalDate userDate = getExistingOrderDate(dates);

            // Service gets all orders for that date and view displays them
            List<Order> orders = service.getAllOrders(userDate);
            view.displayAllOrdersForDate(userDate, orders);

        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() {
        try {
            LocalDate date = dateValidation();
            String name = nameValidation();

            // Display valid states before requesting the user's selection.
            view.displayAvailableStates(service.getAllTaxes());
            String state = stateValidation();

            // Display valid products before requesting the user's selection.
            view.displayAvailableProducts(service.getAllProducts());
            String productType = productValidation();

            BigDecimal area = areaValidation();

            // Build and preview the order before saving it.
            Order complete = service.createOrder(
                    date, name, state, productType, area);

            view.displayCompleteOrder(complete, "Here is your complete order!");

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
            // Get available order dates from Service
            List<LocalDate> dates = service.getAvailableOrderDates();
            if (dates.isEmpty()) {
                view.displayOrderStatusMessage("There are no orders available.");
                return;
            }
            // View displays available dates and asks for the date
            view.displayAvailableDates(dates);
            LocalDate userDate = getExistingOrderDate(dates);

            // get all available order numbers from service
            Integer orderNumber = getOrderNumber(userDate);
            if (orderNumber == null) {
                return;
            }

            Order order = service.getOrder(userDate, orderNumber);
            view.displayCompleteOrder(order, "Order " + order.getOrderNumber());

            String name = view.getCustomerName();

            // blank input keeps the existing customer name
            if (name.trim().isEmpty()) {
                name = order.getOrderName();
            } else {
                service.validateCustomer(name);
            }

            view.displayAvailableStates(service.getAllTaxes());
            String state = view.getState();

            // blank input keeps the existing state
            if (state.trim().isEmpty()) {
                state = order.getState();
            } else {
                service.validateState(state);
            }

            view.displayAvailableProducts(service.getAllProducts());
            String productType = view.getProductType();

            // blank input keeps the existing product type
            if (productType.trim().isEmpty()) {
                productType = order.getProductType();
            } else {
                service.validateProduct(productType);
            }

            BigDecimal area = view.getEditedArea();

            // blank input keeps the existing area
            if (area == null) {
                area = order.getArea();
            } else {
                service.validateOrderArea(area);
            }

            // service creates updated order and displays it to the user
            Order newOrder = service.createEditedOrder(orderNumber, name, state, productType, area);
            view.displayCompleteOrder(newOrder, "Here is your updated order");

            // ask for confirmation
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
            // Get available order dates from Service
            List<LocalDate> dates = service.getAvailableOrderDates();
            if (dates.isEmpty()) {
                view.displayOrderStatusMessage("There are no orders available.");
                return;
            }
            // View displays available dates and asks for the date
            view.displayAvailableDates(dates);
            LocalDate userDate = getExistingOrderDate(dates);

            // get available order numbers from service
            Integer num = getOrderNumber(userDate);

            if (num == null) {
                return;
            }

            // service retrieves order and view displays it to the user
            Order order = service.getOrder(userDate, num);
            view.displayCompleteOrder(order, "Order " + order.getOrderNumber());

            // ask for confirmation
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

    private LocalDate getExistingOrderDate(List<LocalDate> dates) {
        LocalDate userDate;

        while (true) {
            userDate = view.getDate();

            if (dates.contains(userDate)) {
                break;
            }

            view.displayErrorMessage("Please choose a date from the available dates.");
        }

        return userDate;
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

    // retrieve order number
    private Integer getOrderNumber(LocalDate date) {
        // service gets all possible order numbers for chosen date
        List<Integer> orderNumbers = service.getAvailableOrdersNum(date);
        if (orderNumbers.isEmpty()) {
            view.displayOrderStatusMessage("There are no orders available for this date.");
            return null;
        }

        // view displays order numbers and asks user for the input
        view.displayOrderNums(orderNumbers);

        int num;
        while (true) {
            num = view.getNum();
            // only accept an order number that exists for the selected date
            if (orderNumbers.contains(num)) {
                break;
            }
            view.displayErrorMessage("Please select an available order number.");
        }

        return num;
    }
}
