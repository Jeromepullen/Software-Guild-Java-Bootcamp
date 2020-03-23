/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Appointment;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface AppointmentDao {

    //add new appointment
    Appointment create(Appointment appointment) throws AppointmentDaoException;

    //deletes appointment
    boolean delete(int appointmentId) throws AppointmentDaoException;

    //finds all appointments
    List<Appointment> findAll() throws AppointmentDaoException;

    //finds appointment that matches Id
    Appointment findByDate(LocalDate appointmentDate) throws AppointmentDaoException;

    //updates(edits) appointment details
    boolean update(Appointment appointment) throws AppointmentDaoException;

    //searches through the appointment list
    List<Appointment> searchAppointments(String input) throws AppointmentDaoException;

    //displays appointment from the Id inputted by the user
    Appointment displayOne(int appointmentId) throws AppointmentDaoException;

    List<Appointment> getAppointments(LocalDate date) throws AppointmentDaoException;

}
