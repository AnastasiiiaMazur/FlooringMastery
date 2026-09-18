package com.mc.flooringmastery.dao.tax;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TaxDaoFileImpl implements TaxDao {

    private HashMap<String, Tax> taxes = new HashMap<>();
    public static final String PRODUCT_FILE = "Data/Taxes.txt";
    public static final String DELIMITER = ",";

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        loadTaxes();
        return new ArrayList<>(taxes.values());
    }

    private void loadTaxes() throws FlooringMasteryPersistenceException {
        taxes.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("No such file exists!");
        }

        String currentLine;
        Tax currentTax;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);

            taxes.put(currentTax.getStateAbr(), currentTax);
        }

        scanner.close();
    }

    private Tax unmarshallTax(String taxAsText) {
        String[] taxTokens = taxAsText.split(DELIMITER);

        Tax tax = new Tax();
        tax.setStateAbr(taxTokens[0]);
        tax.setState(taxTokens[1]);
        tax.setTaxRate(new BigDecimal(taxTokens[2]));

        return tax;
    }
}
