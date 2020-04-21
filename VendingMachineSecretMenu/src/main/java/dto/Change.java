/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import dao.VMPersistenceException;
import java.math.BigDecimal;

/**
 *
 * @author jeromepullenjr
 */
public class Change {

    private int quarter;
    private int dime;
    private int nickel;
    private int penny;
    // BigDecimal converts userChange
    // Gives user the exact # of denominations from VM userChange

    public void getChange(BigDecimal itemCost, BigDecimal userCash) throws VMPersistenceException {
        quarter = 0;
        dime = 0;
        nickel = 0;
        penny = 0;

        BigDecimal userChange = userCash.subtract(itemCost).multiply(new BigDecimal("100"));

        while (userChange.compareTo(new BigDecimal("0")) > 0) {

            // # of quarters owed back to user 
            if (userChange.compareTo(new BigDecimal("25")) >= 0) {

                quarter++;
                userChange = userChange.subtract(new BigDecimal("25"));

                // # of dimes owed back to user       
            } else if (userChange.compareTo(new BigDecimal("10")) >= 0) {

                dime++;
                userChange = userChange.subtract(new BigDecimal("10"));

                // # of nickels owed back to user       
            } else if (userChange.compareTo(new BigDecimal("5")) >= 0) {

                nickel++;
                userChange = userChange.subtract(new BigDecimal("5"));

                // # of pennies owed back to user     
            } else if (userChange.compareTo(new BigDecimal("1")) >= 0) {

                penny++;
                userChange = userChange.subtract(new BigDecimal("1"));
            }
        }
    }
    // getters

    public int getQuarter() {
        return quarter;
    }

    public int getDime() {
        return dime;
    }

    public int getNickel() {
        return nickel;
    }

    public int getPenny() {
        return penny;
    }

}
