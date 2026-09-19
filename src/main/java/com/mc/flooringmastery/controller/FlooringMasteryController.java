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
                System.out.println("TODO");
                break;
            case 4: // remove an order
                System.out.println("TODO");
                break;
            case 5: // backup data
                System.out.println("TODO");
                break;
            case 6: // quit
                isRunning = false;
                exitMessage();
                break;
        }
    }

    private void displayAllOrders() {
        LocalDate date = view.getDate();
        // send date to the service
        // return list of orders
        // pass list to the view
        // display orders
    }

    private void addOrder() {
        try {
            // 1. Ask View for date
            LocalDate date = dateValidation();

            // 2. Ask View for customer name
            String name = nameValidation();

            // 3. Display available states and get state
            view.displayAvailableStates(service.getAllTaxes());
            String state = stateValidation();

            // 4. Display available products and get product
            view.displayAvailableProducts(service.getAllProducts());
            String productType = productValidation();

            // 5. Ask for area
            BigDecimal area = areaValidation();

            // 6. service.createOrder(...)
            Order complete = service.createOrder(date, name, state, productType, area);

            // 7. View displays completed order
            view.displayCompleteOrder(complete);

            // 8. Ask for confirmation
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

    }

    private void removeOrder() {

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
}
