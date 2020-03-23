/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author jeromepullenjr
 */
public interface UserIO {

    void print(String message);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);

    public BigDecimal readBigDecimal(String prompt);

    public LocalTime readTime(String prompt);

    public LocalDate readLocalDate(String prompt);

    public boolean readBoolean(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal cost);
}
