package com.mc.flooringmastery;

import com.mc.flooringmastery.controller.FlooringMasteryController;
import com.mc.flooringmastery.ui.FlooringMasteryView;
import com.mc.flooringmastery.ui.UserIO;
import com.mc.flooringmastery.ui.UserIOConsoleImpl;

public class App {
    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        FlooringMasteryView view = new FlooringMasteryView(io);
        FlooringMasteryController controller = new FlooringMasteryController(view);

        controller.run();
    }
}
