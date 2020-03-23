/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DaoCustomer;
import dao.DaoPersistanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Customer;
import model.Professional;
import model.Specialty;
import service.DataValidationException;
import service.Service;
import ui.View;

/**
 *
 * @author jeromepullenjr
 */
public class Controller {

    private View view;
    private Service service;
    private final DaoCustomer daoCust;

    public Controller(View view, Service service, DaoCustomer daoCust) {
        this.view = view;
        this.service = service;
        this.daoCust = daoCust;
    }

    public void run() throws DaoPersistanceException, DataValidationException {
        view.displayBannerProgramStart();

        while (true) {
            view.displayBannerMenu();
            view.displayMenu();

            int userInput = 0;
            int incorrectInputCounter = 0;

            while (true) {
                if (incorrectInputCounter >= 5) {
                    view.displayBannerMenu();
                    view.displayMenu();
                    incorrectInputCounter = 0;
                }

                try {
                    userInput = view.getInputMenu();
                    if (userInput > 0 && userInput < 8) {
                        break;
                    }
                } catch (NumberFormatException ex) {
                    //fall through
                }
                view.messageInputIncorrect();
                incorrectInputCounter++;
            }

            switch (userInput) {
                case 1:
                    appointmentsListDentalPro();
                    break;
                case 2:
                    appointmentsListCustomer();

                    break;
                case 3:
                    addCustomer();

                    break;
                case 4:
                    appointmentAdd();

                    break;
                case 5:
                    appointmentEdit();
                    break;
                case 6:
                    appointmentRemove();
                    break;
                case 7:
                    view.displayBannerProgramEnd();
                    System.exit(0);
                    break;
                default:
                    view.messageInputIncorrect();
                    break;
            }
        }
    }

    public void appointmentAdd() throws DataValidationException, DaoPersistanceException {
        List<Customer> listC;
        List<Appointment> listA;
        LocalDate date = LocalDate.MAX;
        Appointment A;
        String cust;
        List<Professional> pro = null;
//        String specialty;
//        String pro;
//        date = view.getInputDate();
//        specialty = view.getInputSpecialty();
//        pro = view.getInputPro();

        //1 for existing customer and 2 for addCustomer
        if (view.existingOrNewCustomer().equals("1")) {

            // 1. Get existing customer name from view.
            // 2. Get matching customers from service.
            // 3. Select one customer using the view..
//        Customer customer = findAndListCustomer();
//        if (customer == null) {
//            return null;
//        }
//        // 4. Get date from view.
//        LocalDate date = view.getDate("Please enter date:");
//        if (date == null) {
//            return null;
//        }
            view.displayBannerChooseExisting();

            do {
                try {

                    cust = view.getCustomerLastName();
                    listC = service.getCustomers(cust);

                    break;
                } //file not found
                catch (DateTimeParseException ex) {

                    view.messageInputIncorrect();
                }
            } while (true);

            Customer customer = view.displaySearchCust(listC);

            int custId = customer.getCustomerId();
            // 5. Get a Specialty from view.
            Specialty specialty = view.getInputSpecialty("Please enter Specialty (DENTIST, HYGIENIST, ORTHODONTIST, ORAL_SURGEON): ");
            if (specialty == null) {

            }
            // 6. Get all Pros with that specialty from service.
//            List<Professional> allProfessionalsBySpecialty = service.findBySpecialty(specialty);
//            if (allProfessionalsBySpecialty.isEmpty()) {
//
//            }
            // 7. Select one pro using the view.
            Professional professional = view.selectProfessional(pro);
            if (professional == null) {

            }
            // 8. Get start and end time from view.
            LocalTime startTime = view.getStartTime("Please enter Start Time: HH:mm");
            LocalTime endTime = view.getEndTime("Please enter End Time: HH:mm");
            String notes = view.getNotes("Please enter customer notes: ");
            // 9. Make an appointment from everything above. Save it (using service).
//        Appointment appointment = dentalClinicService.searchOpenTimeSlots(date, professional, startTime, endTime);
            Appointment appointment = new Appointment();
            appointment.setDate(date);
            appointment.setCustomerId(customer.getCustomerId());
            appointment.setStartTime(startTime);
            appointment.setEndTime(endTime);
            appointment.setSpecialty(specialty);
            appointment.setDentalProLastName(professional.getLastName());
            appointment.setTotalCost(service.calculateCost(appointment));
            appointment.setNotes(notes);

            view.displayUpdatedAppointment(appointment);
            service.addNewAppointment(appointment);
            view.displayMessage("Appointment scheduled!");
            service.addNewAppointment(appointment);

        }

        //confirm
        if (view.getInputApptAdd()
                .equals("y")) {
//service.addAppointment(date, custId);
            view.displayMessage("Appointment scheduled!");
        }
//         
//
//             ^^^ using existing customer ^^^
//
//
//
//           ------------------------------------------
//
//
//
//                  adding a new customer
//
//
//
//

        if (view.existingOrNewCustomer()
                .equals("2")) {

            addCustomer();

            //confirm
            if (view.getInputApptAdd().equals("y")) {
//service.addAppointment(date, custId);
                view.displayMessage("Appointment scheduled!");
            }
        }
    }

    public void appointmentsListDentalPro() {
        List<Appointment> list;
        LocalDate date = LocalDate.MAX;
        String pro;

        view.displayBannerAppointments();

        do {
            try {
                date = view.getInputDate();
                pro = view.getDentalName();
                list = service.getAppointmentsDentalPro(date, pro);
                break;
            } catch (DaoPersistanceException ex) {
                //file not found

            } catch (DateTimeParseException ex) {

                view.messageInputIncorrect();
            }
        } while (true);

        view.displayBannerAppointmentsList(date);
        view.displayAppointments(list);
    }

    public void appointmentsListCustomer() throws DataValidationException, DaoPersistanceException {
        List<Customer> listC;
        List<Appointment> listA;
        LocalDate date = LocalDate.MAX;

        String cust;

        view.displayBannerAppointments();

        do {
            try {

                cust = view.getCustomerLastName();
                listC = service.getCustomers(cust);

                break;
            } catch (DaoPersistanceException ex) {
                //file not found

            } catch (DateTimeParseException ex) {

                view.messageInputIncorrect();
            }
        } while (true);

        Customer customer = view.displaySearchCust(listC);

        int custId = customer.getCustomerId();

        date = view.getInputDate();
        listA = service.getAppointmentsCustomerDate(date, custId);
        view.displayAppointmentsCustIdDate(listA);

        if (listA.isEmpty()) {
            view.displayMessage("No appointments were found");
        }

    }

    public void appointmentRemove() throws DataValidationException, DaoPersistanceException {
        List<Customer> listC;
        List<Appointment> listA;
        LocalDate date = LocalDate.MAX;
        Appointment A;

        String cust;

        view.displayBannerAppointments();

        do {
            try {

                cust = view.getCustomerLastName();
                listC = service.getCustomers(cust);

                break;
            } catch (DaoPersistanceException ex) {
                //file not found

            } catch (DateTimeParseException ex) {

                view.messageInputIncorrect();
            }
        } while (true);

        Customer customer = view.displaySearchCust(listC);

        int custId = customer.getCustomerId();

        date = view.getInputDate();
        listA = service.getAppointmentsCustomerDate(date, custId);
        view.displayAppointmentsCustIdDate(listA);

        if (listA.isEmpty()) {
            view.displayMessage("No appointments were found");
        }

        //confirm
        if (view.getInputConfirmationRemove().equals("y")) {
            try {
                service.removeAppointment(date, custId);
            } catch (DaoPersistanceException ex) {
                view.messageError(ex.getMessage());
            }
            view.displayMessage("Appointment removed!");
        }
    }

    public void appointmentEdit() throws DaoPersistanceException {
        List<Customer> listC;
        List<Appointment> listA;
        LocalDate date = LocalDate.MAX;
        Appointment appointment = null;

        String cust;

        view.displayBannerAppointments();

        do {
            try {

                cust = view.getCustomerLastName();
                listC = service.getCustomers(cust);

                break;
            } catch (DaoPersistanceException ex) {
                //file not found

            } catch (DateTimeParseException ex) {

                view.messageInputIncorrect();
            }
        } while (true);

        Customer customer = view.displaySearchCust(listC);

        int custId = customer.getCustomerId();

        date = view.getInputDate();
        listA = service.getAppointmentsCustomerDate(date, custId);
        view.displayAppointmentsCustIdDate(listA);

        if (listA.isEmpty()) {
            view.displayMessage("No appointments were found");
        }

        //confirm
        if (view.getInputConfirmationEdit().equals("y")) {
            try {
                appointment = view.update(listA.get(0));
                service.editAppointment(date, custId, appointment);
            } catch (DaoPersistanceException ex) {
                view.messageError(ex.getMessage());
            }
            view.displayMessage("Appointment updated!");
        }
    }

    private void addCustomer() throws DaoPersistanceException {
        //show the output for the user to begin inputting the movie
        Customer customer = view.makeCustomer();
        //sends the movie info to the dao

        view.displayFirstName(customer);
        view.displayLastName(customer);
        view.displayCustDOB(customer);

        if (view.getInputConfirmationAdd()
                .equalsIgnoreCase("y")) {
            try {
                daoCust.create(customer);
                view.displayBannerSuccessCustomerAdd();
            } catch (DaoPersistanceException ex) {
                view.messageError(ex.getMessage());
            }
            view.displayMessage("Customer " + customer.getFirstName() + " " + customer.getLastName() + " created!");
        }
    }
}

//****Allow a Dental Professional or User to add notes to the Appointment or change its total cost.
//    private void addAppointment() throws DaoPersistanceException {
//        //show the output for the user to begin inputting the movie
//        Appointment appointment = view.makeAppointment();
//        //sends the movie info to the dao
//        
//        Schedule an Appointment
//User may choose from an existing Customer or add a new Customer ("Add a Customer" use case).
//Enter a date.
//Enter a Specialty.
//Application shows available time slots for all Dental Professionals with that Specialty.
//Choose a Dental Professional.
//Enter a start and end time for the Appointment.
//Review/confirm. If the user doesn't confirm, the Appointment must not be saved.
//        view.displayFirstName(customer);
//        view.displayLastName(customer);
//        view.displayCustDOB(customer);
//
//        if (view.getInputConfirmationAdd()
//                .equalsIgnoreCase("y")) {
//            try {
//                daoCust.create(customer);
//                view.displayBannerSuccessCustomerAdd();
//            } catch (DaoPersistanceException ex) {
//                view.messageError(ex.getMessage());
//            }
//            view.getInputCustomerAdd();
//            view.displayMessage("Customer " + customer.getFirstName() + customer.getLastName() + " created!");
//        }
//    }

