package mycompany.dealership.service;

import mycompany.dealership.data.ContactDao;
import mycompany.dealership.data.SpecialDao;
import mycompany.dealership.models.Contact;
import mycompany.dealership.models.Special;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author jeromepullenjr
 */
@Service
public class CustomerService {

    private final ContactDao contactDao;
    private final SpecialDao specialDao;

    public CustomerService(ContactDao contactDao, SpecialDao specialDao) {
        this.contactDao = contactDao;
        this.specialDao = specialDao;
    }

    public void addContact(Contact contact) {
        contactDao.add(contact);
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAll();
    }

    public List<Special> getAllSpecials() {
        return specialDao.getAll();
    }

    public void addSpecial(Special special) {
        specialDao.add(special);
    }

    public boolean deleteSpecial(int specialId) {
        return specialDao.deleteById(specialId);
    }

    public boolean updateSpecial(Special special) {
        return specialDao.update(special);
    }

}
