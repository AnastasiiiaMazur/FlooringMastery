package com.mc.flooringmastery.dao.tax;

import com.mc.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mc.flooringmastery.dto.Tax;

import java.util.List;

public interface TaxDao {

    List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException;
}
