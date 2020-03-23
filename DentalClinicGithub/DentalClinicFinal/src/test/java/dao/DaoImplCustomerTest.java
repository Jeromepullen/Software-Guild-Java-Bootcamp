/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import model.Customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class DaoImplCustomerTest {

    private DaoImplCustomer DIC = new DaoImplCustomer();
    private int size = DIC.customers.size();

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    public void setUp() {
        customer1 = new Customer();
        customer1.setCustomerId(20);
        customer1.setFirstName("Greg");
        customer1.setLastName("Peters");
        customer1.setDOB(LocalDate.MIN);

        customer2 = new Customer();
        customer2.setCustomerId(30);
        customer2.setFirstName("Pat");
        customer2.setLastName("Olson");
        customer2.setDOB(LocalDate.EPOCH);

        customer3 = new Customer();
        customer3.setCustomerId(44);
        customer3.setFirstName("John");
        customer3.setLastName("Anderson");
        customer3.setDOB(LocalDate.MAX);

        DIC.customers.add(customer1);
        DIC.customers.add(customer2);
        DIC.customers.add(customer3);
    }

    public void tearDown() {
        DIC.customers.remove(customer1);
        DIC.customers.remove(customer2);
        DIC.customers.remove(customer3);
    }

    @Test
    public void testGetCustomer() {
        try {
            assertEquals(customer1, DIC.getCustomer("AA"));
            assertEquals(customer2, DIC.getCustomer("BB"));
            assertEquals(customer3, DIC.getCustomer("CC"));
            //pass
        } catch (DaoPersistanceException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testBadInput() {
        try {
            assertNull(DIC.getCustomer("ABCD"));
            fail();
        } catch (DaoPersistanceException ex) {
            //pass
        }
    }

}
