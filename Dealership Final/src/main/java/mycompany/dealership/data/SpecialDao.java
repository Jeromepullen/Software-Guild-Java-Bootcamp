package mycompany.dealership.data;

import mycompany.dealership.models.Special;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * @author jeromepullenjr
 */
@Repository
public class SpecialDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpecialDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Special add(Special special) {

        final String sql = "INSERT INTO special(title, description) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, special.getTitle());
            statement.setString(2, special.getDescription());

            return statement;

        }, keyHolder);

        special.setSpecialId(keyHolder.getKey().intValue());

        return special;
    }

    public List<Special> getAll() {
        final String sql = "SELECT specialId, title, description FROM special;";
        return jdbcTemplate.query(sql, new SpecialMapper());
    }

    public Special findById(int id) {

        final String sql = "SELECT specialId, title, description "
                + "FROM special WHERE specialId = ?;";

        return jdbcTemplate.queryForObject(sql, new SpecialMapper(), id);
    }

    public boolean update(Special special) {

        final String sql = "UPDATE special SET "
                + "title = ?, "
                + "description = ? "
                + "WHERE specialId = ?;";

        return jdbcTemplate.update(sql,
                special.getTitle(),
                special.getDescription(),
                special.getSpecialId()) > 0;
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM special WHERE specialId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class SpecialMapper implements RowMapper<Special> {

        @Override
        public Special mapRow(ResultSet rs, int index) throws SQLException {
            Special special = new Special();
            special.setTitle(rs.getString("title"));
            special.setDescription(rs.getString("description"));
            special.setSpecialId(rs.getInt("specialId"));
            return special;
        }
    }
}
