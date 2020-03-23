package mycompany.dealership.data;

import mycompany.dealership.models.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * @author jeromepullenjr
 */
@Repository
public class ContactDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Contact add(Contact contact) {

        final String sql = "INSERT INTO contact(name, email, phone, message) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getMessage());
            return statement;

        }, keyHolder);

        contact.setContactId(keyHolder.getKey().intValue());

        return contact;
    }

    public List<Contact> getAll() {
        final String sql = "SELECT contactId, name, email, phone, message FROM contact;";
        return jdbcTemplate.query(sql, new ContactMapper());
    }

    public Contact findById(int id) {

        final String sql = "SELECT contactId, name, email, phone, message "
                + "FROM contact WHERE contactId = ?;";

        return jdbcTemplate.queryForObject(sql, new ContactMapper(), id);
    }

    public boolean update(Contact contact) {

        final String sql = "UPDATE contact SET "
                + "name = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "message = ? "
                + "WHERE contactId = ?;";

        return jdbcTemplate.update(sql,
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getMessage(),
                contact.getContactId()) > 0;
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM contact WHERE contactId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class ContactMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int index) throws SQLException {
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contactId"));
            contact.setEmail(rs.getString("email"));
            contact.setMessage(rs.getString("message"));
            contact.setName(rs.getString("name"));
            contact.setPhone(rs.getString("phone"));
            return contact;
        }
    }

}
