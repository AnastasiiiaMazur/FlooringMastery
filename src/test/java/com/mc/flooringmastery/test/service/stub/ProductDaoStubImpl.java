package com.mc.flooringmastery.test.service.stub;

import com.mc.flooringmastery.dao.product.ProductDao;
import com.mc.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductDaoStubImpl implements ProductDao {

    private Product product;

    public ProductDaoStubImpl() {
        product = new Product(
                "Tile",
                new BigDecimal("3.50"),
                new BigDecimal("4.15")
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of(product);
    }
}