package com.mc.flooringmastery.test.service.stub;

import com.mc.flooringmastery.dao.tax.TaxDao;
import com.mc.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.util.List;

public class TaxDaoStubImpl implements TaxDao {

    private Tax tax;

    public TaxDaoStubImpl() {
        tax = new Tax(
                "Washington",
                "WA",
                new BigDecimal("9.25")
        );
    }

    @Override
    public List<Tax> getAllTaxes() {
        return List.of(tax);
    }
}
