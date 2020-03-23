package mycompany.dealership.data;

import mycompany.dealership.models.BodyStyle;
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
public class BodyStyleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BodyStyleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BodyStyle add(BodyStyle bs) {

        final String sql = "INSERT INTO bodyStyle(name) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, bs.getName());
            return statement;

        }, keyHolder);

        bs.setBodyStyleId(keyHolder.getKey().intValue());

        return bs;
    }

    public List<BodyStyle> getAll() {
        final String sql = "SELECT bodyStyleId, name FROM bodyStyle;";
        return jdbcTemplate.query(sql, new BodyStyleMapper());
    }

    public BodyStyle findById(int id) {

        final String sql = "SELECT bodyStyleId, name "
                + "FROM bodyStyle WHERE bodyStyleId = ?;";

        BodyStyle output = jdbcTemplate.queryForObject(sql, new BodyStyleMapper(), id);
        if (output == null) {
            return null;
        } else {
            return output;
        }
    }

    public boolean update(BodyStyle bs) {

        final String sql = "UPDATE bodyStyle SET "
                + "name = ? "
                + "WHERE bodyStyleId = ?;";

        return jdbcTemplate.update(sql,
                bs.getName(),
                bs.getBodyStyleId()) > 0;
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM bodyStyle WHERE bodyStyleId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class BodyStyleMapper implements RowMapper<BodyStyle> {

        @Override
        public BodyStyle mapRow(ResultSet rs, int index) throws SQLException {
            BodyStyle bs = new BodyStyle();
            bs.setBodyStyleId(rs.getInt("bodyStyleId"));
            bs.setName(rs.getString("name"));
            return bs;
        }
    }
}
