///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Dao;
//
//import Model.Professional;
//import Model.Specialty;
//import java.math.BigDecimal;
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNull;
//import static junit.framework.Assert.fail;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// *
// * @author jeromepullenjr
// */
//public class ProfessionalDaoImplTest {
//
//    private ProfessionalDaoImpl dip = new ProfessionalDaoImpl();
//    private int size = dip.professional.size();
//
//    private Professional professional1;
//    private Professional professional2;
//    private Professional professional3;
//
//    @Before
//    public void setUp() {
//
//        professional1 = new Professional();
//        professional1.setSpecialty(Specialty.DENTIST);
//        professional1.setHourlyRate(new BigDecimal("500"));
//        professional1.setFirstName("John");
//
//        professional2 = new Professional();
//        professional2.setSpecialty(Specialty.HYGIENIST);
//        professional2.setHourlyRate(new BigDecimal("600"));
//        professional2.setFirstName("Steve");
//
//        professional3 = new Professional();
//        professional3.setSpecialty(Specialty.ORAL_SURGEON);
//        professional3.setHourlyRate(new BigDecimal("5"));
//        professional3.setLastName("Johnson");
//
//        dip.professional.add(professional1);
//        dip.professional.add(professional2);
//        dip.professional.add(professional3);
//    }
//
//    @After
//    public void tearDown() {
//        dip.professional.remove(professional1);
//        dip.professional.remove(professional2);
//        dip.professional.remove(professional3);
//    }
//
//    @Test
//    public void testGetProfessional() {
//        try {
//            assertEquals(professional1, dip.getProfessional("DENTIST"));
//            assertEquals(professional2, dip.getProfessional("HYGIENIST"));
//            assertEquals(professional3, dip.getProfessional("ORAL_SURGEON"));
//            //pass
//        } catch (DaoPersistanceException ex) {
//            fail(ex.getMessage());
//        }
//    }
//
//    @Test
//    public void testBadInput() {
//        try {
//            assertNull(dip.getProfessional("notAProfessional"));
//            fail();
//        } catch (DaoPersistanceException ex) {
//            //pass
//        }
//    }
//}
//}
