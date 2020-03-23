package mycompany.dealership.data;

import mycompany.dealership.data.ContactDao;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.Contact;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author jeromepullenjr
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ContactDaoTest {

    @Autowired
    ContactDao cDao;

    @BeforeEach
    public void setUp() {

        List<Contact> contacts = cDao.getAll();
        for (Contact contact : contacts) {
            cDao.deleteById(contact.getContactId());
        }

    }

    //name, email, phone, message
    @Test
    public void testAddContact() {
        assertTrue(cDao.getAll().size() == 0);
        Contact contact = new Contact();
        contact.setName("Test Contact");
        contact.setEmail("test@test.com");
        contact.setPhone("Test Phone");
        contact.setMessage("Test Message");
        contact = cDao.add(contact);

        Contact fromDao = cDao.findById(contact.getContactId());
        assertEquals(contact, fromDao);
    }

    @Test
    public void testGetAllContacts() {
        assertTrue(cDao.getAll().size() == 0);
        Contact contact = new Contact();
        contact.setName("Test Contact");
        contact.setEmail("test@test.com");
        contact.setPhone("Test Phone");
        contact.setMessage("Test Message");
        contact = cDao.add(contact);

        Contact secondContact = new Contact();
        secondContact.setName("Test Contact");
        secondContact.setEmail("test@test.com");
        secondContact.setPhone("Test Phone");
        secondContact.setMessage("Test Message");
        secondContact = cDao.add(secondContact);
        List<Contact> testContacts = cDao.getAll();

        assertEquals(2, testContacts.size());
        assertTrue(testContacts.contains(secondContact));
        assertTrue(testContacts.contains(contact));
    }

    @Test
    public void testUpdateContact() {
        assertTrue(cDao.getAll().size() == 0);
        Contact contact = new Contact();
        contact.setName("Test Contact");
        contact.setEmail("test@test.com");
        contact.setPhone("Test Phone");
        contact.setMessage("Test Message");
        contact = cDao.add(contact);

        Contact fromDao = cDao.findById(contact.getContactId());

        assertEquals(contact, fromDao);

        contact.setName("Another Test Name");

        cDao.update(contact);

        assertNotEquals(contact, fromDao);

        fromDao = cDao.findById(contact.getContactId());

        assertEquals(contact, fromDao);
    }

    @Test
    public void testDeleteContact() {
        assertTrue(cDao.getAll().size() == 0);
        Contact contact = new Contact();
        contact.setName("Test Contact");
        contact.setEmail("test@test.com");
        contact.setPhone("Test Phone");
        contact.setMessage("Test Message");
        contact = cDao.add(contact);
        assertTrue(cDao.getAll().size() == 1);
        cDao.deleteById(contact.getContactId());
        assertTrue(cDao.getAll().size() == 0);
    }

}
