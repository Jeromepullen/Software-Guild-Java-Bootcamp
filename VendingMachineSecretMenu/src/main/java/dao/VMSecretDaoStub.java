/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.SecretCandy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public class VMSecretDaoStub implements VMSecretDao {

    SecretCandy secretCandy;
    List<SecretCandy> secretCandyList = new ArrayList<>();

    @Override                  // edit secretCandy in inventory if secretCandy name matches
    public SecretCandy editSecretCandyInventory(String secretCandyName, SecretCandy secretCandy) throws VMPersistenceException {
        if (secretCandyName.equals(secretCandy.getSecretCandyName())) {
            return secretCandy;
        } else {
            return null;
        }
    }

    @Override                 // get secretCandy from inventory if secretCandy name matches
    public SecretCandy getSecretCandyInventory(String secretCandyName) throws VMPersistenceException {
        if (secretCandyName.equals(secretCandy.getSecretCandyName())) {
            return secretCandy;
        } else {
            return null;
        }
    }

    @Override                   // get only secretCandy in stock
    public List<SecretCandy> getOnlySecretCandyInStock() throws VMPersistenceException {
        return Collections.emptyList();
    }

    @Override                   // get all inventory
    public List<SecretCandy> getAllSecretCandyInventory() throws VMPersistenceException {
        return secretCandyList;
    }

}
