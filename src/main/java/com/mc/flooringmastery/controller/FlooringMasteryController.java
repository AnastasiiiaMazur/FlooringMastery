package com.mc.flooringmastery.controller;

import com.mc.flooringmastery.ui.FlooringMasteryView;

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
            case 1:
                System.out.println("TODO");
                break;
            case 2:
                System.out.println("TODO");
                break;
            case 3:
                System.out.println("TODO");
                break;
            case 4:
                System.out.println("TODO");
                break;
            case 5:
                System.out.println("TODO");
                break;
            case 6:
                isRunning = false;
                exitMessage();
                break;
        }
    }

    private void exitMessage() { view.displayExitMessage(); }
}
