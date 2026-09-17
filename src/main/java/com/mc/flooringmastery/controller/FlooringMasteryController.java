package com.mc.flooringmastery.controller;

import com.mc.flooringmastery.ui.FlooringMasteryView;

import java.time.LocalDate;

public class FlooringMasteryController {

    private boolean isRunning = true;
    private int menuSelection;

    private FlooringMasteryView view;

    public FlooringMasteryController( FlooringMasteryView view ) {
        this.view = view;
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
                System.out.println("TODO");
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

    }

    private void editOrder() {

    }

    private void removeOrder() {

    }

    private void exitMessage() { view.displayExitMessage(); }
}
