/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import model.Appointment;
import model.Customer;
import model.Professional;
import model.Specialty;

/**
 *
 * @author jeromepullenjr
 */
public class View {

    UserIO io;

    public View(UserIO io) {
        this.io = io;

    }

    //==========================================================================
    //	    Helper Methods
    //==========================================================================
    private void createBanner(String string) {
        int paddingSize = 10;
        int bappointmentLength = (paddingSize * 2) + string.length() + 1;
        bappointmentLength /= 2;

        String side = "|";
        String bappointment = "";
        String paddingAroundString = "";

        bappointment += " ";
        for (int i = 0; i < bappointmentLength; i++) {
            bappointment += "- ";
        }

        for (int i = 0; i < paddingSize; i++) {
            paddingAroundString += " ";
        }

        io.print(bappointment);
        io.print(side
                + paddingAroundString
                + string
                + paddingAroundString
                + side);
        io.print(bappointment);
    }

    //==========================================================================
    //	    General Methods
    //==========================================================================
    public LocalDate getInputDate() {
        io.print("Format:  Month/Day/Year");
        io.print("Example: 12/31/2019");
        String inputtedDate = io.readString("Enter date of appointment");

        String pattern = "(\\,)|(\\.)|(\\-)|(\\_)|(\\/)|(\\\\)";
        inputtedDate = inputtedDate.trim().replaceAll(pattern, "");
        io.print(inputtedDate);

        return LocalDate.parse(inputtedDate, DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    public String getCustomerLastNameSearch() {
        io.print("Search for a Customer.");
        return io.readString("Last Name starts with:");
    }

    public Customer displaySearchCust(List<Customer> cust) {
        if (cust.size() > 100) {
            io.print("Showing the top 100 results.\n");
        }
        cust = cust.stream()
                .sorted((a, b) -> a.getLastName().compareTo(b.getLastName()))
                .limit(100)
                .collect(Collectors.toList());
        int index = 1;
        for (Customer searchCustomer : cust) {
            io.print(String.format("%s: %s : %s : %s : %s", index++,
                    searchCustomer.getCustomerId(),
                    searchCustomer.getFirstName(),
                    searchCustomer.getLastName(),
                    searchCustomer.getDOB()
            ));
        }
        io.print(""); // empty newline
        int record = io.readInt("Enter a row number to choose the customer or 0 to reload list.", 0, index - 1);
        if (record == 0) {
            return displaySearchCust(cust);
        } else if (record > 0) {
            cust.get(record - 1);
        } else {
            io.print("Error, enter a valid row");
        }
        Customer customer = cust.get(record - 1);
//        io.print("You selected " + customer.getFirstName() + " " + customer.getLastName());
//        io.print("Is this correct?");
//        String userInput = io.readString("Enter [y] to display full appointment details.\n"
//                + "Or [n] to select a different row.");
//        if (userInput.equalsIgnoreCase("y")) {
//            //io.print("The customer you are looking for has been found!");
//        } else if (userInput.equalsIgnoreCase("n")) {
//            displaySearchCust(cust);
//        }
        return customer;
    }

    public Customer makeCustomer() {

        io.print("\nAdd a Customer");
        io.print("============");

        Customer customer = new Customer();
        customer.setFirstName(io.readString("First Name:"));
        customer.setLastName(io.readString("Last Name:"));
        String inputtedDob = io.readString("Date of Birth MM/DD/YYYY");
        customer.setDOB(LocalDate.parse(inputtedDob, DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        return customer;
    }

    public Customer customerAdd() {
        Customer customer = new Customer();

        customer.getFirstName();
        customer.getLastName();
        customer.getDOB();

        return customer;
    }

    public void displayFirstName(Customer customer) {
        io.print("First Name: " + customer.getFirstName());
    }

    public void displayLastName(Customer customer) {
        io.print("Last Name: " + customer.getLastName());
    }

    public void displayCustDOB(Customer customer) {
        io.print("Date of Birth: " + customer.getDOB());
    }

    public String getInputCustomerAdd() {
        return io.readString("Are you sure you want to add this customer? y/n");
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public String getCustomerLastName() {
        String inputtedCustomerLastName = io.readString("Enter Customer Last Name.");
        return inputtedCustomerLastName;
    }

    public String getDentalName() {
        String inputtedProLastName = io.readString("Enter Dental Professional Last Name.");
        return inputtedProLastName;
    }

    public int getInputcustomerId() {
        return io.readInt("Enter the Appointment Number.");
    }

    public void displayBannerAppointmentAdd() {
        createBanner("Add Appointment");
    }

    public String getInputCustomerName() {
        return io.readString("Enter the customer's name.");
    }

    public String getInputCustomer() {
        return io.readString("Enter the customer .");
    }

    public Specialty getInputSpecialty(String prompt) {
        return Specialty.valueOf(io.readString("Enter the specialty ").toUpperCase());
    }

    public String getInputPro() {
        return io.readString("Enter the Dental Professional");
    }

    public LocalTime getStartTime(String please_enter_Start_Time_HHmm) {
        return io.readTime("Please enter Start Time: HH:mm");
    }

    public LocalTime getEndTime(String please_enter_End_Time_HHmm) {
        return io.readTime("Please enter End Time: HH:mm");
    }

    public String getInputStartTime() {
        return io.readString("Enter the Start Time of the appointment");
    }

    public String getInputEndTime() {
        return io.readString("Enter the End Time of the appointment");
    }

    public void displayProfessionalType(Appointment appointment) {
        io.print("Current Data: " + appointment.getProfessional().getType());
    }

    public void displayAppointment(Appointment A) {
        io.print("Customer ID:              " + A.getCustomerId());
        io.print("Dental Pro Last Name:     " + A.getDentalProLastName());
        io.print("Specialty:                " + A.getSpecialty());
        io.print("Start Time:               " + A.getStartTime());
        io.print("End Time:                 " + A.getEndTime());
        io.print("Total Cost:             $ " + A.getTotalCost());
        io.print("Notes:                    " + A.getNotes());
        io.print("");
        io.print("-------------------------------");
        io.print("");
    }

    public void displayCustomer(Customer C) {
        io.print("Customer ID:             " + C.getCustomerId());
        io.print("First Name:              " + C.getFirstName());
        io.print("Last Name:             $ " + C.getLastName());
        io.print("Date of Birth:           " + C.getDOB());
        io.print("");
        io.print("-------------------------------");
        io.print("");
    }

    //==========================================================================
    //	    Main Menu
    //==========================================================================
    public void displayBannerMenu() {
        createBanner("Main Menu");
    }

    public void displayMenu() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("*  <<Dental Clinic>>");
        io.print("* 1. View an Appointment by Date and Dental Professional");
        io.print("* 2. View Appointments by Date and Customer");
        io.print("* 3. Add a Customer");
        io.print("* 4. Schedule an Appointment");
        io.print("* 5. Update an Appointment");
        io.print("* 6. Cancel an Appointment");
        io.print("* 7. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public Appointment update(Appointment listA) {

        String notes = io.readString(
                String.format("Update Notes (%s):", listA.getNotes()));
        if (!notes.isEmpty()) {
            listA.setNotes(notes);
        }

        String totalCost = io.readString(
                String.format("Update Total Cost (%s):", listA.getTotalCost().toString()));
        if (!totalCost.toString().isEmpty()) {
            listA.setTotalCost(new BigDecimal(totalCost));
        }
        return listA;
    }

    public int getInputMenu() {
        return io.readInt("Enter an option.");
    }

    public boolean displayUpdatedAppointment(Appointment appointment) {
        io.print("\n Please verify your appointment information is correct: ");
        io.print("=========================================================");
        io.print("Dental Professional: " + appointment.getDentalProLastName());
        io.print("Specialty: " + appointment.getSpecialty());
        io.print("Date: " + appointment.getDate());
        io.print("Start Time: " + appointment.getStartTime());
        io.print("End Time: " + appointment.getEndTime());
        io.print("Total Cost $: " + appointment.getTotalCost());
        io.print("Notes: " + appointment.getNotes());
        boolean infoToVerify = confirm("Is the information above accurate?");
        return infoToVerify;
    }

//    public Professional getInputPro(Professional pro) {
//        return io.readString("Enter the Dental Professional");
//    }
    public boolean confirm(String message) {
        return io.readBoolean(String.format("%s [y/n]:", message));
    }

    public void displayBannerAppointments() {
        createBanner("Display Appointments");
    }

    public void displayBannerChooseExisting() {
        createBanner("Choose existing customer");
    }

    public void displayRemoveAppointments() {
        createBanner("Cancel an Appointment");
    }

    public void displayBannerAppointmentsList(LocalDate date) {
        createBanner("Displaying appointments for "
                + date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }

    public void displayAppointments(List<Appointment> list) {
        for (Appointment A : list) {
            displayAppointment(A);
        }
    }

    public void displayAppointmentsCustIdDate(List<Appointment> listA) {
        for (Appointment A : listA) {
            displayAppointment(A);
        }
    }

    public void displayCustomers(List<Customer> cust) {
        for (Customer C : cust) {
            displayCustomer(C);
        }
    }

    private BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    public void displayAvailableProfessionals(List<Professional> list) {
        io.print("Avaiable Professionals");

        for (Professional p : list) {
            io.print(p.getType());
        }

    }

    public Professional selectProfessional(List<Professional> professional) {
        if (professional.size() > 25) {
            io.print("Too many professional. Showing the top 25.\n");
        }
        professional = professional.stream()
                .sorted((a, b) -> a.getLastName().compareTo(b.getLastName()))
                .limit(25)
                .collect(Collectors.toList());
        int index = 1;
        for (Professional p : professional) {
            io.print(String.format("%s: %s",
                    index++,
                    p.getLastName()));
        }

        io.print(""); // empty newline
        int record = io.readInt("Enter a row number or 0 to exit.", 0, index - 1);
        if (record > 0) {
            return professional.get(record - 1);
        }
        return null;
    }

    public Appointment AppointmentAdd(String customerName, String customer, String professionalType) {
        Appointment appointment = new Appointment();

        appointment.setCustomerName(customerName);
        appointment.getProfessional().setType(professionalType);

        return appointment;
    }

    public String getInputConfirmationAdd() {
        return io.readString("Are you sure you want to add this customer? y/n");
    }

    public String getInputApptAdd() {
        return io.readString("Are you sure you want to schedule this appointment? y/n");
    }

    public void displayBannerSuccessCustomerAdd() {
        createBanner("Customer added successfully.");
    }

    public void displayBannerSuccessAppointmentAdd() {
        createBanner("Appointment added successfully.");
    }

    public void displayBannerAppointmentEdit() {
        createBanner("Edit Appointment");
    }

    public void displayMessageEdit() {
        io.print("Appointment found.");
        io.print("Press enter to skip any part you do not want to edit.");
    }

    public Appointment AppointmentEditAll(Appointment appointment, String customerName, String professionalType) {
        Appointment newAppointment = new Appointment();
        newAppointment.setCustomerId(appointment.getCustomerId());

        if (customerName.isEmpty()) {
            newAppointment.setCustomerName(appointment.getCustomerName());
        } else {
            newAppointment.setCustomerName(customerName);
        }

        if (professionalType.isEmpty()) {
            newAppointment.getProfessional().setType(appointment.getProfessional().getType());
        } else {
            newAppointment.getProfessional().setType(professionalType);
        }

        return newAppointment;
    }

    public void displayBannerAppointmentRemove() {
        createBanner("Remove Appointment");
    }

    public String getInputConfirmationRemove() {
        return io.readString("Are you sure you want to remove this appointment? y/n");
    }

    public String existingOrNewCustomer() {
        return io.readString("Press '1' to select from an existing customer or '2' to add a new customer");
    }

    public void displayBannerSuccessAppointmentRemove() {
        createBanner("Appointment removed successfully.");
    }

    //==========================================================================
    //	    Program Start
    //==========================================================================
    public void displayBannerProgramStart() {
        io.print(" -----------------------------------------------------");
        io.print("|						   	|");
        io.print("|                 Dental Clinic                   	|");
        io.print("|						   	|");
        io.print(" - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    //==========================================================================
    //	    Program End
    //==========================================================================
    public void displayBannerProgramEnd() {
        io.print(" ---------------------------------------------------------");
        io.print("|							    |");
        io.print("|                  Exiting Dental Clinic           	    |");
        io.print("|							    |");
        io.print(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    public void messageInputIncorrect() {
        io.print("Incorrect Input");
    }

    public void displayNoCustomer(String search) {
        io.print("No customer found");
    }

    public void messageInputUnknown() {
        io.print("Uknown Input");
    }

    public void messageDateNotFound(LocalDate date) {
        io.print("No appointments for "
                + date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + " exist.");
    }

    public void messagecustomerIdNotFound(int customerId) {
        io.print("No appointments with appointment Number " + customerId + " exist.");
    }

    public void messageError(String message) {
        io.print(message);
    }

    public String getInputConfirmationEdit() {
        return io.readString("Are you sure you want to update this appointment? y/n"); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNotes(String prompt) {
        return io.readString("Please enter customer notes: "); //To change body of generated methods, choose Tools | Templates.
    }

}
