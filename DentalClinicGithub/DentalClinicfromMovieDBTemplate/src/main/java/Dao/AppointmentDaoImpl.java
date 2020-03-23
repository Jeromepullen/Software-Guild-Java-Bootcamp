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
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author jeromepullenjr
 */
public class AppointmentDaoImpl implements AppointmentDao {

    private static final String todaysDate = LocalDateTime.now()
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    static final String FILE_NAME_FOR_TODAY = "data" + File.separator + "appointments_" + todaysDate + ".txt";
    static final String FILE_NAME = "data" + File.separator + "appointments_.txt";

    private static final String DELIMITER = ",";
    private static final String REMOVE_APPOINTMENT_DATE = "[CANCELED] ";
    private Map<String, List<Appointment>> allAppointments = new TreeMap<>();

    @Override
    public Appointment create(Appointment appointment) throws AppointmentDaoException {
        int customerId = 0;
        List<Appointment> all = findAll();
        for (Appointment b : all) {
            customerId = Math.max(customerId, b.getCustomerId());
        }
        //appointment.setAppointmentId(customerId + 1);
        all.add(appointment);

        save(all);

        return appointment;
    }

    @Override
    public boolean update(Appointment appointment) throws AppointmentDaoException {
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

    @Override
    public boolean delete(int appointmentId) throws AppointmentDaoException {
        List<Appointment> all = findAll();
        for (Appointment b : all) {
            if (b.getCustomerId() == appointmentId) {
                all.remove(b);
                save(all);
                return true;
            }
        }
        return false;
    }

    private void save(List<Appointment> appointments) throws AppointmentDaoException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME_FOR_TODAY))) {
            for (Appointment appointment : appointments) {
                writer.println(marshall(appointment));
            }
        } catch (IOException ex) {
            throw new AppointmentDaoException(ex.getMessage(), ex);
        }
    }

//
//    //        @Override
////    public Customer findByEmail(String emailAddress) {
////        return findAll().stream()
////                .filter(c -> c.getEmailAddress().equals(emailAddress))
////                .findFirst()
////                .orElse(null);
//    //	    File Reading
////    
////
////    public List<Appointment> findAll() {
////        try {
////            return read(this::appointment).stream()
////                    .collect(Collectors.toList());
////        } catch (StorageException ex) {
////            return new ArrayList<>();
////        }
////    }
//    public boolean checkIfDateExists(LocalDate date) {
//        List<String> listOfFiles = new ArrayList<>();
//
//        File folder = new File("data" + File.separator + "appointments");
//        File[] files = folder.listFiles();
//
//        for (File f : files) {
//            listOfFiles.add(f.getName());
//        }
//
//        List<String> substrs = listOfFiles.stream()
//                .map((s) -> s.substring(s.indexOf("_") + 1, s.indexOf(".")))
//                .collect(Collectors.toList());
//
//        List<LocalDate> listOfDates = listOfFiles.stream()
//                .map((s) -> s.substring(s.indexOf("_") + 1, s.indexOf(".")))
//                .map((s) -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyyMMdd")))
//                .collect(Collectors.toList());
//
//        if (listOfDates.stream()
//                .anyMatch(d -> d.equals(date))) {
//            return true;
//        }
//
//        return date.equals(LocalDate.now())
//                && getAppointmentsListForToday().size() > 0;
//    }
//
//    private void readFile(String fileName, LocalDate date, int appointmentDate) throws AppointmentDaoException {
//        Scanner scanner;
//        File file = new File(fileName);
//
//        int currentLineNum = 0;
//
//        //if the file doesn't exist, it doesn't want to read it
//        if (file.exists() == false) {
//            return;
//        }
//
//        try {
//            scanner = new Scanner(new FileReader(fileName));
//
//            //skips the first line of the file
//            //first line of file is header names
//            scanner.nextLine();
//            currentLineNum++;
//
//            while (scanner.hasNext()) {
//                String line = scanner.nextLine();
//                String[] lineParts = line.split(DELIMITER, -1);
//
//                Appointment appointment = new Appointment();
//                Customer customer = new Customer();
//                Professional professional = new Professional();
//
//                appointment.setDate(parseFileNameForDate(fileName));
//
//                customer.setCustomerId(Integer.parseInt(lineParts[0]));
//
//                professional.setLastName(lineParts[1]);
//
//                Specialty.valueOf(lineParts[2]);
//
//                appointment.setStartTime(LocalTime.parse(lineParts[3]));
//
//                appointment.setEndTime(LocalTime.parse(lineParts[4]));
//
//                appointment.setTotalCost(new BigDecimal(lineParts[5]));
//
//                appointment.setNotes(lineParts[6]);
//
//                appointments.add(appointment);
//
//                currentLineNum++;
//            }
//            //SET customerName IN HERE SOMEWHERE**
//
//            scanner.close();
//        } catch (FileNotFoundException ex) {
//            String message = "Could not load: \n"
//                    + fileName;
//
//            throw new AppointmentDaoException(message);
//        } catch (NumberFormatException ex) {
//            String message = "Monetary Data Unable to be parsed in\n"
//                    + fileName
//                    + "\non line "
//                    + currentLineNum;
//
//            throw new AppointmentDaoException(message);
//        } catch (IndexOutOfBoundsException ex) {
//            String message = "Unable to be parsed correctly in \n"
//                    + fileName
//                    + "\non line "
//                    + currentLineNum;
//            ex.printStackTrace();
//
//            throw new AppointmentDaoException(message);
//        }
//
//        String dateKey = parseFileNameForDate(fileName);
//
//        //check if my list of every appointments contains the same date
//        //that I am trying to put in
//        if (allAppointments.containsKey(dateKey)) {
//
//            //store each the appointment lists to use
//            Iterator itr1 = allAppointments.get(dateKey).iterator();
//            Iterator itr2 = appointments.iterator();
//
//            //compare to the two appointment's dates
//            while (itr1.hasNext()) {
//                while (itr2.hasNext()) {
//                    Appointment appointment1 = (Appointment) itr1.next();
//                    Appointment appointment2 = (Appointment) itr2.next();
//                    if (appointment1.getDate() != appointment2.getDate()) {
//                        allAppointments.get(dateKey).add(appointment2);
//                    }
//                }
//            }
//        } else {
//            allAppointments.put(dateKey, appointments);
//        }
//    }
//
//    //	    File Writing
//    public void saveAll() throws AppointmentDaoException {
//        PrintWriter writer;
//        String fileName = "";
//
//        try {
//            for (String key : allAppointments.keySet()) {
//
//                fileName = buildFileName(key);
//                writer = new PrintWriter(new File(fileName));
//                writer.println("customer_id,dental_pro_last_name,specialty,start_time,end_time,total_cost,notes");
//
//                for (Appointment appointment : allAppointments.get(key)) {
//
//                    String line = appointment.getCustomer().getId()
//                            + DELIMITER + appointment.getDentalPro()
//                            + DELIMITER + appointment.getSpecialty()
//                            + DELIMITER + appointment.getStartTime()
//                            + DELIMITER + appointment.getEndTime()
//                            + DELIMITER + appointment.getAppointmentTotal()
//                            + DELIMITER + appointment.getNotes();
//
//                    writer.println(line);
//                    writer.flush();
//                }
//
//                writer.close();
//            }
//        } catch (FileNotFoundException ex) {
//            throw new AppointmentDaoException("Could not find \n" + fileName);
//        }
//    }
    private String marshall(Appointment appointment) {
        return appointment.getCustomerId() + DELIMITER
                + appointment.getDentalProLastName() + DELIMITER
                + appointment.getSpecialty().toString() + DELIMITER
                + appointment.getStartTime() + DELIMITER
                + appointment.getEndTime() + DELIMITER
                + appointment.getTotalCost() + DELIMITER
                + appointment.getNotes();
    }

    private Appointment unmarshall(String line) {

        String[] tokens = line.split(DELIMITER);
        if (tokens.length != 6) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setCustomerId(Integer.parseInt(tokens[0]));
        appointment.setDentalProLastName(tokens[1]);
        Specialty.valueOf(tokens[2]);
        appointment.setStartTime(LocalTime.parse(tokens[3]));
        appointment.setEndTime(LocalTime.parse(tokens[4]));
        appointment.setTotalCost(new BigDecimal(tokens[5]));
        appointment.setNotes(tokens[6]);
        return appointment;
    }

    @Override
    public Appointment findByDate(LocalDate appointmentDate) throws AppointmentDaoException {
        for (Appointment appointment : findAll()) {
            if (appointment.getDate() == appointmentDate) {
                return appointment;
            }
        }
        return null;
    }

//    public List<Appointment> getAppointments(LocalDate date) throws AppointmentDaoException {
//        String dateKey = convertDateToString(date);
//        marshall((dateKey));
//
//        List<Appointment> filteredList;
//
//        filteredList = allAppointments.get(dateKey).stream()
//                .filter(o -> o.getDate().equals(date))
//                .collect(Collectors.toList());
//
//        return filteredList;
//    }
    @Override
    public List<Appointment> findAll() throws AppointmentDaoException {
        ArrayList<Appointment> all = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_FOR_TODAY))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Appointment appointment = unmarshall(line);
                if (appointment != null) {
                    all.add(appointment);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new AppointmentDaoException(ex.getMessage(), ex);
        }
        return all;
    }

//    @Override
//    public Appointment displayOne(Appointment appointment, LocalDate date) throws AppointmentDaoException {
//        String dateKey = convertDateToString(date);
//        marshall(parseFileNameForDate());
//
//        List<Appointment> filteredList;
//
//        filteredList = findAll().get(dateKey).stream()
//                .filter(o -> o.getDate().equals(date))
//                .collect(Collectors.toList());
//
//        return appointment;
//    }
//
//    @Override
//    public Appointment getAppointment(LocalDate date, int appointmentDate) throws AppointmentDaoException {
//        String dateKey = convertDateToString(date);
//        readFile(buildFileName(dateKey));
//
//        Optional<Appointment> result = getAppointments(date).stream()
//                .filter(a -> !a.getCustomerName().equals(REMOVE_APPOINTMENT_DATE))
//                .findFirst();
//
//        Appointment appointment = result.get();
//
//        if (appointment != null) {
//            return appointment;
//        }
//        throw new AppointmentDaoException("Appointment not found in file");
//    }
//
//    @Override
//    public List<Appointment> searchAppointments(String input) throws AppointmentDaoException {
//
//        List<Appointment> matches = new ArrayList<>();
//        for (Appointment appointment : findAll()) {
//            LocalDate date = appointment.getDate();
//            if (date.toString().contains(input)) {
//                matches.add(appointment);
//            }
//        }
//        return matches;
//    }
    private String parseFileNameForDate(String fileName) {
        //YYYYmmdd = 8
        int charPosition = fileName.indexOf('_') + 1;
        return fileName.substring(charPosition, charPosition + 8);
    }

    private String convertDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    private String buildFileName(String date) {
        String mainPath = "data"
                + File.separator + "appointments"
                + File.separator + "appointments_";
        String dateKey = date;
        String extention = ".txt";

        return mainPath + dateKey + extention;
    }

    //find a way to get the date from the file
    //@Override
    public Appointment findById(int arg0) throws AppointmentDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> searchAppointments(String input) throws AppointmentDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Appointment displayOne(int appointmentId) throws AppointmentDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> getAppointments(LocalDate date) throws AppointmentDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
