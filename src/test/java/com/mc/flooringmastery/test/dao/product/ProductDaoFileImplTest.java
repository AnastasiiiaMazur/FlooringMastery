package com.mc.flooringmastery.test.dao.product;

import com.mc.flooringmastery.dao.product.ProductDao;
import com.mc.flooringmastery.dao.product.ProductDaoFileImpl;
import com.mc.flooringmastery.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDaoFileImplTest {

    ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoFileImpl();
    }

    @Test
    void getAllProducts() {
        List<Product> products = productDao.getAllProducts();

        assertEquals(4, products.size());

        Product tile = new Product(
                "Tile",
                new BigDecimal("3.50"),
                new BigDecimal("4.15")
        );

        assertTrue(products.contains(tile));
    }

}