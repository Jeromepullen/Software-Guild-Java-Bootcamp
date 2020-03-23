package mycompany.dealership.data;

import mycompany.dealership.models.Make;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jeromepullenjr
 */
@Repository
public class MakeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MakeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Make add(Make make) {

        final String sql = "INSERT INTO make(name) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, make.getName());
            return statement;

        }, keyHolder);

        make.setMakeId(keyHolder.getKey().intValue());

        return make;
    }

    public List<Make> getAll() {
        final String sql = "SELECT makeId, name FROM make;";
        return jdbcTemplate.query(sql, new MakeMapper());
    }

    public Make findById(int id) {

        final String sql = "SELECT makeId, name "
                + "FROM make WHERE makeId = ?;";

        return jdbcTemplate.queryForObject(sql, new MakeMapper(), id);
    }

    public Make findByModelId(int modelId) {
        final String sql = "select "
                + "make.name, "
                + "make.makeId "
                + "from make "
                + "inner join model on make.makeId = model.makeId "
                + "where model.modelId = ?;";

        return jdbcTemplate.queryForObject(sql, new MakeMapper(), modelId);
    }

    public boolean update(Make make) {

        final String sql = "UPDATE make SET "
                + "name = ? "
                + "WHERE makeId = ?;";

        return jdbcTemplate.update(sql,
                make.getName(),
                make.getMakeId()) > 0;
    }

    @Transactional
    public void deleteById(int id) {
        final String delete_models = "DELETE from model WHERE makeId = ?";
        jdbcTemplate.update(delete_models, id);

        final String delete_make = "DELETE FROM make WHERE makeId = ?";
        jdbcTemplate.update(delete_make, id);
    }

    private static final class MakeMapper implements RowMapper<Make> {

        @Override
        public Make mapRow(ResultSet rs, int index) throws SQLException {
            Make make = new Make();
            make.setMakeId(rs.getInt("makeId"));
            make.setName(rs.getString("name"));
            return make;
        }
    }
}
