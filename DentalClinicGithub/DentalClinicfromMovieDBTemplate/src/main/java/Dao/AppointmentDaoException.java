/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

/**
 *
 * @author jeromepullenjr
 */
public class AppointmentDaoException extends Exception {

    public AppointmentDaoException(String message) {
        super(message);
    }

    public AppointmentDaoException(String message, Exception rootCause) {
        super(message, rootCause);
    }

}
