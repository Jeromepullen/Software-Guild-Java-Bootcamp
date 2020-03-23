/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.Appointment;
import model.Professional;
import model.Specialty;

/**
 *
 * @author jeromepullenjr
 */
//read file collection object
//---------------------------
public class DaoImplProfessional implements DaoProfessional {

    private static final String DELIMITER = ",";
    static final String FILE_NAME = "data"
            + File.separator + "professionals"
            + File.separator + "professionals.txt";

    public List<Professional> professionals = new ArrayList<>();

    public String getFilePath() {
        return FILE_NAME;
    }

    public List<Professional> getProfessionals() throws DaoPersistanceException {
        readFile(FILE_NAME);

        return professionals;
    }

    public List<Professional> findByProfessionalLastName(LocalDate date, String pro) throws DaoPersistanceException {

        return (List<Professional>) getProfessionals().stream()
                .filter(A -> A.getDentalProLastName().equals(pro))
                .collect(Collectors.toList());

    }

    public Professional findByProfessionalLastName2(String professionalLastName) throws DaoPersistanceException {
        return getProfessionals().stream()
                .filter(p -> p.getDentalProLastName().equals(professionalLastName))
                .findFirst().orElse(null);
    }

    @Override
    public Professional getProfessional(String type) throws DaoPersistanceException {
        readFile(FILE_NAME);

        for (int i = 0; i < professionals.size(); i++) {
            if (professionals.get(i).getType().equalsIgnoreCase(type)) {
                return professionals.get(i);
            }
        }
        throw new DaoPersistanceException("Not a professional in file");
    }

    public List<Professional> readFile(String fileName) throws DaoPersistanceException {
        if (!professionals.isEmpty()) {
            return professionals;
        }

        Scanner scanner;
        int currentLineNum = 0;

        try {
            scanner = new Scanner(new FileReader(fileName));

            //skips the first line of the file
            //first line of file is 'category/column' names
            scanner.nextLine();
            currentLineNum++;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineParts = line.split(DELIMITER);

                Professional professional = new Professional();

                professional.setProId(Integer.parseInt(lineParts[0]));
                professional.setFirstName(lineParts[1]);
                professional.setLastName(lineParts[2]);
                Specialty.valueOf(lineParts[3]);
                professional.setHourlyRate(new BigDecimal(lineParts[4]));

                professionals.add(professional);
                currentLineNum++;
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            String message = "Could not load: \n"
                    + fileName;

            throw new DaoPersistanceException(message);
        } catch (NumberFormatException ex) {
            String message = "Data unable to be parsed in\n"
                    + fileName
                    + "\non line "
                    + currentLineNum;

            throw new DaoPersistanceException(message);
        } catch (IndexOutOfBoundsException ex) {
            String message = "Unable to be parsed correctly in \n"
                    + fileName
                    + "\non line "
                    + currentLineNum;

            throw new DaoPersistanceException(message);
        }

        return professionals;
    }
}
