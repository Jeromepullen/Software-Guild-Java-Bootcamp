/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Professional;
import Model.Specialty;
import java.io.File;
import java.math.BigDecimal;

/**
 *
 * @author jeromepullenjr
 */
public class ProfessionalDaoImpl implements ProfessionalDao {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data" + File.separator + "professional.txt";

    private String marshall(Professional professional, Specialty specialty) {
        return professional.getProId() + DELIMITER
                + professional.getFirstName() + DELIMITER
                + professional.getLastName() + DELIMITER
                + specialty.toString() + DELIMITER
                + professional.getHourlyRate();
    }

    private Professional unmarshall(String line) {

        String[] tokens = line.split(DELIMITER);
        if (tokens.length != 4) {
            return null;
        }

        Professional professional = new Professional();
        professional.setProId(Integer.parseInt(tokens[0]));
        professional.setFirstName(tokens[1]);
        professional.setLastName(tokens[2]);
        Specialty.valueOf(tokens[3]);
        professional.setHourlyRate(new BigDecimal(tokens[4]));
        return professional;
    }

}
