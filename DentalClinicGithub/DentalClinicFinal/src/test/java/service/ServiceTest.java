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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Professional;
import model.Customer;
import model.Specialty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class ServiceTest {

    private DaoImplAppointment DIA = new DaoImplAppointment();
    private DaoImplProfessional DIP = new DaoImplProfessional();
    private DaoImplCustomer DIC = new DaoImplCustomer();
    private String mode = "Professional";
    private Service service = new Service(DIA, DIP, DIC, mode);

    private LocalDate date = LocalDate.now().plusYears(100);
    private String dateKey = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    private String fileName = "data"
            + File.separator + "appointments"
            + File.separator + "appointments_" + dateKey + ".txt";

    private File fileA = new File(fileName);

    private List<Appointment> appointments = new ArrayList<>();

    private Appointment appointment1 = new Appointment();
    private Appointment appointment2 = new Appointment();

    private int customerId1 = 8888;
    private int customerId2 = 9999;

    public ServiceTest() {
    }

    public void setUp() {
        Customer customer1 = new Customer();
        Professional professional1 = new Professional();

        appointment1.setCustomerId(50);
        appointment1.setDentalProLastName("Johnston");
        appointment1.setSpecialty(Specialty.ORAL_SURGEON);
        appointment1.setStartTime(LocalTime.MIN);
        appointment1.setEndTime(LocalTime.NOON);
        appointment1.setTotalCost(new BigDecimal(333));
        appointment1.setNotes("Test notes");

        Customer customer2 = new Customer();
        Professional professional2 = new Professional();

        appointment2.setCustomerId(5);
        appointment2.setDentalProLastName("Alison");
        appointment2.setSpecialty(Specialty.HYGIENIST);
        appointment2.setStartTime(LocalTime.NOON);
        appointment2.setEndTime(LocalTime.NOON);
        appointment2.setTotalCost(new BigDecimal(40));
        appointment2.setNotes("Test notes 2");

        appointments.add(appointment1);
        appointments.add(appointment2);
    }

    public void tearDown() {
        appointments.remove(appointment1);
        appointments.remove(appointment2);
        fileA.delete();
    }

    @Test
    public void testAddAppointment() {
        try {
            service.addAppointment(appointments.get(0));

            assertEquals(new BigDecimal("175.00"), appointment1.getProfessional().getHourlyRate());

            assertEquals(new BigDecimal("100.00 "), appointment2.getProfessional().getHourlyRate());

        } catch (DaoPersistanceException ex) {
            System.out.println(ex.getMessage() + "\t" + ServiceTest.class.getName());
            fail();
        }
    }

    public void testGetAppointments() {
        try {
            PrintWriter writer = new PrintWriter(fileA);
            //pass
            writer.close();
        } catch (FileNotFoundException ex) {
            fail();
        }

        try {
            service.getAppointments(date);
            //pass
        } catch (DaoPersistanceException | DataValidationException ex) {
            fail();
        }
    }

    public void testReplaceAppointmentInList() {
        DIA.getAllAppointments().put(dateKey, appointments);

        try {
            service.replaceAppointmentInList(LocalDate.MAX, appointment1, appointment2);
            //pass
        } catch (DaoPersistanceException ex) {
            fail();
        }

        try {
            assertEquals(2, DIA.getAppointments(date).size());
            assertEquals(DIA.getAppointments(date).get(0).getTotalCost(),
                    DIA.getAppointments(date).get(1).getTotalCost());
            //pass
        } catch (DaoPersistanceException ex) {
            fail();
        }
    }

}
