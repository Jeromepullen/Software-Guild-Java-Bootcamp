/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class DaoImplAppointmentTest {

    private static final String DELIMITER = ",";

    private final DaoImplAppointment DIA = new DaoImplAppointment();
    //sets 100 years in the future
    private final LocalDate date = LocalDate.now().plusYears(100);
    private final String dateKey = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    private final String fileNameA = "data"
            + File.separator + "appointments"
            + File.separator + "appointments_" + dateKey + ".txt";

    private final File fileA = new File(fileNameA);

    private Appointment appointment1 = new Appointment();
    private Appointment appointment2 = new Appointment();
    private Appointment appointment3 = new Appointment();

    public DaoImplAppointmentTest() {
    }

    private Appointment createAppointment() {
        Appointment appointment = new Appointment();
        Customer customer = new Customer();
        Professional professional = new Professional();

        appointment.setCustomerId(5);
        appointment.setDentalProLastName("Johnson");
        appointment.setSpecialty(Specialty.DENTIST);
        appointment.setStartTime(LocalTime.MIN);
        appointment.setEndTime(LocalTime.NOON);
        appointment.setTotalCost(new BigDecimal(300));
        appointment.setNotes("Test notes");

        return appointment;
    }

    public void setUp() throws DaoPersistanceException {
        int customerId = 9000;
        //int customerId = 9001;
        //int customerId = 9002;

        appointment1 = createAppointment();
        appointment2 = createAppointment();
        appointment3 = createAppointment();

        appointment1.setCustomerId(customerId + 0);
        appointment2.setCustomerId(customerId + 1);
        appointment3.setCustomerId(customerId + 2);

        DIA.getAppointments(date).add(appointment1);
        DIA.getAppointments(date).add(appointment2);
        DIA.getAppointments(date).add(appointment3);

        DIA.getAllAppointments().put(dateKey, DIA.getAppointments(date));
    }

    public void tearDown() throws DaoPersistanceException {
        DIA.getAppointments(date).clear();
        DIA.getAllAppointments().remove(dateKey);
        fileA.delete();
    }

    @Test
    public void testAddAppointment() throws DaoPersistanceException {
        assertTrue(DIA.getAppointments(date).contains(appointment1));
        assertTrue(DIA.getAppointments(date).contains(appointment2));
        assertTrue(DIA.getAppointments(date).contains(appointment3));

        assertTrue(DIA.getAllAppointments().get(dateKey).contains(appointment1));
        assertTrue(DIA.getAllAppointments().get(dateKey).contains(appointment2));
        assertTrue(DIA.getAllAppointments().get(dateKey).contains(appointment3));

        Appointment appointmentA = createAppointment();
        Appointment appointmentB = createAppointment();
        Appointment appointmentC = createAppointment();

        try {
            //added appointment1, appointment2, and appointment3
            //	line 1	line 2	    line 3 of file
            DIA.addAppointment(appointmentA);
            DIA.addAppointment(appointmentB);
            DIA.addAppointment(appointmentC);
        } catch (DaoPersistanceException ex) {
            System.out.println("Unable to load file");
        }

        assertFalse(DIA.getAppointments(date).isEmpty());

        assertEquals(4, appointmentA.getCustomerId());
        assertEquals(5, appointmentB.getCustomerId());
        assertEquals(6, appointmentC.getCustomerId());
    }

    @Test
    public void testRemoveAppointment() {
        String removeAppointmentKey = DIA.getRemoveAppointmentKey();
        String appointment1OldName = appointment1.getCustomerName();
        String appointment2OldName = appointment2.getCustomerName();
        String appointment3OldName = appointment3.getCustomerName();

        try {
            DIA.removeAppointment(date, appointment1.getCustomerId());
            DIA.removeAppointment(date, appointment2.getCustomerId());
            DIA.removeAppointment(date, appointment3.getCustomerId());
        } catch (DaoPersistanceException ex) {
            System.out.println("Unable to remove appointment");
        }

        assertTrue(DIA.getAllAppointments().get(dateKey).get(0).getCustomerName()
                .equals(removeAppointmentKey + appointment1OldName));
        assertTrue(DIA.getAllAppointments().get(dateKey).get(1).getCustomerName()
                .equals(removeAppointmentKey + appointment2OldName));
        assertTrue(DIA.getAllAppointments().get(dateKey).get(2).getCustomerName()
                .equals(removeAppointmentKey + appointment3OldName));
    }

    @Test
    public void testGetAppointment() {
        assertEquals(appointment1, DIA.getAllAppointments().get(dateKey).get(0));
        assertEquals(appointment2, DIA.getAllAppointments().get(dateKey).get(1));
        assertEquals(appointment3, DIA.getAllAppointments().get(dateKey).get(2));
    }

    @Test
    public void testGetAppointments() {
        List<Appointment> tempList = new ArrayList();
        tempList.add(appointment1);
        tempList.add(appointment2);
        tempList.add(appointment3);

        assertEquals(tempList, DIA.getAllAppointments().get(dateKey));
    }

    @Test
    public void testCheckIfDateExists() {
        try {
            PrintWriter writer = new PrintWriter(fileA);
            //pass
        } catch (FileNotFoundException ex) {
            fail();
        }
        assertTrue(DIA.checkIfDateExists(date));

        LocalDate date2 = LocalDate.of(9999, 10, 10);

        assertFalse(DIA.checkIfDateExists(date2));
    }

    @Test
    public void testSaveAll() throws FileNotFoundException {
        try {
            DIA.saveAll();
        } catch (DaoPersistanceException ex) {
            System.out.println("Could not write to file");
        }

        assertTrue(DIA.getAllAppointments().size() > 0);

        for (String key : DIA.getAllAppointments().keySet()) {
            //build file path
            String filePath = "appointments_" + key + ".txt";

            //collect files
            List<String> listOfFiles = new ArrayList<>();
            File folder = new File("data" + File.separator + "appointments");
            File[] files = folder.listFiles();

            for (File f : files) {
                listOfFiles.add(f.getName());
            }

            //check if file exists
            boolean exists = false;

            for (int i = 0; i < listOfFiles.size(); i++) {
                if (listOfFiles.get(i).equals(filePath)) {
                    exists = true;
                }
            }
            assertTrue(exists);
        }

    }

}
