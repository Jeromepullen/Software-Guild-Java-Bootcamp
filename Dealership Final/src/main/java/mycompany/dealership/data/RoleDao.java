package mycompany.dealership.data;

import mycompany.dealership.models.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
public class RoleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Role getById(int id) {
        try {
            final String SELECT_ROLE_BY_ID = "SELECT id, role FROM role WHERE id = ?";
            return jdbcTemplate.queryForObject(SELECT_ROLE_BY_ID, new RoleMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public Role getByRoleName(String role) {
        try {
            final String SELECT_ROLE_BY_ROLE = "SELECT id, role FROM role WHERE role = ?";
            return jdbcTemplate.queryForObject(SELECT_ROLE_BY_ROLE, new RoleMapper(), role);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public List<Role> getAll() {
        final String SELECT_ALL_ROLES = "SELECT id, role FROM role";
        return jdbcTemplate.query(SELECT_ALL_ROLES, new RoleMapper());
    }

    public void delete(int id) {
        final String DELETE_USER_ROLE = "DELETE FROM user_role WHERE role_id = ?";
        final String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
        jdbcTemplate.update(DELETE_USER_ROLE, id);
        jdbcTemplate.update(DELETE_ROLE, id);
    }

    public void update(Role role) {
        final String UPDATE_ROLE = "UPDATE role SET role = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_ROLE, role.getRole(), role.getId());
    }

    @Transactional
    public Role create(Role role) {
        final String INSERT_ROLE = "INSERT INTO role(role) VALUES(?)";
        jdbcTemplate.update(INSERT_ROLE, role.getRole());
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        role.setId(newId);
        return role;
    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRole(rs.getString("role"));
            return role;
        }
    }
}
