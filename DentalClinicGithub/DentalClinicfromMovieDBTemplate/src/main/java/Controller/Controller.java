package Controller;

import Model.Appointment;
import UI.View;
import java.util.List;
import Dao.AppointmentDao;
import Dao.AppointmentDaoException;
import Dao.AppointmentDaoImpl;
import Dao.CustomerDao;
import Dao.CustomerDaoException;
import Dao.CustomerDaoImpl;
import Dao.ProfessionalDao;
import Dao.ProfessionalDaoImpl;
import Model.Customer;
import java.time.LocalDate;

public class Controller {

    private final View view;
    private final AppointmentDao dao;
    private final CustomerDao daoCustomer;
    private final ProfessionalDao daoProfessional;

    public Controller(View view, AppointmentDaoImpl dao, CustomerDaoImpl daoCust, ProfessionalDaoImpl daoPro) {
        this.view = view;
        this.dao = dao;
        this.daoCustomer = daoCust;
        this.daoProfessional = daoPro;
    }

    public void run() throws AppointmentDaoException {
        int selection = 0;
        do {
            selection = view.displayMenuAndSelect();
            switch (selection) {
                case 1:
//                    addAppointment();
                    break;
                case 2:
//                    updateAppointment();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    addCustomer();
                    break;
                case 5:
                    viewAppointment();
                    break;
                case 6:
                    searchAppointments();
                    break;
                case 7:
                    displayAll();

            }

//                    io.print("0. [Exit]");
//        io.print("1. Add an appointment.");
//        io.print("2. Edit an appointment.");
//        io.print("3. Cancel an appointment.");
//        io.print("4. Add a customer.");
//        io.print("5. Display a single appointment.");
//        io.print("6. Search for an appointment.");
//        io.print("7. Display all appointments.");
        } while (selection > 0);

        view.sayGoodbye();
    }

//    private void addAppointment() {
//        //show the output for the user to begin inputting the appointment
//        Appointment appointment = view.makeAppointment();
//        try {
//            //sends the appointment info to the dao 
//            dao.create(appointment);
//
//            //shows if the appointment was successfully created (from the view)
//            view.displayMessage(appointment.getDentalProLastName() + " created!");
//        } catch (AppointmentDaoException ex) {
//
//            //shows this if the appointment was NOT successfully created
//            view.displayException(ex);
//        }
//
//    }
    private void addCustomer() {
        //show the output for the user to begin inputting the appointment
        Customer customer = view.makeCustomer();
        try {
            //sends the appointment info to the dao 
            daoCustomer.create(customer);

            //shows if the appointment was successfully created (from the view)
            view.displayMessage(customer.getFirstName() + customer.getLastName() + " created!");
        } catch (CustomerDaoException ex) {

            //shows this if the appointment was NOT successfully created
            view.displayException(ex);
        }

    }

    private void displayAll() {
        try {
            //retrieves the entire ArrayList of appointments from the dao
            List<Appointment> all = dao.findAll();

            //shows all of the appointments that were retrieved
            view.displayAppointments(all);

        } catch (AppointmentDaoException ex) {
            //shows this if the appointments were not retrieved properly
            view.displayException(ex);
        }
    }

//    private void updateAppointment() {
//        //asks for the appointmentId from the user 
//        LocalDate appointmentDate = view.getInputDate();
//
//        try {
//
//            Appointment appointment = dao.findByDate(appointmentDate);
//            //if there is no appointment found it will display that it wasn't found
//            if (appointment == null) {
//                view.displayMessage("Appointment Date " + appointmentDate + " not found.");
//                return;
//            }
//            //calls the update method in the view which updatse each part of the appointment
//            appointment = view.update(appointment);
//            if (dao.update(appointment)) {
//
//                //displays if appointment was successfully updated
//                view.displayMessage("Customer " + appointment.getCustomerId() + " updated!");
//            } else {
//
//                //displays if appointment was not found
//                view.displayMessage("Appointment Date " + appointmentDate + " not found.");
//            }
//        } catch (AppointmentDaoException ex) {
//            //if there was an error updating the appointment
//            view.displayException(ex);
//        }
//
//    }
    private void cancelAppointment() {
        try {
            //reads the appointmentId input from the user
            int appointmentId = view.readAppointmentId("Delete Appointment");
            if (dao.delete(appointmentId)) {
                //if successfully deleted
                view.displayMessage("Appointment Id " + appointmentId + " deleted.");
            } else {
                //if not successfully deleted
                view.displayMessage("Appointment Id " + appointmentId + " not found.");
            }
            //if appointment was not found
        } catch (AppointmentDaoException ex) {
            view.displayException(ex);
        }

    }

    private void viewAppointment() throws AppointmentDaoException {

        //asks for appointmentID
        LocalDate date = view.getInputDate();

        //looks up the appointment that matches the id inputted
        Appointment appointment = dao.findByDate(date);

        //displays appointment info that matches the id
        view.displayAppointment(appointment);
    }

    private void searchAppointments() throws AppointmentDaoException {
        try {
            view.displaySearchBanner();

            //get the user input for what the appointment starts with
            String startsWith = view.getSearch();

            // get appointment data for every appointment that starts with the user input
            List<Appointment> All = dao.searchAppointments(startsWith);

            //display appointments that meet this criteria
            view.displayAppointmentList(All);

        } catch (AppointmentDaoException ex) {
            view.displayException(ex);
        }
    }
}
