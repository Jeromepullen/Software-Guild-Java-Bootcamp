///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Dao;
//
//import Model.Customer;
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNull;
//import static junit.framework.Assert.fail;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// *
// * @author jeromepullenjr
// */
//public class CustomerDaoImplTest {
//
//    private CustomerDaoImplTest dit = new CustomerDaoImplTest();
//    private int size = dit.customers.size();
//
//    private Customer customer1;
//    private Customer customer2;
//    private Customer customer3;
//
//    @Before
//    public void setUp() {
//        customer1 = new Customer();
//        customer1.setFirstName("Bob");
//        customer1.setLastName("Myers");
//
//        customer2 = new Customer();
//        customer2.setFirstName("Chad");
//        customer2.setLastName("Wilson");
//
//        customer3 = new Customer();
//        customer3.setFirstName("Alex");
//        customer3.setLastName("Olson");
//
//        dit.customers.add(customer1);
//        dit.customers.add(customer2);
//        dit.customers.add(customer3);
//    }
//
//    @After
//    public void tearDown() {
//        dit.customers.remove(customer1);
//        dit.customers.remove(customer2);
//        dit.customers.remove(customer3);
//    }
//
//    @Test
//    public void testGetCustomer() {
//        try {
//            assertEquals(customer1, dit.getCustomer("AA"));
//            assertEquals(customer2, dit.getCustomer("BB"));
//            assertEquals(customer3, dit.getCustomer("CC"));
//            //pass
//        } catch (DaoPersistanceException ex) {
//            fail(ex.getMessage());
//        }
//    }
//
//    @Test
//    public void testBadInput() {
//        try {
//            assertNull(dit.getCustomer("ABCD"));
//            fail();
//        } catch (DaoPersistanceException ex) {
//            //pass
//        }
//    }
//
//}
//
//}
