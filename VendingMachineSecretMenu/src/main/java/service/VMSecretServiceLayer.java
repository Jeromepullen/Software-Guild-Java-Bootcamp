/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.VMPersistenceException;
import dto.SecretCandy;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface VMSecretServiceLayer {

    // get all candy from inventory
    List<SecretCandy> getAllSecretCandyInventory() throws VMPersistenceException;
    // get candy only if it is in stock

    List<SecretCandy> getOnlySecretCandyInStock() throws VMPersistenceException;
    // purchase candy from inventory

    String buySecretCandyInventory(SecretCandy candy, BigDecimal userCash) throws InsufficientFundsException, VMPersistenceException;
    // get candy from inventory

    SecretCandy getSecretCandyInventory(String secretCandyName) throws NotFoundException, VMPersistenceException;
    // check if candy is in stock

    void inSecretStockStatus(String secretCandyName) throws OutOfStockException, VMPersistenceException;
}
