/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Candy;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface VMDao {

    // get candy only if it is in stock
    List<Candy> getOnlyCandyInStock() throws VMPersistenceException;
    // get all candy from inventory

    List<Candy> getAllCandyInventory() throws VMPersistenceException;
    // edit inventory

    Candy editCandyInventory(String candyName, Candy candy) throws VMPersistenceException;
    // get candy from inventory

    Candy getCandyInventory(String candyName) throws VMPersistenceException;
}
