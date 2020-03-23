package UI;

import Model.Appointment;
import Model.Customer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public int displayMenuAndSelect() {

        io.print("\nMain Menu");
        io.print("==========");

        io.print("0. [Exit]");
        io.print("1. Add an appointment.");
        io.print("2. Edit an appointment.");
        io.print("3. Cancel an appointment.");
        io.print("4. Add a customer.");
        io.print("5. Display a single appointment.");
        io.print("6. Search for an appointment.");
        io.print("7. Display all appointments.");

        return io.readInt("Select [0-7]:", 0, 7);
    }

    public void sayGoodbye() {
        io.print("\nGoodbye...");
    }

//    public Appointment makeAppointment() {
//
//        io.print("\nAdd an Appointment");
//        io.print("============");
//
//        Appointment appointment = new Appointment();
//        Customer customer = new Customer();
//        appointment.getCustomerId(io.readRequiredString("Choose an existing customer or add a new customer: "))
//        );
//        appointment.getDate(LocalDate.parse(io.readRequiredString("Enter a Date: ")));
//        specialty.valueOf(io.readRequiredString("Dental Pro Specialty: "));
//        appointment.getTimeSlot(io.readRequiredString("Pick an available time slot: "));
//        appointment.getStartTime(io.readRequiredString("Start Time: "));
//        appointment.getEndTime(io.readRequiredString("End Time: "));
//        appointment.getTotalCost(io.readRequiredString("Total Cost: "));
//        appointment.getNotes(io.readRequiredString("User Rating: "));
//        return appointment;
//    }
    public Customer makeCustomer() {

        io.print("\nAdd a Customer");
        io.print("============");

        Customer customer = new Customer();
        customer.setFirstName(io.readRequiredString("First Name:"));
        customer.setLastName(io.readRequiredString("Last Name:"));
        customer.setDOB(LocalDate.parse(io.readRequiredString("Date of Birth MM/DD/YYYY):")));

        return customer;
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void displayException(Exception ex) {
        io.print("\nERROR");
        io.print("=======");
        io.print(ex.toString());
    }

    public void displayAppointments(List<Appointment> all) {
        io.print("\nAll Appointments");
        io.print("============");
        if (all.isEmpty()) {
            io.print("No appointments founds.");
            return;
        }

        for (Appointment b : all) {

            io.print(b.getCustomerId() + ": " + b.getDentalProLastName() + ", " + b.getSpecialty()
                    + ", " + b.getStartTime() + ", " + b.getEndTime() + ", " + b.getTotalCost()
                    + ", " + b.getNotes()
            );
        }
    }

    public int readAppointmentId(String header) {
        io.print("");
        io.print(header);
        io.print("============");
        return io.readInt("Appointment Date?:");
    }

    public LocalDate getInputDate() {
        io.print("Format:  Month/Day/Year");
        io.print("Example: 12/31/2019");
        String inputtedDate = io.readString("Enter a date.");

        String pattern = "(\\,)|(\\.)|(\\-)|(\\_)|(\\/)|(\\\\)";
        inputtedDate = inputtedDate.trim().replaceAll(pattern, "");
        io.print(inputtedDate);

        return LocalDate.parse(inputtedDate, DateTimeFormatter.ofPattern("MMddyyyy"));
    }
//
//    public Appointment update(Appointment appointment) {
//
//        LocalDate date = .getInputDate(date); //compareToLocalDate
//        String.format("Enter a Date (%s):" appointment.getDate()
//        )
//        );
//        if (!date.isEmpty()) {
//            appointment.setDate(date);
//        }
//
//        String customer = io.readString(
//                String.format("Choose a Customer:", appointment.getCustomer().getFirstName()));
//        if (!customer.isEmpty()) {
//            appointment.setCustomer(Customer);
//        }
//
//        String mpaaRating = io.readString(
//                String.format("Choose an Appointment (%s):", appointment.getStartTime()));
//        if (!mpaaRating.isEmpty()) {
//            appointment.setMpaaRating(mpaaRating);
//        }
//
//        String DirectorName = io.readString(
//                String.format("Add Notes (%s):", appointment.getDirectorName()));
//        if (!DirectorName.isEmpty()) {
//            appointment.setDirectorName(DirectorName);
//        }
//
//        String studio = io.readString(
//                String.format("Studio Name (%s):", appointment.getTotalCost()));
//        if (!studio.isEmpty()) {
//            appointment.setStudio(studio);
//        }
//
//        String userRating = io.readString(
//                String.format("User Rating (%s):", appointment.getNotes()));
//        if (!userRating.isEmpty()) {
//            appointment.setUserRating(userRating);
//        }
//
//        return appointment;
//    }

    public void displaySearchBanner() {
        io.print("Search Appointments");
    }

    public void displayDisplayAppointmentBanner() {
        io.print("=== View Appointment Information ===");
    }

    public String getSearch() {
        String appointmentTitle = io.readString("Search for appointments that start with: ");
        return appointmentTitle;
    }

    public void displayAppointmentList(List<Appointment> appointmentList) {
        for (Appointment appointment : appointmentList) {
            io.print("Customer Id: " + appointment.getCustomerId());
            io.print("Dental Pro Last Name: " + appointment.getDentalProLastName());
            io.print("Specialty: " + appointment.getSpecialty().toString());
            io.print("Start Time: " + appointment.getStartTime());
            io.print("End Time: " + appointment.getEndTime());
            io.print("Total Cost: " + appointment.getTotalCost());
            io.print("Notes: " + appointment.getNotes());
            io.print("--------");
        }
    }

    public String getAppointmentTitleChoice() {
        String appointmentTitle = io.readString("Title of appointment that you would "
                + "like more information about: ");
        return appointmentTitle;
    }

    public void displayAppointment(Appointment appointment) {

        if (appointment != null) {
            io.print(appointment.getDentalProLastName());
            io.print(appointment.getSpecialty().toString());
            io.print(appointment.getStartTime().toString());
            io.print(appointment.getEndTime().toString());
            io.print(appointment.getTotalCost().toString());
            io.print(appointment.getNotes());
        } else {
            io.print("That appointment is not in the database.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayViewAppointmentBanner() {
        io.print("=== View Appointment Information ===");
    }

}
