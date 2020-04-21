/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.VMDao;
import dao.VMDaoStub;
import dto.Candy;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
//public class PlantServiceTest {
//
//    private PlantService service = new PlantService(new PlantStubDao());
//
//    public PlantServiceTest() {
//    }
public class VMServiceLayerTest {

    private VMServiceLayer service;

//    private VMServiceLayer service = new VMServiceLayer(new VMDaoStub());
    public VMServiceLayerTest() {

        VMDao dao = new VMDaoStub();
        service = new VMServiceLayerImpl(dao);
    }

    /**
     * Test of getCandyInventory method, of class VMServiceLayer.
     */
    @Test
    public void getCandyInventory() throws Exception {
        Candy candy = new Candy("Hersheys");
        candy.setCost(new BigDecimal("1.00"));
        candy.setVMInventory(7);
    }

    /**
     * Test of inStockStatus method, of class VMServiceLayer.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInStockStatus() throws Exception {
        try {
            service.inStockStatus("Reeses Pieces");
        } catch (OutOfStockException e) {
        }
    }

    /**
     * Test of buyCandyInventory method, of class VMServiceLayer.
     */
    @Test
    public void testBuyCandyInventory() throws Exception {

        Candy candy = new Candy("Chips");
        candy.setCost(new BigDecimal("1.75"));
        candy.setVMInventory(11);
        BigDecimal userCash = new BigDecimal("2");
        service.buyCandyInventory(candy, userCash);
    }

    /**
     * Test of getAllCandyInventory method, of class VMServiceLayer.
     */
    @Test
    public void testGetAllCandyInventory() throws Exception {

        assertEquals(0, service.getAllCandyInventory().size());
    }

    /**
     * Test of getOnlyCandyInStock method, of class VMServiceLayer.
     */
    @Test
    public void testGetOnlyCandyInStock() throws Exception {

        assertEquals(0, service.getOnlyCandyInStock().size());
    }

}
