/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Candy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public class VMDaoStub implements VMDao {

    Candy candy;
    List<Candy> candyList = new ArrayList<>();

    @Override                  // edit candy in inventory if candy name matches
    public Candy editCandyInventory(String candyName, Candy candy) throws VMPersistenceException {
        if (candyName.equals(candy.getCandyName())) {
            return candy;
        } else {
            return null;
        }
    }

    @Override                 // get candy from inventory if candy name matches
    public Candy getCandyInventory(String candyName) throws VMPersistenceException {
        if (candyName.equals(candy.getCandyName())) {
            return candy;
        } else {
            return null;
        }
    }

    @Override                   // get only candy in stock
    public List<Candy> getOnlyCandyInStock() throws VMPersistenceException {
        return Collections.emptyList();
    }

    @Override                   // get all inventory
    public List<Candy> getAllCandyInventory() throws VMPersistenceException {
        return candyList;
    }

}
