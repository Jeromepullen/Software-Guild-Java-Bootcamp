/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author jeromepullenjr
 */
public class VMAuditDaoFileImpl implements VMAuditDao {

    public static final String AUDIT_FILE = "VMaudit.txt"; // specify file name

    @Override
    public void auditFile(String entry) throws VMPersistenceException {
        PrintWriter out;

        try {                          // create new PrintWriter/FileWriter
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {       // catch error
            throw new VMPersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();    // write data, flush stream
    }

}
