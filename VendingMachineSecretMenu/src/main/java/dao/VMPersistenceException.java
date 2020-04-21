/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author jeromepullenjr
 */
public class VMPersistenceException extends Exception {

    public VMPersistenceException(String message) {
        super(message);
    }

    public VMPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
