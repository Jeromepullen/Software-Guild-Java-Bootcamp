/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.time.LocalDate;
import java.util.List;
import model.Appointment;
import model.Professional;

/**
 *
 * @author jeromepullenjr
 */
//--------------------------------
//	Getters and Setters
public interface DaoAppointment {

    public Appointment addAppointment(Appointment appointment) throws DaoPersistanceException;

    public Appointment removeAppointment(LocalDate date, int custId) throws DaoPersistanceException;

    public Appointment editAppointment(LocalDate date, int custId, Appointment appointment) throws DaoPersistanceException;

    public List<Appointment> getAppointments(LocalDate date) throws DaoPersistanceException;

    public List<Appointment> getAppointmentsDentalPro(LocalDate date, String pro) throws DaoPersistanceException;

    public List<Appointment> getAppointmentsCustomer(LocalDate date, String cust) throws DaoPersistanceException;

    public List<Appointment> findAll() throws DaoPersistanceException;

    public List<Appointment> getAppointmentsCustomerDate(LocalDate date, int custId) throws DaoPersistanceException;

    public boolean delete(LocalDate date, int customerId) throws DaoPersistanceException;

    public boolean update(Appointment appointment) throws DaoPersistanceException;

}
