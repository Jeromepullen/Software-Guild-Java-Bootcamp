/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Candy;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class VMDaoTest {

    VMDao dao = new VMDaoFileImpl();

    public VMDaoTest() {
    }

    /**
     * Test of getAllCandyInventory method, of class VMDao.
     */
    @Test
    public void testGetAllCandyInventory() throws Exception {
        Candy candy = new Candy("Mounds");
        candy.setVMInventory(4);
        candy.setCost(new BigDecimal("1.00"));

        dao.editCandyInventory(candy.getCandyName(), candy);

        Candy candy1 = new Candy("Corn Nuts");
        candy1.setVMInventory(4);
        candy1.setCost(new BigDecimal("2.00"));

        dao.editCandyInventory(candy1.getCandyName(), candy1);

        assertEquals(2, (int) dao.getAllCandyInventory().size());
    }

    /**
     * Test of getOnlyCandyInStock method, of class VMDao.
     */
    @Test
    public void testGetOnlyCandyInStock() throws Exception {
        Candy candy = new Candy("Snickers");
        candy.setVMInventory(8);
        candy.setCost(new BigDecimal("1.00"));

        dao.editCandyInventory(candy.getCandyName(), candy);

        Candy candy1 = new Candy("Reeses Pieces");
        candy1.setVMInventory(8);
        candy1.setCost(new BigDecimal("1.00"));

        dao.editCandyInventory(candy1.getCandyName(), candy1);

        assertEquals(2, (int) dao.getOnlyCandyInStock().size());
    }

}
