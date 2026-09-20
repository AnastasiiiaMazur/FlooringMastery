package com.mc.flooringmastery.test.dao.tax;

import com.mc.flooringmastery.dao.tax.TaxDao;
import com.mc.flooringmastery.dao.tax.TaxDaoFileImpl;
import com.mc.flooringmastery.dto.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TaxDaoFileImplTest {

    TaxDao taxDao;

    @BeforeEach
    void setUp() {
        taxDao = new TaxDaoFileImpl();
    }

    @Test
    void getAllTaxes() {
        List<Tax> taxes = taxDao.getAllTaxes();

        assertEquals(4, taxes.size());

        Tax taxWA = new Tax(
                "Washington",
                "WA",
                new BigDecimal("9.25")
        );

        assertTrue(taxes.contains(taxWA));
    }
}