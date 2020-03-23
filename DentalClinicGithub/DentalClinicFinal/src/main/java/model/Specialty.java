/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jeromepullenjr
 */
public enum Specialty {

    DENTIST(0, "DENTIST", 15, 300),
    HYGIENIST(1, "HYGIENIST", 30, 300),
    ORTHODONTIST(2, "ORTHODONTIST", 15, 300),
    ORAL_SURGEON(3, "ORAL_SURGEON", 30, 600);

    private int value;

    private String name;

    private int minVisit;

    private int maxVisit;

    private Specialty(int value, String name, int minVisit, int maxVisit) {

        this.value = value;

        this.name = name;

        this.minVisit = minVisit;

        this.maxVisit = maxVisit;

    }

    @Override

    public String toString() {

        return this.name;

    }

    public int getMinVisit() {

        return minVisit;

    }

    public int getMaxVisit() {

        return maxVisit;

    }
}

//    private final double minAppointmentLength;
//    private final double maxAppointmentLength;
//   
//    private Specialty(double minAppointmentLengthMin, double maxAppointmentLengthMin) {
//        this.minAppointmentLength = minAppointmentLengthMin * 60;
//        this.maxAppointmentLength = maxAppointmentLengthMin * 60;
//    }
//
//    public double getMinAppointmentLength() {
//        return minAppointmentLength;
//    }
//
//    public double getMaxAppointmentLength() {
//        return maxAppointmentLength;
//    }
