/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.math.BigDecimal;

/**
 *
 * @author jeromepullenjr
 */
public class Candy {

    private BigDecimal cost;
    private int VMInventory;
    private String candyName;
    // getters & setters

    public Candy(String candyName) {
        this.candyName = candyName;
    }

    public int getVMInventory() {
        return VMInventory;
    }

    public void setVMInventory(int inventory) {
        this.VMInventory = inventory;
    }

    public String getCandyName() {
        return candyName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
