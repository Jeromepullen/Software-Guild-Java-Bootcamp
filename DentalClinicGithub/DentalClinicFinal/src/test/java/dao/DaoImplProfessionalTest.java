/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import model.Professional;
import model.Specialty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jeromepullenjr
 */
public class DaoImplProfessionalTest {

    private DaoImplProfessional dip = new DaoImplProfessional();
    private int size = dip.professionals.size();

    private Professional professional1;
    private Professional professional2;
    private Professional professional3;

    public void setUp() {

        professional1 = new Professional();
        professional1.setSpecialty(Specialty.DENTIST);
        professional1.setHourlyRate(new BigDecimal("500"));
        professional1.setFirstName("John");

        professional2 = new Professional();
        professional2.setSpecialty(Specialty.HYGIENIST);
        professional2.setHourlyRate(new BigDecimal("600"));
        professional2.setFirstName("Steve");

        professional3 = new Professional();
        professional3.setSpecialty(Specialty.ORAL_SURGEON);
        professional3.setHourlyRate(new BigDecimal("5"));
        professional3.setLastName("Johnson");

        dip.professionals.add(professional1);
        dip.professionals.add(professional2);
        dip.professionals.add(professional3);
    }

    public void tearDown() {
        dip.professionals.remove(professional1);
        dip.professionals.remove(professional2);
        dip.professionals.remove(professional3);
    }

    @Test
    public void testGetProfessional() {
        try {
            assertEquals(professional1, dip.getProfessional("DENTIST"));
            assertEquals(professional2, dip.getProfessional("HYGIENIST"));
            assertEquals(professional3, dip.getProfessional("ORAL_SURGEON"));
            //pass
        } catch (DaoPersistanceException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testBadInput() {
        try {
            assertNull(dip.getProfessional("notAProfessional"));
            fail();
        } catch (DaoPersistanceException ex) {
            //pass
        }
    }
}
