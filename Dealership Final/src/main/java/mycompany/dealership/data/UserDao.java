package mycompany.dealership.data;

import mycompany.dealership.data.RoleDao.RoleMapper;
import mycompany.dealership.models.Role;
import mycompany.dealership.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jeromepullenjr
 */
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getById(int id) {
        try {
            final String SELECT_USER_BY_ID = "SELECT id, username, email, password, enabled, firstName, lastName FROM user WHERE id = ?";
            User user = jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);
            user.setRoles(getRolesById(user.getId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public User getByUsername(String username) {
        try {
            final String SELECT_USER_BY_USERNAME = "SELECT id, username, email, password, enabled, firstName, lastName FROM user WHERE username = ?";
            User user = jdbcTemplate.queryForObject(SELECT_USER_BY_USERNAME, new UserMapper(), username);
            user.setRoles(getRolesById(user.getId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public List<User> getAll() {
        final String SELECT_ALL_USERS = "SELECT id, username, email, password, enabled, firstName, lastName FROM user";
        List<User> users = jdbcTemplate.query(SELECT_ALL_USERS, new UserMapper());
        for (User user : users) {
            user.setRoles(getRolesById(user.getId()));
        }
        return users;
    }

    private Set<Role> getRolesById(int id) throws DataAccessException {
        final String SELECT_ROLES_FOR_USER = "SELECT r.* FROM user_role ur "
                + "JOIN role r ON ur.role_id = r.id "
                + "WHERE ur.user_id = ?";
        Set<Role> roles = new HashSet(jdbcTemplate.query(SELECT_ROLES_FOR_USER, new RoleMapper(), id));
        return roles;
    }

    public void update(User user) {
        final String UPDATE_USER = "UPDATE user SET username = ?, password = ?,enabled = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_USER, user.getUsername(), user.getPassword(), user.isEnabled(), user.getId());

        final String DELETE_USER_ROLE = "DELETE FROM user_role WHERE user_id = ?";
        jdbcTemplate.update(DELETE_USER_ROLE, user.getId());
        for (Role role : user.getRoles()) {
            final String INSERT_USER_ROLE = "INSERT INTO user_role(user_id, role_id) VALUES(?,?)";
            jdbcTemplate.update(INSERT_USER_ROLE, user.getId(), role.getId());
        }
    }

    public void delete(int id) {
        final String DELETE_USER_ROLE = "DELETE FROM user_role WHERE user_id = ?";
        final String DELETE_USER = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(DELETE_USER_ROLE, id);
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Transactional
    public User create(User user) {
        final String INSERT_USER = "INSERT INTO user(username, password, enabled, firstName, lastName, email) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(INSERT_USER, user.getUsername(), user.getPassword(), user.isEnabled(), user.getFirstName(), user.getLastName(), user.getEmail());
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        user.setId(newId);

        for (Role role : user.getRoles()) {
            final String INSERT_USER_ROLE = "INSERT INTO user_role(user_id, role_id) VALUES(?,?)";
            jdbcTemplate.update(INSERT_USER_ROLE, user.getId(), role.getId());
        }
        return user;
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}
