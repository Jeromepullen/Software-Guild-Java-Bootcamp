/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.SecretCandy;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface VMSecretDao {

    // get secretCandy only if it is in stock
    List<SecretCandy> getOnlySecretCandyInStock() throws VMPersistenceException;
    // get all secretCandy from inventory

    List<SecretCandy> getAllSecretCandyInventory() throws VMPersistenceException;
    // edit inventory

    SecretCandy editSecretCandyInventory(String secretCandyName, SecretCandy secretCandy) throws VMPersistenceException;
    // get secretCandy from inventory

    SecretCandy getSecretCandyInventory(String secretCandyName) throws VMPersistenceException;
}
