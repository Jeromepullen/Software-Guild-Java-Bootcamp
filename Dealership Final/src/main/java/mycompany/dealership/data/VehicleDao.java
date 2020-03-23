package mycompany.dealership.data;

import mycompany.dealership.models.SearchCriteria;
import mycompany.dealership.models.Vehicle;
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
public class VehicleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VehicleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Vehicle add(Vehicle vehicle) {

        final String sql = "INSERT INTO vehicle(modelId, vin, isNew, mileage, year, "
                + "bodyStyleId, isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, vehicle.getModelId());
            statement.setString(2, vehicle.getVin());
            statement.setBoolean(3, vehicle.isIsNew());
            statement.setInt(4, vehicle.getMileage());
            statement.setInt(5, vehicle.getYear());
            statement.setInt(6, vehicle.getBodyStyleId());
            statement.setBoolean(7, vehicle.isIsAutomatic());
            statement.setBigDecimal(8, vehicle.getMsrp());
            statement.setBigDecimal(9, vehicle.getPrice());
            statement.setInt(10, vehicle.getExteriorColorId());
            statement.setInt(11, vehicle.getInteriorColorId());
            statement.setString(12, vehicle.getDescription());
            statement.setBoolean(13, vehicle.isIsFeatured());
            statement.setString(14, vehicle.getUrl());
            statement.setBoolean(15, false);
            return statement;

        }, keyHolder);

        vehicle.setVehicleId(keyHolder.getKey().intValue());
        return vehicle;
    }

    public List<Vehicle> getAll() {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased FROM vehicle;";
        return jdbcTemplate.query(sql, new VehicleMapper());
    }

    public List<Vehicle> getAllUsedCars() {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased FROM vehicle where isNew = false;";
        return jdbcTemplate.query(sql, new VehicleMapper());
    }

    public List<Vehicle> getAllNewCars() {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, url, isFeatured, isPurchased FROM vehicle where isNew = true;";
        return jdbcTemplate.query(sql, new VehicleMapper());
    }

    public List<Vehicle> getByYear(int year) {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased FROM vehicle where year = ?;";
        return jdbcTemplate.query(sql, new VehicleMapper(), year);

    }

    public List<Vehicle> getByYearPartial(int year) {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased FROM vehicle where year like '%?%';";
        return jdbcTemplate.query(sql, new VehicleMapper(), year);
    }

    public Vehicle findById(int id) {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, url, isFeatured, isPurchased FROM vehicle where vehicleId = ?;";
        return jdbcTemplate.queryForObject(sql, new VehicleMapper(), id);
    }

    public List<Vehicle> getFeaturedVehicles() {
        final String sql = "SELECT vehicleId, modelId, vin, isNew, mileage, year, BodyStyleId, "
                + "isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, url, isFeatured, isPurchased FROM vehicle where isFeatured = true;";
        return jdbcTemplate.query(sql, new VehicleMapper());
    }

    public List<Vehicle> getVehicleSearchNew(SearchCriteria search) {
        final String sql = "select "
                + "vehicle.year year, "
                + "vehicle.vehicleId vehicleId, "
                + "vehicle.bodyStyleId bodyStyleId, "
                + "vehicle.exteriorColorId exteriorColorId, "
                + "vehicle.interiorColorId interiorColorId, "
                + "vehicle.isFeatured isFeatured, "
                + "vehicle.isPurchased isPurchased, "
                + "vehicle.modelId modelId, "
                + "model.name, "
                + "make.name, "
                + "vehicle.price price, "
                + "vehicle.msrp msrp, "
                + "vehicle.vin vin, "
                + "vehicle.description description, "
                + "bodyStyle.name, "
                + "exterior.name, "
                + "interior.name, "
                + "vehicle.mileage mileage, "
                + "vehicle.isNew isNew, "
                + "vehicle.isAutomatic isAutomatic, "
                + "vehicle.url url "
                + "from vehicle "
                + "inner join model on model.modelId = vehicle.modelId "
                + "inner join make on model.makeId = make.makeId "
                + "inner join color exterior on vehicle.exteriorColorId = exterior.colorId "
                + "inner join color interior on vehicle.interiorColorId = interior.colorId "
                + "inner join bodyStyle on vehicle.bodyStyleId = bodyStyle.bodyStyleId "
                + "where (model.name like '%" + search.getModel() + "%' "
                + "or make.name like '%" + search.getMake() + "%' "
                + "or vehicle.year = " + search.getYear() + ") "
                + "and vehicle.isPurchased = 0 "
                + "and vehicle.isNew = true "
                + "order by vehicle.price DESC;";
        return jdbcTemplate.query(sql, new VehicleMapper());

    }

    public List<Vehicle> getVehicleSearchUsed(SearchCriteria search) {
        final String sql = "select "
                + "vehicle.year year, "
                + "vehicle.vehicleId vehicleId, "
                + "vehicle.bodyStyleId bodyStyleId, "
                + "vehicle.exteriorColorId exteriorColorId, "
                + "vehicle.interiorColorId interiorColorId, "
                + "vehicle.isFeatured isFeatured, "
                + "vehicle.isPurchased isPurchased, "
                + "vehicle.modelId modelId, "
                + "model.name, "
                + "make.name, "
                + "vehicle.price price, "
                + "vehicle.msrp msrp, "
                + "vehicle.vin vin, "
                + "vehicle.description description, "
                + "bodyStyle.name, "
                + "exterior.name, "
                + "interior.name, "
                + "vehicle.mileage mileage, "
                + "vehicle.isNew isNew, "
                + "vehicle.isAutomatic isAutomatic, "
                + "vehicle.url url "
                + "from vehicle "
                + "inner join model on model.modelId = vehicle.modelId "
                + "inner join make on model.makeId = make.makeId "
                + "inner join color exterior on vehicle.exteriorColorId = exterior.colorId "
                + "inner join color interior on vehicle.interiorColorId = interior.colorId "
                + "inner join bodyStyle on vehicle.bodyStyleId = bodyStyle.bodyStyleId "
                + "where (model.name like '%" + search.getModel() + "%' "
                + "or make.name like '%" + search.getMake() + "%' "
                + "or vehicle.year = " + search.getYear() + ") "
                + "and vehicle.isPurchased = 0 "
                + "and vehicle.isNew = false "
                + "order by vehicle.price DESC;";
        return jdbcTemplate.query(sql, new VehicleMapper());

    }

    public boolean update(Vehicle vehicle) {

        final String sql = "UPDATE vehicle SET "
                + "modelId = ?, "
                + "vin = ?, "
                + "isNew = ?, "
                + "mileage = ?, "
                + "year = ?, "
                + "BodyStyleId = ?, "
                + "isAutomatic = ?, "
                + "msrp = ?, "
                + "price = ?, "
                + "exteriorColorId = ?, "
                + "interiorColorId = ?, "
                + "description = ?, "
                + "url = ?, "
                + "isFeatured = ?, "
                + "isPurchased = ? "
                + "WHERE vehicleId = ?;";

        return jdbcTemplate.update(sql,
                vehicle.getModelId(),
                vehicle.getVin(),
                vehicle.isIsNew(),
                vehicle.getMileage(),
                vehicle.getYear(),
                vehicle.getBodyStyleId(),
                vehicle.isIsAutomatic(),
                vehicle.getMsrp(),
                vehicle.getPrice(),
                vehicle.getExteriorColorId(),
                vehicle.getInteriorColorId(),
                vehicle.getDescription(),
                vehicle.getUrl(),
                vehicle.isIsFeatured(),
                vehicle.isIsPurchased(),
                vehicle.getVehicleId()) > 0;
    }

    @Transactional
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM vehicle WHERE vehicleId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class VehicleMapper implements RowMapper<Vehicle> {

        @Override
        public Vehicle mapRow(ResultSet rs, int index) throws SQLException {
            Vehicle v = new Vehicle();
            v.setBodyStyleId(rs.getInt("bodyStyleId"));
            v.setDescription(rs.getString("description"));
            v.setExteriorColorId(rs.getInt("exteriorColorId"));
            v.setInteriorColorId(rs.getInt("interiorColorId"));
            v.setIsAutomatic(rs.getBoolean("IsAutomatic"));
            v.setIsNew(rs.getBoolean("isNew"));
            v.setMileage(rs.getInt("mileage"));
            v.setModelId(rs.getInt("modelId"));
            v.setMsrp(rs.getBigDecimal("msrp"));
            v.setPrice(rs.getBigDecimal("price"));
            v.setVehicleId(rs.getInt("vehicleId"));
            v.setVin(rs.getString("vin"));
            v.setYear(rs.getInt("year"));
            v.setUrl(rs.getString("url"));
            v.setIsFeatured(rs.getBoolean("isFeatured"));
            v.setIsPurchased(rs.getBoolean("isPurchased"));
            return v;
        }
    }
}
