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
        loadProducts();
        return new ArrayList<>(products.values());
    }

    private void loadProducts() throws FlooringMasteryPersistenceException {
        products.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("No such file exists!");
        }

        String currentLine;
        Product currentProduct;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);

            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);

        Product product = new Product();
        product.setProductType(productTokens[0]);
        product.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
        product.setLabourCostPerSquareFoot(new BigDecimal(productTokens[2]));

        return product;
    }
}
