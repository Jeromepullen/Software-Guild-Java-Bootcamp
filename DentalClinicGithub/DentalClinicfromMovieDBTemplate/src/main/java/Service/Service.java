/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the eCDIor.
 */
package Service;

import Dao.AppointmentDaoImpl;
import Dao.CustomerDaoImpl;
import Dao.ProfessionalDaoImpl;

/**
 *
 * @author jeromepullenjr
 */
public class Service {

    private final AppointmentDaoImpl ADI;
    private final ProfessionalDaoImpl PDI;
    private final CustomerDaoImpl CDI;
    private final String mode;

    public Service(AppointmentDaoImpl ADI, ProfessionalDaoImpl PDI, CustomerDaoImpl CDI, String mode) {
        this.ADI = ADI;
        this.PDI = PDI;
        this.CDI = CDI;
        this.mode = mode;
    }

//    public void replaceAppointmentInList(LocalDate date, Appointment appointment1, Appointment appointment2) throws DaoPersistanceException {
//        List<Appointment> = ADI.findAll();
//
//        //do caculations on newly edited appointment
//        appointment2 = calculateAppointment(appointment2);
//        String dateKey = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//
//        int index = map.get(dateKey).indexOf(appointment1);
//        map.get(dateKey).remove(index);
//        map.get(dateKey).add(appointment2);
//    }
//
//    public Appointment addAppointment(Appointment appointment) throws DaoPersistanceException {
////        appointment = calculateAppointment(appointment);
//        return ADI.addAppointment(appointment);
//    }
//
//    public Appointment removeAppointment(LocalDate date, int appointmentDate) throws DaoPersistanceException {
//        return ADI.removeAppointment(date, appointmentDate);
//    }
//
//    public Appointment getAppointment(LocalDate date, int appointmentDate) throws DaoPersistanceException {
//        return ADI.getAppointment(date, appointmentDate);
//    }
//
//    public List<Appointment> getAppointments(LocalDate date) throws DaoPersistanceException, DataValidationException {
//        if (checkDateExists(date) == false) {
//            throw new DataValidationException("Date not found.");
//        }
//
//        return ADI.getAppointments(date);
//    }
//
//    //		Helper methods
//    public List<Customer> getCustomers() throws DaoPersistanceException {
//        return CDI.getCustomers();
//    }
//
//    public List<Professional> getProfessionals() throws DaoPersistanceException {
//        return PDI.getProfessionals();
//    }
//
//    public void checkCustomerExists(String name) throws DaoPersistanceException {
//        CDI.getCustomer(name);
//    }
//
//    public void checkProfessionalExists(String type) throws DaoPersistanceException {
//        PDI.getProfessional(type);
//    }
//
//    public boolean checkDateExists(LocalDate date) {
//        return ADI.checkIfDateExists(date);
//    }
//
//    //		Calculations
//    private long minutesBetween(Appointment appointment) {
//
//        long minutes = Duration.between(appointment.getStartTime(), appointment.getEndTime()).toMinutes();
//
//        return minutes;
//
//    }
//
//    private BigDecimal calculateTotal(Appointment appointment, Professional professional, long minutes) {
//
//        BigDecimal total = BigDecimal.ZERO;
//
//        //calculate HOurlyRate by minutesBetween / 60
//        return professional.getHourlyRate().multiply(total);
//
//    }
//
////    		Private methods
//    private Appointment calculateAppointment(Appointment appointment) throws DaoPersistanceException {
//        //calculate HourlyRate by minutesBetween / 60
//        return appointment;
//    }
//
//    public void addCustomer(Customer customer) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//}
}
