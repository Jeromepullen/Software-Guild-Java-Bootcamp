package mycompany.dealership.service;

import mycompany.dealership.data.RoleDao;
import mycompany.dealership.data.SaleDao;
import mycompany.dealership.data.UserDao;
import mycompany.dealership.models.Role;
import mycompany.dealership.models.Sale;
import mycompany.dealership.models.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author jeromepullenjr
 */
@Service
public class UserService {

    private final SaleDao saleDao;
    private final RoleDao roleDao;
    private final UserDao userDao;

    public UserService(SaleDao saleDao, RoleDao roleDao, UserDao userDao) {
        this.saleDao = saleDao;
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }

    public List<Sale> getAllSales() {
        return saleDao.getAll();
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void deleteUser(int userId) {
        userDao.delete(userId);
    }

    public User getUserById(int userId) {
        return userDao.getById(userId);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void updateSale(Sale sale) {
        saleDao.update(sale);
    }

    public Sale addSale(Sale sale) {
        return saleDao.add(sale);
    }

    public User addUser(User user) {
        return userDao.create(user);
    }

    public Role getRoleById(int id) {
        return roleDao.getById(id);
    }
}
