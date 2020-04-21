/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.VMPersistenceException;
import dao.VMSecretDao;
import dto.SecretCandy;
import dto.Change;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jeromepullenjr
 */
public class VMSecretServiceLayerImpl implements VMSecretServiceLayer {

    VMSecretDao secretDao;
    Change userChange = new Change();

    public VMSecretServiceLayerImpl(VMSecretDao secretDao) {
        this.secretDao = secretDao;
    }

    @Override
    public String buySecretCandyInventory(SecretCandy secretCandy, BigDecimal userCash) throws VMPersistenceException, InsufficientFundsException {

        // user cash must be greater than secretCandy cost, otherwise throw error
        if (secretCandy.getCost().compareTo(userCash) > 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        // update the current inventory based on user purchase 
        int currentInventory = secretCandy.getSecretVMInventory();

        secretCandy.setSecretVMInventory(currentInventory - 1);   // subtract 1 to set the
        // updated inventory level
        secretDao.editSecretCandyInventory(secretCandy.getSecretCandyName(), secretCandy); // edit inventory
        // by secretCandy name
        userChange.getChange(secretCandy.getCost(), userCash);

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
    public List<SecretCandy> getOnlySecretCandyInStock() throws VMPersistenceException {
        return secretDao.getOnlySecretCandyInStock();
    }

    @Override
    public List<SecretCandy> getAllSecretCandyInventory() throws VMPersistenceException {
        return secretDao.getAllSecretCandyInventory();
    }

    @Override
    public void inSecretStockStatus(String secretCandyName) throws VMPersistenceException, OutOfStockException {

        List<SecretCandy> inStock = secretDao.getOnlySecretCandyInStock().stream()
                .filter(i -> i.getSecretCandyName().equals(secretCandyName))
                .collect(Collectors.toList());

        if (inStock.isEmpty()) {
            throw new OutOfStockException("\tThis item is out of stock");
        }
    }

    @Override
    public SecretCandy getSecretCandyInventory(String secretCandyName) throws VMPersistenceException, NotFoundException {

        SecretCandy secretCandy = secretDao.getSecretCandyInventory(secretCandyName);

        if (secretCandy == null) {
            throw new NotFoundException("There are no more left in the inventory");
        }
        return secretCandy;
    }

}
