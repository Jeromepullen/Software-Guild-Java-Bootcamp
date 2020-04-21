/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.VMDao;
import dao.VMPersistenceException;
import dto.Candy;
import dto.Change;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jeromepullenjr
 */
public class VMServiceLayerImpl implements VMServiceLayer {

    VMDao dao;
    Change userChange = new Change();

    public VMServiceLayerImpl(VMDao dao) {
        this.dao = dao;
    }

    @Override
    public String buyCandyInventory(Candy candy, BigDecimal userCash) throws VMPersistenceException, InsufficientFundsException {

        // user cash must be greater than candy cost, otherwise throw error
        if (candy.getCost().compareTo(userCash) > 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        // update the current inventory based on user purchase 
        int currentInventory = candy.getVMInventory();

        candy.setVMInventory(currentInventory - 1);   // subtract 1 to set the
        // updated inventory level
        dao.editCandyInventory(candy.getCandyName(), candy); // edit inventory
        // by candy name
        userChange.getChange(candy.getCost(), userCash);

        // Change logic
        String str;
        if (userChange.getQuarter() > 0) {
            str = "Your item has been vended.\nPlease take your"
                    + " remaining change:\n" + userChange.getQuarter()
                    + " quarters & " + userChange.getDime() + " dimes & "
                    + userChange.getNickel() + " nickels &\n"
                    + userChange.getPenny() + " pennies";

        } else if (userChange.getDime() > 0) {
            str = "Your item has been vended. Your change remaining is: "
                    + userChange.getDime() + " dimes & "
                    + userChange.getNickel() + " nickels &\n"
                    + userChange.getPenny() + " pennies";

        } else if (userChange.getNickel() > 0) {
            str = "\"Your item has been vended. Your change remaining is: "
                    + userChange.getNickel() + " nickels &\n"
                    + userChange.getPenny() + " pennies";

        } else if (userChange.getPenny() > 0) {
            str = "\"Your item has been vended. Your change remaining is: "
                    + userChange.getPenny() + " pennies";

        } else {
            str = "There is no change left over";
        }
        return str;
    }

    @Override
    public List<Candy> getOnlyCandyInStock() throws VMPersistenceException {
        return dao.getOnlyCandyInStock();
    }

    @Override
    public List<Candy> getAllCandyInventory() throws VMPersistenceException {
        return dao.getAllCandyInventory();
    }

    @Override
    public void inStockStatus(String candyName) throws VMPersistenceException, OutOfStockException {

        List<Candy> inStock = dao.getOnlyCandyInStock().stream()
                .filter(i -> i.getCandyName().equals(candyName))
                .collect(Collectors.toList());

        if (inStock.isEmpty()) {
            throw new OutOfStockException("\tThis item is out of stock");
        }
    }

    @Override
    public Candy getCandyInventory(String candyName) throws VMPersistenceException, NotFoundException {

        Candy candy = dao.getCandyInventory(candyName);

        if (candy == null) {
            throw new NotFoundException("There are no more left in the inventory");
        }
        return candy;
    }
}
