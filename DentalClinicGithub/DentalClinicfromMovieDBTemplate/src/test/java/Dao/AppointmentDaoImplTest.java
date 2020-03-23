/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Appointment;
import Model.Customer;
import Model.Professional;
import Model.Specialty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class AppointmentDaoImplTest {

    private static final String DELIMITER = ",";

    private final AppointmentDaoImpl dio = new AppointmentDaoImpl();
    //sets 100 years in the future
    private final LocalDate date = LocalDate.now().plusYears(100);
    private final String dateKey = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    private final String fileNameA = "data"
            + File.separator + "appointments"
            + File.separator + "Appointments_" + dateKey + ".txt";

    private final File fileA = new File(fileNameA);

    private Appointment appointment1 = new Appointment();
    private Appointment appointment2 = new Appointment();
    private Appointment appointment3 = new Appointment();

    public AppointmentDaoImplTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    private Appointment createAppointment() {
        Appointment appointment = new Appointment();
        Customer customer = new Customer();
        Professional professional = new Professional();
//        Specialty specialty = new Specialty();

        customer.setCustomerId(0);
        professional.setLastName("Paul");
        specialty.(Specialty.DENTIST);
        appointment.setStartTime(LocalTime.MIN);
        appointment.setEndTime(LocalTime.NOON);
        appointment.setAppointmentTotal(new BigDecimal("175"));
        appointment.setNotes("Test Notes");

        appointment.setCustomer(customer);
        appointment.setProfessional(professional);

        return appointment;
    }

    @Test
    public void testAddAppointment() {
        assertTrue(dio.getAppointmentsListForToday().contains(appointment1));
        assertTrue(dio.getAppointmentsListForToday().contains(appointment2));
        assertTrue(dio.getAppointmentsListForToday().contains(appointment3));

        assertTrue(dio.getAllAppointments().get(dateKey).contains(appointment1));
        assertTrue(dio.getAllAppointments().get(dateKey).contains(appointment2));
        assertTrue(dio.getAllAppointments().get(dateKey).contains(appointment3));

        Appointment appointmentA = createAppointment();
        Appointment appointmentB = createAppointment();
        Appointment appointmentC = createAppointment();

        appointmentA.setDate("12/30/2019");
        appointmentB.setDate("02/11/2020");
        appointmentC.setDate("11/30/2019");

        try {
            //added appointment1, appointment2, and appointment3
            //	line 1	line 2	    line 3 of file
            dio.addAppointment(appointmentA);
            dio.addAppointment(appointmentB);
            dio.addAppointment(appointmentC);
        } catch (DaoPersistanceException ex) {
            System.out.println("Unable to load file");
        }

        assertFalse(dio.getAppointmentsListForToday().isEmpty());

        assertEquals(4, appointmentA.getDate());
        assertEquals(5, appointmentB.getDate());
        assertEquals(6, appointmentC.getDate());
    }

    @Test
    public void testRemoveAppointment() {
        String removeAppointmentKey = dio.getRemoveAppointmentKey();
        String appointment1OldName = (String) appointment1.getCustomerName();
        String appointment2OldName = (String) appointment2.getCustomerName();
        String appointment3OldName = (String) appointment3.getCustomerName();

        try {
            dio.removeAppointment(date, 0);
            dio.removeAppointment(date, 0);
            dio.removeAppointment(date, 0);
        } catch (DaoPersistanceException ex) {
            System.out.println("Unable to remove appointment");
        }

        assertTrue(dio.getAllAppointments().get(dateKey).get(0).getCustomerName()
                .equals(removeAppointmentKey + appointment1OldName));
        assertTrue(dio.getAllAppointments().get(dateKey).get(1).getCustomerName()
                .equals(removeAppointmentKey + appointment2OldName));
        assertTrue(dio.getAllAppointments().get(dateKey).get(2).getCustomerName()
                .equals(removeAppointmentKey + appointment3OldName));
    }

    @Test
    public void testGetAppointment() {
        assertEquals(appointment1, dio.getAllAppointments().get(dateKey).get(0));
        assertEquals(appointment2, dio.getAllAppointments().get(dateKey).get(1));
        assertEquals(appointment3, dio.getAllAppointments().get(dateKey).get(2));
    }

    @Test
    public void testGetAppointments() {
        List<Appointment> tempList = new ArrayList();
        tempList.add(appointment1);
        tempList.add(appointment2);
        tempList.add(appointment3);

        assertEquals(tempList, dio.getAllAppointments().get(dateKey));
    }

    @Test
    public void testCheckIfDateExists() {
        try {
            PrintWriter writer = new PrintWriter(fileA);
            //pass
        } catch (FileNotFoundException ex) {
            fail();
        }
        assertTrue(dio.checkIfDateExists(date));

        LocalDate date2 = LocalDate.of(9999, 10, 10);

        assertFalse(dio.checkIfDateExists(date2));
    }

    @Test
    public void testSaveAll() {
        try {
            dio.saveAll();
        } catch (DaoPersistanceException ex) {
            System.out.println("Could not write to file");
        }

        assertTrue(dio.getAllAppointments().size() > 0);

        for (String key : dio.getAllAppointments().keySet()) {
            //build file path
            String filePath = "Appointments_" + key + ".txt";

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

    /**
     * Test of create method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Appointment appointment = null;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        Appointment expResult = null;
        Appointment result = instance.create(appointment);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Appointment appointment = null;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        boolean expResult = false;
        boolean result = instance.update(appointment);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        int appointmentId = 0;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        boolean expResult = false;
        boolean result = instance.delete(appointmentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findByDate method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testFindByDate() throws Exception {
        System.out.println("findByDate");
        LocalDate appointmentDate = null;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        Appointment expResult = null;
        Appointment result = instance.findByDate(appointmentDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        List<Appointment> expResult = null;
        List<Appointment> result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayOne method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testDisplayOne() throws Exception {
        System.out.println("displayOne");
        int appointmentId = 0;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        Appointment expResult = null;
        Appointment result = instance.displayOne(appointmentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchAppointments method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testSearchAppointments() throws Exception {
        System.out.println("searchAppointments");
        String input = "";
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        List<Appointment> expResult = null;
        List<Appointment> result = instance.searchAppointments(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class AppointmentDaoImpl.
     */
    @org.junit.jupiter.api.Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        int arg0 = 0;
        AppointmentDaoImpl instance = new AppointmentDaoImpl();
        Appointment expResult = null;
        Appointment result = instance.findById(arg0);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

}
