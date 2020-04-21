/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.SecretCandy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author jeromepullenjr
 */
public class VMSecretDaoFileImpl implements VMSecretDao {

    Map<String, SecretCandy> secretCandyInv = new HashMap<>(); // create new HashMap
    private static final String VMSECRETINVFILE = "SecretVMInventoryList.txt";
    private static final String DELIMITER = "::";

    public void writeInventory() throws VMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VMSECRETINVFILE));
        } catch (IOException e) {
            throw new VMPersistenceException("Unable to save vending machine data", e);
        }
        // creates list to save candy with the delimiter
        List<SecretCandy> secretCandyList = getAllSecretCandyInventory();

        for (SecretCandy secretCandyChoice : secretCandyList) {
            out.println(secretCandyChoice.getSecretCandyName() + DELIMITER
                    + secretCandyChoice.getCost() + DELIMITER
                    + secretCandyChoice.getSecretVMInventory() + DELIMITER);
            out.flush(); // forces PrintWriter to write to file
        }
        out.close();    // close and flush stream    
    }

    public void getInventory() throws VMPersistenceException {
        Scanner sc;
        String currentLine;
        String[] currentTokens;

        try {           // create new scanner for reading in the inventory file
            sc = new Scanner(new BufferedReader(new FileReader(VMSECRETINVFILE)));
        } catch (FileNotFoundException e) {
            throw new VMPersistenceException("Unable to load inventory data", e);
        }
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine(); // read in next line from file
            currentTokens = currentLine.split(DELIMITER); // tokenize scanner
            SecretCandy scanSecretCandy = new SecretCandy(currentTokens[0]);
            // set candy values in inventory
            scanSecretCandy.setCost(new BigDecimal(currentTokens[1]));
            scanSecretCandy.setSecretVMInventory(Integer.parseInt(currentTokens[2]));
            // put selected candy into HashMap by candy name
            secretCandyInv.put(scanSecretCandy.getSecretCandyName(), scanSecretCandy);
        }
        sc.close(); // close scanner
    }

    @Override // edit and write candy to inventory
    public SecretCandy editSecretCandyInventory(String secretCandyName, SecretCandy secretCandy) throws VMPersistenceException {
        SecretCandy editSecretCandy = secretCandyInv.put(secretCandyName, secretCandy);
        writeInventory();
        return editSecretCandy;
    }

    @Override // get candy from inventory
    public SecretCandy getSecretCandyInventory(String secretCandyName) throws VMPersistenceException {
        getInventory();
        SecretCandy getSecretCandy = secretCandyInv.get(secretCandyName);
        return getSecretCandy;
    }

    @Override // get inventory entire list
    public List<SecretCandy> getAllSecretCandyInventory() throws VMPersistenceException {
        getInventory();
        List<SecretCandy> allSecretCandyInventory = new ArrayList<>(secretCandyInv.values());
        return allSecretCandyInventory;
    }

    @Override // get in stock candy only from inventory stream
    public List<SecretCandy> getOnlySecretCandyInStock() throws VMPersistenceException {
        getInventory();
        List<SecretCandy> allSecretCandyInventory = this.getAllSecretCandyInventory();

        return allSecretCandyInventory.stream()
                .filter(i -> i.getSecretVMInventory() > 0)
                .collect(Collectors.toList());
    }

}
