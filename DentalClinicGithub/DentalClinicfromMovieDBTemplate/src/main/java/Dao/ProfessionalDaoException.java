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
public class ProfessionalDaoException extends Exception {

    public ProfessionalDaoException(String message) {
        super(message);
    }

    public ProfessionalDaoException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
