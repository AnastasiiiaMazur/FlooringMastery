package com.mc.flooringmastery.dao.product;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
}
