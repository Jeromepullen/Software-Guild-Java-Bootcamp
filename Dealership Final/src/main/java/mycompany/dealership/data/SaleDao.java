package mycompany.dealership.data;

import mycompany.dealership.models.Sale;
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
public class SaleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Sale add(Sale sale) {

        final String sql = "INSERT INTO sale(userId, vehicleId, purchaseTypeId, name, email, phone, addressOne, addressTwo, city, stateId, zipcode, timeOfSale) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, sale.getUserId());
            statement.setInt(2, sale.getVehicleId());
            statement.setInt(3, sale.getPurchaseTypeId());
            statement.setString(4, sale.getName());
            statement.setString(5, sale.getEmail());
            statement.setString(6, sale.getPhone());
            statement.setString(7, sale.getAddressOne());
            statement.setString(8, sale.getAddressTwo());
            statement.setString(9, sale.getCity());
            statement.setInt(10, sale.getStateId());
            statement.setString(11, sale.getZipcode());
            statement.setTimestamp(12, sale.getTimeOfSale());
            return statement;

        }, keyHolder);

        sale.setSaleId(keyHolder.getKey().intValue());

        return sale;
    }

    public List<Sale> getAll() {
        final String sql = "SELECT saleId, userId, vehicleId, purchaseTypeId, name, email, phone, "
                + "addressOne, addressTwo, city, stateId, zipcode, timeOfSale from sale;";
        return jdbcTemplate.query(sql, new SaleMapper());
    }

    public Sale findById(int id) {
        final String sql = "SELECT saleId, userId, vehicleId, purchaseTypeId, name, email, phone, "
                + "addressOne, addressTwo, city, stateId, zipcode, timeOfSale from sale where saleId = ?;";
        return jdbcTemplate.queryForObject(sql, new SaleMapper(), id);
    }

    public boolean update(Sale sale) {

        final String sql = "UPDATE sale SET "
                + "userId = ?, "
                + "vehicleId = ?, "
                + "purchaseTypeId = ?, "
                + "name = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "addressOne = ?, "
                + "addressTwo = ?, "
                + "city = ?, "
                + "stateId = ?, "
                + "zipcode = ?, "
                + "timeOfSale = ? "
                + "FROM sale "
                + "WHERE saleId = ?;";

        return jdbcTemplate.update(sql,
                sale.getUserId(),
                sale.getVehicleId(),
                sale.getPurchaseTypeId(),
                sale.getName(),
                sale.getEmail(),
                sale.getPhone(),
                sale.getAddressOne(),
                sale.getAddressTwo(),
                sale.getCity(),
                sale.getStateId(),
                sale.getZipcode(),
                sale.getTimeOfSale(),
                sale.getSaleId()) > 0;
    }

    //parents and children this aint gonna work
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM sale WHERE saleId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class SaleMapper implements RowMapper<Sale> {

        @Override
        public Sale mapRow(ResultSet rs, int index) throws SQLException {
            Sale s = new Sale();
            s.setAddressOne(rs.getString("addressOne"));
            s.setAddressTwo(rs.getString("addressTwo"));
            s.setCity(rs.getString("city"));
            s.setEmail(rs.getString("email"));
            s.setName(rs.getString("name"));
            s.setPhone(rs.getString("phone"));
            s.setPurchaseTypeId(rs.getInt("purchaseTypeId"));
            s.setSaleId(rs.getInt("saleId"));
            s.setStateId(rs.getInt("stateId"));
            s.setTimeOfSale(rs.getTimestamp("timeOfSale"));
            s.setUserId(rs.getInt("userId"));
            s.setVehicleId(rs.getInt("vehicleId"));
            s.setZipcode(rs.getString("zipCode"));
            return s;
        }
    }
}
