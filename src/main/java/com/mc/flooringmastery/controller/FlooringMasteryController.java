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
                System.out.println("TODO");
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
            view.displayCompleteOrder(complete);

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
            // 1. Ask View for date

            // 2. Ask View for order number

            // 3. Service gets the existing order
            //    If it doesn't exist, Service throws validation exception

            // 4. View displays the existing order

            // 5. Ask View for new customer name
            //    Blank input should keep existing value

            // 6. Display available states and ask for new state
            //    Blank input should keep existing value

            // 7. Display available products and ask for new product
            //    Blank input should keep existing value

            // 8. Ask View for new area
            //    Blank input should keep existing value

            // 9. Service creates/recalculates the edited order

            // 10. View displays the updated order

            // 11. Ask user to confirm the edit

            // 12. If confirmed:
            //        save edited order through Service
            //        display success message
            //     Otherwise:
            //        display cancellation message

        } catch (FlooringMasteryDataValidationException e) {
            // display validation error
        } catch (FlooringMasteryPersistenceException e) {
            // display file/persistence error
        }
    }

    private void removeOrder() {
        try {
            // 1. Ask View for date

            // 2. Ask View for order number

            // 3. Service gets the order
            //    If it doesn't exist, Service throws validation exception

            // 4. View displays the order

            // 5. Ask user to confirm removal

            // 6. If confirmed:
            //        Service removes the order
            //        display success message
            //     Otherwise:
            //        display cancellation message

        } catch (FlooringMasteryDataValidationException e) {
            // display validation error
        } catch (FlooringMasteryPersistenceException e) {
            // display file/persistence error
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
}
