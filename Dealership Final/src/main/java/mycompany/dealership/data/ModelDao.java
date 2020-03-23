package mycompany.dealership.data;

import mycompany.dealership.models.CarModel;
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
public class ModelDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ModelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CarModel add(CarModel model) {

        final String sql = "INSERT INTO model(makeId, name) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, model.getMakeId());
            statement.setString(2, model.getName());
            return statement;

        }, keyHolder);

        model.setModelId(keyHolder.getKey().intValue());

        return model;
    }

    public List<CarModel> getAll() {
        final String sql = "SELECT model.modelId, model.makeId, model.name FROM model "
                + "inner join make on make.makeId = model.makeId;";
        return jdbcTemplate.query(sql, new ModelMapper());
    }

    public List<CarModel> getAllByBrandId(int makeId) {
        final String sql = "SELECT model.modelId, model.makeId, model.name FROM model "
                + "inner join make on make.makeId = model.makeId where makeId = ?;";
        return jdbcTemplate.query(sql, new ModelMapper(), makeId);
    }

    public List<CarModel> getAllByBrandName(String brandName) {
        final String sql = "SELECT model.modelId, model.makeId, model.name FROM model "
                + "inner join make on make.makeId = model.makeId where make.name = ?;";
        List<CarModel> output = jdbcTemplate.query(sql, new ModelMapper(), brandName);
        return output;
    }

    public boolean update(CarModel model) {

        final String sql = "UPDATE model SET "
                + "name = ?, "
                + "makeId = ?"
                + "WHERE modelId = ?;";

        return jdbcTemplate.update(sql,
                model.getName(),
                model.getMakeId(),
                model.getModelId()) > 0;
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM model WHERE modelId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public CarModel findById(int modelId) {

        final String sql = "SELECT makeId, name, modelId "
                + "FROM model WHERE modelId = ?;";

        return jdbcTemplate.queryForObject(sql, new ModelMapper(), modelId);
    }

    private static final class ModelMapper implements RowMapper<CarModel> {

        @Override
        public CarModel mapRow(ResultSet rs, int index) throws SQLException {
            CarModel model = new CarModel();
            model.setMakeId(rs.getInt("makeId"));
            model.setModelId(rs.getInt("modelId"));
            model.setName(rs.getString("name"));
            return model;
        }
    }
}
