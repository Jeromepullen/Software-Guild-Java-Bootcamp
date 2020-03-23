/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Customer;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface CustomerDao //add new customer
{

    Customer create(Customer customer) throws CustomerDaoException;

    //deletes customer
    boolean delete(int customerId) throws CustomerDaoException;

    //finds all customers
    List<Customer> findAll() throws CustomerDaoException;

    //finds customer that matches Id
    Customer findById(int customerId) throws CustomerDaoException;

    //updates(edits) customer details
    boolean update(Customer customer) throws CustomerDaoException;

    //searches through the customer list
    List<Customer> searchCustomers(String input) throws CustomerDaoException;

    //displays customer from the Id inputted by the user
    Customer displayOne(int customerId) throws CustomerDaoException;

}
