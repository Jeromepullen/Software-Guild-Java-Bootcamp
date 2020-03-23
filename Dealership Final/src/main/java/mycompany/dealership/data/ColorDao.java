package mycompany.dealership.data;

import mycompany.dealership.models.Color;
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
public class ColorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ColorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Color add(Color color) {

        final String sql = "INSERT INTO color(name) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, color.getName());
            return statement;

        }, keyHolder);

        color.setColorId(keyHolder.getKey().intValue());

        return color;
    }

    public List<Color> getAll() {
        final String sql = "SELECT colorId, name FROM color;";
        return jdbcTemplate.query(sql, new ColorMapper());
    }

    public Color findById(int id) {

        final String sql = "SELECT colorId, name "
                + "FROM color WHERE colorId = ?;";

        return jdbcTemplate.queryForObject(sql, new ColorMapper(), id);
    }

    public boolean update(Color color) {

        final String sql = "UPDATE color SET "
                + "name = ? "
                + "WHERE colorId = ?;";

        return jdbcTemplate.update(sql,
                color.getName(),
                color.getColorId()) > 0;
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM color WHERE colorId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class ColorMapper implements RowMapper<Color> {

        @Override
        public Color mapRow(ResultSet rs, int index) throws SQLException {
            Color color = new Color();
            color.setName(rs.getString("name"));
            color.setColorId(rs.getInt("colorId"));
            return color;
        }
    }
}
