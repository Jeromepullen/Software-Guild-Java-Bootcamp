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
public class SecretCandy {

    private BigDecimal cost;
    private int SecretVMInventory;
    private String secretCandyName;
    // getters & setters

    public SecretCandy(String secretCandyName) {
        this.secretCandyName = secretCandyName;
    }

    public int getSecretVMInventory() {
        return SecretVMInventory;
    }

    public void setSecretVMInventory(int inventory) {
        this.SecretVMInventory = inventory;
    }

    public String getSecretCandyName() {
        return secretCandyName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
