/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public class Response {

    private final ArrayList<String> errorMessages = new ArrayList<>();

    public boolean hasError() {
        return errorMessages.size() > 0;
    }

    public void addError(String message) {
        errorMessages.add(message);
    }

    public List<String> getErrors() {
        return new ArrayList<>(errorMessages);
    }
}
