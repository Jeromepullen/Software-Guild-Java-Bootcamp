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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import model.Appointment;
import model.Specialty;

/**
 *
 * @author jeromepullenjr
 */
public class DaoImplAppointment implements DaoAppointment {

    private static final String todaysDate = LocalDateTime.now()
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    static final String FILE_NAME_FOR_TODAY = "data"
            + File.separator + "appointments"
            + File.separator + "appointments_" + todaysDate + ".txt";

    private static final String DELIMITER = ",";
    private static final String REMOVE_APPOINTMENT = "[CANCELED] ";

    private Map<String, List<Appointment>> allAppointments = new TreeMap<>();
//    private List<Appointment> appointmentsListForToday = new ArrayList<>();

    public DaoImplAppointment() {
    }

    //==========================================================================
    //	    Getters and Setters
    //==========================================================================
    public String getRemoveAppointmentKey() {
        return REMOVE_APPOINTMENT;
    }

    public Map<String, List<Appointment>> getAllAppointments() {
        return allAppointments;
    }

//    public List<Appointment> getAppointmentsListForToday() {
//        return appointmentsListForToday;
//    }
    //==========================================================================
    //	    Overrides
    //==========================================================================
    @Override
    public Appointment addAppointment(Appointment appointment) throws DaoPersistanceException {
//        String dateKey = parseFileNameForDate(FILE_NAME_FOR_TODAY);
//        readFile(FILE_NAME_FOR_TODAY);
//
//        appointmentsListForToday.add(appointment);
//        allAppointments.put(dateKey, appointmentsListForToday);
//
//        int customerId = allAppointments.get(dateKey).size();
//        appointment.setcustomerId(customerId);

        return appointment;
    }

//Allow a Dental Professional or User to add notes to the Appointment or change its total cost.
    @Override
    public boolean delete(LocalDate date, int custId) throws DaoPersistanceException {
        List<Appointment> all = findAll();

        for (Appointment A : all) {
            if (A.getCustomerId() == custId) {
                all.remove(A);
                save(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public Appointment removeAppointment(LocalDate date, int custId) throws DaoPersistanceException {
        String dateKey = convertDateToString(date);
        readFile(buildFileName(dateKey));
        List<Appointment> allAppointments = findAll();

        for (Appointment A : allAppointments) {
            if (A.getCustomerId() == custId) {
                allAppointments.remove(A);
                save(allAppointments);
                return A;
            }
        }
        throw new DaoPersistanceException("Unable to find appointment");
    }

    @Override
    public Appointment editAppointment(LocalDate date, int custId, Appointment appointment) throws DaoPersistanceException {
        String dateKey = convertDateToString(date);
        readFile(buildFileName(dateKey));
        List<Appointment> allAppointments = findAll();

        for (int i = 0; i < allAppointments.size(); i++) {
            if (allAppointments.get(i).getCustomerId() == custId) {
                allAppointments.set(i, appointment);
                save(allAppointments);
                return appointment;
            }
        }
        throw new DaoPersistanceException("Unable to find appointment");
    }

    @Override
    public boolean update(Appointment appointment) throws DaoPersistanceException {
        List<Appointment> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCustomerId() == appointment.getCustomerId()) {
                all.set(i, appointment);
                save(all);
                return true;
            }
        }

        return false;
    }
//ADD THE HEADER BACK INTO THE FILE WHEN YOU WRITE TO IT

    private String marshall(Appointment A) {

        return A.getCustomerId() + DELIMITER
                + A.getDentalProLastName() + DELIMITER
                + A.getSpecialty() + DELIMITER
                + A.getStartTime() + DELIMITER
                + A.getEndTime() + DELIMITER
                + A.getTotalCost() + DELIMITER
                + A.getNotes();
    }

    private void save(List<Appointment> appointment) throws DaoPersistanceException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME_FOR_TODAY))) {
            writer.println(
                    "customer_id,dental_pro_lastname,specialty,start_time,end_time,total_cost,notes");

            for (Appointment A : appointment) {
                writer.println(marshall(A));
            }
        } catch (IOException ex) {
            throw new DaoPersistanceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Appointment> getAppointments(LocalDate date) throws DaoPersistanceException {

        String dateKey = convertDateToString(date);
        readFile(buildFileName(dateKey));

        List<Appointment> filteredList = allAppointments.get(dateKey);

        return filteredList;
    }

    @Override
    public List<Appointment> getAppointmentsDentalPro(LocalDate date, String pro) throws DaoPersistanceException {

        return getAppointments(date).stream()
                .filter(A -> A.getDentalProLastName().equals(pro))
                .collect(Collectors.toList());

    }

    @Override
    public List<Appointment> getAppointmentsCustomerDate(LocalDate date, int custId) throws DaoPersistanceException {

        return getAppointments(date).stream()
                .filter(A -> A.getCustomerId()
                == (custId)
                )
                .collect(Collectors.toList());

    }

//    public void addAppointment(Appointment appointment, LocalDate date) throws DaoPersistanceException {
//        String dateKey = convertDateToString(date);
//        readFile(buildFileName(dateKey));
//        allAppointments.(appointment);
//        allAppointments.put(dateKey, appointmentsListForToday);
//    }
    @Override
    public List<Appointment> findAll() throws DaoPersistanceException {
        ArrayList<Appointment> all = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_FOR_TODAY))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Appointment appointment = unmarshall(line);
                if (appointment != null) {
                    all.add(appointment);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new DaoPersistanceException(ex.getMessage(), ex);
        }
        return all;
    }

    private Appointment unmarshall(String line) {

        String[] tokens = line.split(DELIMITER, -1);

        if (tokens.length != 7) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setCustomerId(Integer.parseInt(tokens[0]));
        appointment.setDentalProLastName(tokens[1]);
        appointment.setSpecialty(Specialty.valueOf(tokens[2]));
        appointment.setStartTime(LocalTime.parse(tokens[3]));
        appointment.setEndTime(LocalTime.parse(tokens[4]));
        appointment.setTotalCost(new BigDecimal(tokens[5]));
        appointment.setNotes(tokens[6]);
        return appointment;
    }

    //==========================================================================
    //	    File Reading
    //==========================================================================
    public boolean checkIfDateExists(LocalDate date) {
        List<String> listOfFiles = new ArrayList<>();

        File folder = new File("data" + File.separator + "appointments");
        File[] files = folder.listFiles();

        for (File f : files) {
            listOfFiles.add(f.getName());
        }

        List<LocalDate> listOfDates = listOfFiles.stream()
                .map((s) -> s.substring(s.indexOf("_") + 1, s.indexOf(".")))
                .map((s) -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyyMMdd")))
                .collect(Collectors.toList());

        if (listOfDates.stream()
                .anyMatch(d -> d.equals(date))) {
            return true;
        }

        return date.equals(LocalDate.now())
                && allAppointments.size() > 0;
    }

    private void readFile(String fileName) throws DaoPersistanceException {
        Scanner scanner;
        File file = new File(fileName);
        List<Appointment> appointments = new ArrayList();

        int currentLineNum = 0;

        //if the file doesn't exist, we don't want to read it
        if (file.exists() == false) {
            return;
        }

        try {
            scanner = new Scanner(new FileReader(fileName));

            //skips the first line of the file
            //first line of file is 'category/column' names
            scanner.nextLine();
            currentLineNum++;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineParts = line.split(DELIMITER, -1);

                Appointment appointment = new Appointment();

                appointment.setCustomerId(Integer.parseInt(lineParts[0]));
                appointment.setDentalProLastName(lineParts[1]);
                appointment.setSpecialty(Specialty.valueOf(lineParts[2]));
                appointment.setStartTime(LocalTime.parse(lineParts[3]));
                appointment.setEndTime(LocalTime.parse(lineParts[4]));
                appointment.setTotalCost(new BigDecimal(lineParts[5]));
                appointment.setNotes(lineParts[6]);

                appointments.add(appointment);

                currentLineNum++;
            }

            scanner.close();
        } catch (FileNotFoundException ex) {
            String message = "Could not load: \n"
                    + fileName;

            throw new DaoPersistanceException(message);
        } catch (NumberFormatException ex) {
            String message = "Data Unable to be parsed in\n"
                    + fileName
                    + "\non line "
                    + currentLineNum;

            throw new DaoPersistanceException(message);
        } catch (IndexOutOfBoundsException ex) {
            String message = "Unable to be parsed correctly in \n"
                    + fileName
                    + "\non line "
                    + currentLineNum;

            throw new DaoPersistanceException(message);

        }

        String dateKey = parseFileNameForDate(fileName);

        //check if my list of every appointments contains the same date
        //that I am trying to put in
        if (allAppointments.containsKey(dateKey)) {

            //store each the appointment lists to use
            Iterator itr1 = allAppointments.get(dateKey).iterator();
            Iterator itr2 = appointments.iterator();

            while (itr1.hasNext()) {
                while (itr2.hasNext()) {
                    Appointment appointment1 = (Appointment) itr1.next();
                    Appointment appointment2 = (Appointment) itr2.next();
                    if (appointment1.getCustomerId() != appointment2.getCustomerId()) {
                        allAppointments.get(dateKey).add(appointment2);
                    }
                }
            }
        } else {
            allAppointments.put(dateKey, appointments);
        }
    }

    //==========================================================================
    //	    File Writing
    //==========================================================================
    public void saveAll() throws DaoPersistanceException, FileNotFoundException {
        PrintWriter writer;
        String fileName = "";
        try {
            for (String key : allAppointments.keySet()) {

                fileName = buildFileName(key);
                writer = new PrintWriter(new File(fileName));
                writer.println(
                        "customer_id,dental_pro_lastname,specialty,start_time,end_time,total_cost,notes");
                for (Appointment appointment : allAppointments.get(key)) {

                    String line = appointment.getCustomerId()
                            + DELIMITER + appointment.getDentalProLastName()
                            + DELIMITER + Specialty.valueOf(key)
                            + DELIMITER + appointment.getStartTime()
                            + DELIMITER + appointment.getEndTime()
                            + DELIMITER + appointment.getTotalCost()
                            + DELIMITER + appointment.getNotes();

                    writer.println(line);
                    writer.flush();
                }

                writer.close();
            }
        } catch (FileNotFoundException ex) {
            throw new DaoPersistanceException("Could not find \n" + fileName);
        }
    }

    //==========================================================================
    //		Helper methods
    //==========================================================================
    private String convertDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String buildFileName(String date) {
        String mainPath = "data"
                + File.separator + "appointments"
                + File.separator + "appointments_";
        String dateKey = date;
        String extention = ".txt";

        return mainPath + dateKey + extention;
    }

    private String parseFileNameForDate(String fileName) {
        //dd MM YYYY = 8
        int charPosition = fileName.indexOf('_') + 1;
        return fileName.substring(charPosition, charPosition + 8);
    }

    @Override
    public List<Appointment> getAppointmentsCustomer(LocalDate date, String cust) throws DaoPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
