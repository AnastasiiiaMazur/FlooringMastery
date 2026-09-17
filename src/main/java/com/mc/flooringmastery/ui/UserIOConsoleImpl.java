package com.mc.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class UserIOConsoleImpl implements UserIO {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        int response = 0;

        print(prompt);
        try {
            response = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            print("Enter a number.");
        }

        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean isValid = false;
        int num = 0;

        while (!isValid) {
            try {
                print(prompt);
                num = Integer.parseInt(scanner.nextLine());
                if (num < min || num > max) {
                    print("Please write a valid number!\n");
                    continue;
                }

                isValid = true;
            } catch (NumberFormatException ex) {
                print("Please enter a valid number!\n");
            }
        }

        return num;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        BigDecimal num = new BigDecimal(0);
        print(prompt);
        try {
            num = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException ex) {
            print("Enter a valid decimal number!\n");
        }
        return num;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        LocalDate userDate = LocalDate.now();
        print(prompt);
        try {
            if (scanner.hasNextBigDecimal()) {
                userDate = LocalDate.parse(scanner.nextLine());
            }
        } catch (Exception ex) {
            print("Enter a valid date format!\n");
        }
        return userDate;
    }
}
