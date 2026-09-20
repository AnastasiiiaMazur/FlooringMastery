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

        while (true) {
            try {
                print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                print("Please enter a valid number!\n");
            }
        }
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
        while (true) {
            try {
                print(prompt);
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException ex) {
                print("Enter a valid decimal number!\n");
            }
        }
    }

    @Override
    public LocalDate readLocalDateOnce(String prompt) {
        print(prompt);

        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (Exception ex) {
            print("Enter a valid date format!\n");
        }

        return null;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {

        while (true) {
            try {
                print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception ex) {
                print("Enter a valid date in yyyy-MM-dd format!\n");
            }
        }
    }
}
