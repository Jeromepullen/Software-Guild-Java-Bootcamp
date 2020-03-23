/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Customer;

/**
 *
 * @author jeromepullenjr
 */
//read file into collection object
//--------------------------------
//Get customerName
//Get customerRate
public interface DaoCustomer {

    public Customer getCustomer(String name) throws DaoPersistanceException;

    //add new customer
    Customer create(Customer customer) throws DaoPersistanceException;

    //finds all customers
    List<Customer> findAll() throws DaoPersistanceException;

    //finds customer that matches Id
    Customer findById(int customerId) throws DaoPersistanceException;

    //searches through the customer list
    List<Customer> searchCustomers(String input) throws DaoPersistanceException;

    //displays customer from the Id inputted by the user
    Customer displayOne(int customerId) throws DaoPersistanceException;

    List<Customer> getCustomers(String cust) throws DaoPersistanceException;

    List<Customer> getCustomerLastName(String cust) throws DaoPersistanceException;

    public Customer findByLastName(String cust) throws DaoPersistanceException;

    public List<Customer> allCustomers(String cust) throws DaoPersistanceException;

}
