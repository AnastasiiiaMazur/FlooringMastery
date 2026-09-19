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
            LocalDate date = view.getDate();
            // 2. Ask View for customer name
            String name = view.getCustomerName();
            // 3. Display available states and get state
            view.displayAvailableStates(service.getAllTaxes());
            String state = view.getState();
            // 4. Display available products and get product
            view.displayAvailableProducts(service.getAllProducts());
            String productType = view.getProductType();
            // 5. Ask for area
            BigDecimal area = view.getArea();
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
            // 9. If confirmed:
            //       service.addOrder(date, order)

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
}
