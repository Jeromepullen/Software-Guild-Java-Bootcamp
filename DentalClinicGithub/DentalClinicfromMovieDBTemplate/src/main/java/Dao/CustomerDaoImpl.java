/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Customer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public class CustomerDaoImpl implements CustomerDao {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data" + File.separator + "customers.txt";

    @Override
    public Customer create(Customer customer) throws CustomerDaoException {
        int customerId = 0;
        List<Customer> all = findAll();
        for (Customer b : all) {
            customerId = Math.max(customerId, b.getCustomerId());
        }
        customer.setCustomerId(customerId + 1);
        all.add(customer);

        save(all);

        return customer;
    }

    @Override
    public boolean update(Customer customer) throws CustomerDaoException {
        List<Customer> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCustomerId() == customer.getCustomerId()) {
                all.set(i, customer);
                save(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(int customerId) throws CustomerDaoException {
        List<Customer> all = findAll();
        for (Customer b : all) {
            if (b.getCustomerId() == customerId) {
                all.remove(b);
                save(all);
                return true;
            }
        }
        return false;
    }

    private void save(List<Customer> customers) throws CustomerDaoException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Customer b : customers) {
                writer.println(marshall(b));
            }
        } catch (IOException ex) {
            throw new CustomerDaoException(ex.getMessage(), ex);
        }
    }

    private String marshall(Customer b) {
        return b.getCustomerId() + DELIMITER
                + b.getFirstName() + DELIMITER
                + b.getLastName() + DELIMITER
                + b.getDOB();
    }

    private Customer unmarshall(String line) {

        String[] tokens = line.split(DELIMITER);
        if (tokens.length != 3) {
            return null;
        }

        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(tokens[0]));
        customer.setFirstName(tokens[1]);
        customer.setLastName(tokens[2]);
        customer.setDOB(LocalDate.parse(tokens[3]));
        return customer;
    }

    @Override
    public Customer findById(int customerId) throws CustomerDaoException {
        for (Customer b : findAll()) {
            if (b.getCustomerId() == customerId) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findAll() throws CustomerDaoException {
        ArrayList<Customer> all = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = unmarshall(line);
                if (customer != null) {
                    all.add(customer);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new CustomerDaoException(ex.getMessage(), ex);
        }
        return all;
    }

    @Override
    public Customer displayOne(int customerId) throws CustomerDaoException {
        for (Customer o : findAll()) {
            if (o.getCustomerId() == customerId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public List<Customer> searchCustomers(String input) throws CustomerDaoException {

        List<Customer> matches = new ArrayList<>();
        for (Customer customer : findAll()) {
            String title = customer.getFirstName();
            if (title.contains(input)) {
                matches.add(customer);
            }
        }
        return matches;
    }

}
