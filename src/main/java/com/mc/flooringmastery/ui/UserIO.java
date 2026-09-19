package com.mc.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {

    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String decimal);

    LocalDate readLocalDate(String date);

    LocalDate readLocalDateOnce(String date);

}
