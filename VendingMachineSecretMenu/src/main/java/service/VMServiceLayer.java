/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.VMPersistenceException;
import dto.Candy;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface VMServiceLayer {
    // get all candy from inventory

    List<Candy> getAllCandyInventory() throws VMPersistenceException;
    // get candy only if it is in stock

    List<Candy> getOnlyCandyInStock() throws VMPersistenceException;
    // purchase candy from inventory

    String buyCandyInventory(Candy candy, BigDecimal userCash) throws InsufficientFundsException, VMPersistenceException;
    // get candy from inventory

    Candy getCandyInventory(String candyName) throws NotFoundException, VMPersistenceException;
    // check if candy is in stock

    void inStockStatus(String candyName) throws OutOfStockException, VMPersistenceException;
}
