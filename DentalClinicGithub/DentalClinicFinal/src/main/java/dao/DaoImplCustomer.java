/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Customer;

/**
 *
 * @author jeromepullenjr
 */
//read file into collection object
//--------------------------------
public class DaoImplCustomer implements DaoCustomer {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data"
            + File.separator + "customers"
            + File.separator + "customers.txt";

    public List<Customer> customers = new ArrayList<>();

    public String getFilePath() {
        return FILE_NAME;
    }

    @Override
    public Customer create(Customer customer) throws DaoPersistanceException {
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

//    @Override
//    public boolean update(Customer customer) throws DaoPersistanceException {
//        List<Customer> all = findAll();
//        for (int i = 0; i < all.size(); i++) {
//            if (all.get(i).getCustomerId() == customer.getCustomerId()) {
//                all.set(i, customer);
//                save(all);
//                return true;
//            }
//        }
//
//        return false;
//    }
    private void save(List<Customer> customers) throws DaoPersistanceException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Customer b : customers) {
                writer.println(marshall(b));
            }
        } catch (IOException ex) {
            throw new DaoPersistanceException(ex.getMessage(), ex);
        }
    }

    private String marshall(Customer C) {
        return C.getCustomerId() + DELIMITER
                + C.getFirstName() + DELIMITER
                + C.getLastName() + DELIMITER
                + C.getDOB();

    }

    private Customer unmarshall(String line) {

        List<Customer> customers = new ArrayList();

        String[] tokens = line.split(DELIMITER);
        if (tokens.length != 4) {
            return null;
        }

        Customer customer = new Customer();

        customer.setCustomerId(Integer.parseInt(tokens[0]));
        customer.setFirstName(tokens[1]);
        customer.setLastName(tokens[2]);
        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        customer.setDOB(LocalDate.parse(tokens[3], Formatter));
        return customer;
    }

//    @Override
//    public List<Customer> getCustomers(String cust) throws DaoPersistanceException {
//        
//        List<Customer> all = findAll().get(cust);
//        
//        return all;
//    }
    @Override
    public Customer findById(int customerId) throws DaoPersistanceException {
        for (Customer b : findAll()) {
            if (b.getCustomerId() == customerId) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<Customer> getCustomerLastName(String cust) throws DaoPersistanceException {

        return getCustomers(cust).stream()
                .filter(A -> A.getLastName().equals(cust))
                .collect(Collectors.toList());

    }

    @Override
    public List<Customer> findAll() throws DaoPersistanceException {
        ArrayList<Customer> all = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            //you want to read the first line and throw it away
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Customer customer = unmarshall(line);
                if (customer != null) {
                    all.add(customer);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new DaoPersistanceException(ex.getMessage(), ex);
        }
        return all;
    }

    @Override
    public Customer displayOne(int customerId) throws DaoPersistanceException {
        for (Customer o : findAll()) {
            if (o.getCustomerId() == customerId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public List<Customer> searchCustomers(String cust) throws DaoPersistanceException {

        List<Customer> matches = new ArrayList<>();
        for (Customer customer : findAll()) {
            String fullCust = customer.getLastName();
            if (fullCust.contains(cust)) {
                matches.add(customer);
            }
        }
        return matches;
    }

    @Override
    public Customer getCustomer(String name) throws DaoPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Customer> getCustomers(String cust) throws DaoPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer findByLastName(String cust) throws DaoPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Customer> allCustomers(String cust) throws DaoPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
