/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jeromepullenjr
 */
public enum Specialty {
    DENTIST,
    //DENTIST: min Appointment is 15 minutes, max 3 hours

    HYGIENIST,
    //HYGIENIST: min 30 minutes, max 2 hours

    ORTHODONTIST,
    //ORTHODONTIST: min 15 minutes, max 1 hour

    ORAL_SURGEON
    //ORAL_SURGEON: min 30 minutes, max 8 hours (may schedule over lunch if Appointment > 5 hours)

    //Only one Appointment per Customer, per Specialty, per day. (A Customer could see a HYGIENIST in the morning and a DENTIST in the afternoon, but never two DENTISTs in a single day.)
//It is absolutely essential to prevent Dental Professionals from being double-booked.
//make a method to take in string and give proper enum back
    //dentist.toString() (from type to String)
    //Specialty s = Specialty.valueOf(Oral_Surgeon)
//    Business Hours
//Monday-Friday: 7:30AM-12:30PM, then 1:00PM-6:00PM
//Saturday: 8:30AM-12:30PM
//Scheduling
//NEVER allow an Appointment outside of business hours.
//Other
//Appointments must calculate a total cost based on the Dental Professional's hourly rate and the Appointment duration.
//The User cannot cancel an Appointment that has already occurred.
}
