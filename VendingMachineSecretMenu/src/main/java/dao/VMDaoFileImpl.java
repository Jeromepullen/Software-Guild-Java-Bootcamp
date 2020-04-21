/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Candy;
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
public class VMDaoFileImpl implements VMDao {

    Map<String, Candy> candyInv = new HashMap<>(); // create new HashMap
    private static final String VMINVFILE = "VMInventoryList.txt";
    private static final String DELIMITER = "::";

    public void writeInventory() throws VMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VMINVFILE));
        } catch (IOException e) {
            throw new VMPersistenceException("Unable to save vending machine data", e);
        }
        // creates list to save candy with the delimiter
        List<Candy> candyList = getAllCandyInventory();

        for (Candy candyChoice : candyList) {
            out.println(candyChoice.getCandyName() + DELIMITER
                    + candyChoice.getCost() + DELIMITER
                    + candyChoice.getVMInventory() + DELIMITER);
            out.flush(); // forces PrintWriter to write to file
        }
        out.close();    // close and flush stream    
    }

    public void getInventory() throws VMPersistenceException {
        Scanner sc;
        String currentLine;
        String[] currentTokens;

        try {           // create new scanner for reading in the inventory file
            sc = new Scanner(new BufferedReader(new FileReader(VMINVFILE)));
        } catch (FileNotFoundException e) {
            throw new VMPersistenceException("Unable to load inventory data", e);
        }
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine(); // read in next line from file
            currentTokens = currentLine.split(DELIMITER); // tokenize scanner
            Candy scanCandy = new Candy(currentTokens[0]);
            // set candy values in inventory
            scanCandy.setCost(new BigDecimal(currentTokens[1]));
            scanCandy.setVMInventory(Integer.parseInt(currentTokens[2]));
            // put selected candy into HashMap by candy name
            candyInv.put(scanCandy.getCandyName(), scanCandy);
        }
        sc.close(); // close scanner
    }

    @Override // edit and write candy to inventory
    public Candy editCandyInventory(String candyName, Candy candy) throws VMPersistenceException {
        Candy editCandy = candyInv.put(candyName, candy);
        writeInventory();
        return editCandy;
    }

    @Override // get candy from inventory
    public Candy getCandyInventory(String candyName) throws VMPersistenceException {
        getInventory();
        Candy getCandy = candyInv.get(candyName);
        return getCandy;
    }

    @Override // get inventory entire list
    public List<Candy> getAllCandyInventory() throws VMPersistenceException {
        getInventory();
        List<Candy> allCandyInventory = new ArrayList<>(candyInv.values());
        return allCandyInventory;
    }

    @Override // get in stock candy only from inventory stream
    public List<Candy> getOnlyCandyInStock() throws VMPersistenceException {
        getInventory();
        List<Candy> allCandyInventory = this.getAllCandyInventory();

        return allCandyInventory.stream()
                .filter(i -> i.getVMInventory() > 0)
                .collect(Collectors.toList());
    }

}
