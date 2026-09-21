package com.mc.flooringmastery.dao.product;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProductDaoFileImpl implements ProductDao {

    private HashMap<String, Product> products = new HashMap<>();
    public static final String PRODUCT_FILE = "Data/Products.txt";
    public static final String DELIMITER = ",";

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        // load tax data from file
        loadProducts();
        return new ArrayList<>(products.values());
    }

    private void loadProducts() throws FlooringMasteryPersistenceException {
        // clear existing data before loading the file
        products.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("No such file exists!");
        }

        String currentLine;
        Product currentProduct;

        // skip the header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        // read and store each tax record by state abbreviation
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);

            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private Product unmarshallProduct(String productAsText) {
        // convert file data into a tax object
        String[] productTokens = productAsText.split(DELIMITER);

        Product product = new Product(
                productTokens[0],
                new BigDecimal(productTokens[1]),
                new BigDecimal(productTokens[2])
        );

        return product;
    }
}
