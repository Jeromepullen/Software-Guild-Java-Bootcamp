/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DaoImplAppointment;
import dao.DaoImplProfessional;
import dao.DaoImplCustomer;
import dao.DaoPersistanceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Appointment;
import model.Professional;
import model.Customer;
import model.Response;
import model.Specialty;

/**
 *
 * @author jeromepullenjr
 */
public class Service {

    private final DaoImplAppointment DIA;
    private final DaoImplProfessional DIP;
    private final DaoImplCustomer DIC;
    private final String mode;

    public Service(DaoImplAppointment DIA, DaoImplProfessional DIP, DaoImplCustomer DIC, String mode) {
        this.DIA = DIA;
        this.DIP = DIP;
        this.DIC = DIC;
        this.mode = mode;

    }

//    public List<Professional> findAllProfessionalBySpecialty(Specialty specialty) {
//        return Specialty.valueOf(specialty);
//    }
    public BigDecimal calculateCost(Appointment appointment) throws DaoPersistanceException {
        Response response = new Response();
        LocalTime start = appointment.getStartTime();
        LocalTime end = appointment.getEndTime();
        String lastName = appointment.getDentalProLastName();
        Professional professional = null;

        professional = DIP.findByProfessionalLastName2(lastName);

        if (start == null || end == null || professional == null) {
            return BigDecimal.ZERO;
        }
        long m = start.until(end, ChronoUnit.MINUTES);
        BigDecimal minutes = new BigDecimal(m);
        BigDecimal hours = minutes.divide(BigDecimal.valueOf(m), 2, RoundingMode.HALF_UP);
        return professional.getHourlyRate()
                .multiply(hours)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private boolean timeIsOutOfBounds(Appointment newAppointment) throws DaoPersistanceException {
        boolean isSaturday = newAppointment.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY);
        boolean inSaturdayHours = (newAppointment.getStartTime().compareTo(LocalTime.parse("08:30")) > 0)
                && (newAppointment.getEndTime().compareTo(LocalTime.parse("12:30")) < 0);
        boolean isSunday = newAppointment.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isDuringBuisnessHours = (newAppointment.getStartTime().compareTo(LocalTime.parse("07:30")) > 0)
                && (newAppointment.getEndTime().compareTo(LocalTime.parse("18:00")) < 0);
        boolean onLunch = (newAppointment.getStartTime().compareTo(LocalTime.parse("12:30")) > 0)
                && (newAppointment.getEndTime().compareTo(LocalTime.parse("13:00")) < 0);
        boolean illegalTime = true;
        if (isSunday) {
            throw new DaoPersistanceException("Cannot schedule appointment on a Sunday.");
        }
        if ((isSaturday && !inSaturdayHours) || !isDuringBuisnessHours) {
            throw new DaoPersistanceException("Cannot schedule appointment outside of buisness hours.");
        } else if (onLunch) {
            throw new DaoPersistanceException("Cannot schedule appointment during lunch hours.");
        } else {
            illegalTime = false;
        }
        return illegalTime;
    }

    public boolean tooMuchTimeForPro(Appointment appointment) throws DaoPersistanceException {
        double professionalMinTime = appointment.getProfessional().getSpecialty().getMinVisit();
        double professionalMaxTime = appointment.getProfessional().getSpecialty().getMaxVisit();
        double totalProTime = professionalMaxTime - professionalMinTime;
        double appointmentStartTime = new Double(appointment.getStartTime().toSecondOfDay() / 60); // will give time in minutes
        double appointmentEndTime = new Double(appointment.getEndTime().toSecondOfDay() / 60); // will give minutes
        double totalAppointmentTime = appointmentEndTime - appointmentStartTime;
        boolean tooMuchTime = totalProTime - totalAppointmentTime < 0;
        boolean illegalTime = true;
        if (tooMuchTime) {
            throw new DaoPersistanceException("a " + appointment.getProfessional().getSpecialty().toString().toLowerCase()
                    + " cannot be scheduled for this amount of time");
        } else {
            illegalTime = false;
        }
        return illegalTime;
    }

    public boolean twoOfTheSameProOnOneDay(Appointment appointment) throws DaoPersistanceException {
        LocalDate appointmentDate = appointment.getDate();
        Specialty s = appointment.getProfessional().getSpecialty();
        int id = appointment.getCustomerId(); // for debugging only
        List<Appointment> appointmentsByDayAndCustomer = DIA.getAppointmentsCustomerDate(appointmentDate, appointment.getCustomerId());
        long numberOfAppointmentsWithProfessional = appointmentsByDayAndCustomer.stream()
                .filter(i -> i.getSpecialty().equals(s)).count();
        boolean twoOfSame = true;
        if (numberOfAppointmentsWithProfessional > 1) {
            throw new DaoPersistanceException("This patient is already scheduled with a "
                    + appointment.getProfessional().getSpecialty().toString().toLowerCase() + " today");
        } else {
            twoOfSame = false;
        }
        return twoOfSame;
    }

    public boolean proIsDoubleBooked(Appointment appointment) throws DaoPersistanceException {
        LocalTime startTime = appointment.getStartTime();
        LocalTime endTime = appointment.getEndTime();
        String lastName = appointment.getDentalProLastName(); // for debugging only
        List<Appointment> appointments = DIA.getAppointmentsDentalPro(appointment.getDate(), lastName);
        boolean doubleBooked = true;
        for (Appointment a : appointments) {
            if (startTime.compareTo(a.getStartTime()) > 0 && startTime.compareTo(a.getEndTime()) < 0) {  // means the startTime is before the one it's being compared to
                throw new DaoPersistanceException("This " + appointment.getSpecialty().toString().toLowerCase()
                        + " is already booked during this time slot.  Please try again.");
            } else if (endTime.compareTo(a.getStartTime()) > 0 && endTime.compareTo(a.getEndTime()) < 0) {
                throw new DaoPersistanceException("This " + appointment.getSpecialty().toString().toLowerCase()
                        + " is already booked during this time slot.  Please try again.");
            } else if (startTime.compareTo(a.getStartTime()) < 0 && endTime.compareTo(a.getEndTime()) > 0) {
                throw new DaoPersistanceException("This " + appointment.getSpecialty().toString().toLowerCase()
                        + " is already booked during this time slot.  Please try again.");
            } else if (startTime.compareTo(a.getStartTime()) == 0 && endTime.compareTo(a.getEndTime()) == 0) {
                throw new DaoPersistanceException("This " + appointment.getSpecialty().toString().toLowerCase()
                        + " is already booked during this time slot.  Please try again.");
            } else {
                doubleBooked = false;
            }
        }
        return doubleBooked;
    }

    public void replaceAppointmentInList(LocalDate date, Appointment appointment1, Appointment appointment2) throws DaoPersistanceException {
        Map<String, List<Appointment>> map = DIA.getAllAppointments();

        //do caculations on newly edited appointment
        appointment2 = calculateAppointment(appointment2);

        String dateKey = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        int index = map.get(dateKey).indexOf(appointment1);
        map.get(dateKey).remove(index);
        map.get(dateKey).add(appointment2);
    }

    public Appointment addAppointment(Appointment appointment) throws DaoPersistanceException {
        appointment = calculateAppointment(appointment);
        return DIA.addAppointment(appointment);
    }

    public Appointment removeAppointment(LocalDate date, int custId) throws DaoPersistanceException {
        return DIA.removeAppointment(date, custId);
    }

    public List<Appointment> getAppointmentsDentalPro(LocalDate date, String pro) throws DaoPersistanceException {
        return DIA.getAppointmentsDentalPro(date, pro);
    }

    public List<Appointment> getAppointmentsCustomerDate(LocalDate date, int custId) throws DaoPersistanceException {
        return DIA.getAppointmentsCustomerDate(date, custId);
    }

    public List<Customer> getCustomers(String cust) throws DaoPersistanceException {
        return DIC.searchCustomers(cust);
    }

    public List<Appointment> getAppointments(LocalDate date) throws DaoPersistanceException, DataValidationException {
        if (checkDateExists(date) == false) {
            throw new DataValidationException("Date not found.");
        }

        return DIA.getAppointments(date);
    }

    public List<Appointment> getAppointmentsCustomer(LocalDate date, String cust) throws DaoPersistanceException, DataValidationException {
        if (checkDateExists(date) == false) {
            throw new DataValidationException("Date not found.");
        }

        return DIA.getAppointmentsCustomer(date, cust);
    }

    public Response addNewAppointment(Appointment appointment) {
        Response response = new Response();
        try {
            DIA.addAppointment(appointment);
        } catch (DaoPersistanceException ex) {
            response.hasError();
        }
        try {
            if (!proIsDoubleBooked(appointment)) {
                response.addError("error!");
            }
        } catch (DaoPersistanceException ex) {
            response.hasError();
        }
        try {
            if (!timeIsOutOfBounds(appointment)) {
                response.addError("error!");
            }
        } catch (DaoPersistanceException ex) {
            response.hasError();
        }
        try {
            if (!tooMuchTimeForPro(appointment)) {
                response.addError("error!");
            }
        } catch (DaoPersistanceException ex) {
            response.hasError();
        }
        try {
            if (!twoOfTheSameProOnOneDay(appointment)) {
                response.addError("error!");
            }
        } catch (DaoPersistanceException ex) {
            response.hasError();
        }
        return response;
    }

    public List<Professional> getProfessionals() throws DaoPersistanceException {
        return DIP.getProfessionals();
    }

    public void checkCustomerExists(String name) throws DaoPersistanceException {
        DIC.getCustomer(name);
    }

    public void checkProfessionalExists(String type) throws DaoPersistanceException {
        DIP.getProfessional(type);
    }

    public boolean checkDateExists(LocalDate date) {
        return DIA.checkIfDateExists(date);
    }

    private Appointment calculateAppointment(Appointment appointment) throws DaoPersistanceException {
        //acquired customer information
        String customerName = appointment.getCustomer().getName();
        Customer customer = DIC.getCustomer(customerName);

        appointment.setCustomer(customer);

        //acquires professional information
        String professionalType = appointment.getProfessional().getType();
        Professional professional = DIP.getProfessional(professionalType);

        appointment.setProfessional(professional);

        //caculates cost fields
        professional.getHourlyRate();
        appointment.getStartTime();
        appointment.getEndTime();

        appointment.getTotalCost();

        return appointment;
    }

    public List<Professional> findBySpecialty(LocalDate date, String specialty) throws DaoPersistanceException {

        return (List<Professional>) getProfessionals().stream()
                .filter(A -> A.getSpecialty().equals(Specialty.valueOf(specialty)))
                .collect(Collectors.toList());

    }

    public Appointment editAppointment(LocalDate date, int custId, Appointment appointment) throws DaoPersistanceException {
        return DIA.editAppointment(date, custId, appointment);
    }

// public List<Professional> findAllProfessionalBySpecialty(Specialty specialty) {
//        try {
//            return findBySpecialty(specialty);
//        } catch (DaoPersistanceException ex) {
//            Logger.getLogger(class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return new ArrayList<>();
//    }
//    public List<Professional> findAllProfessionalBySpecialty(Specialty specialty) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
