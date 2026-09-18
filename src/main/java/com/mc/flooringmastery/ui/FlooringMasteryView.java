package com.mc.flooringmastery.ui;

import com.mc.flooringmastery.dto.Order;

import java.time.LocalDate;

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

//    public Order getNewOrderInfo() {
//
//    }
}
