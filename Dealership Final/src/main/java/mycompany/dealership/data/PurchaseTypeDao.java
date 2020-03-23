package mycompany.dealership.data;

import mycompany.dealership.models.PurchaseType;
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
public class PurchaseTypeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PurchaseTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PurchaseType add(PurchaseType purchase) {

        final String sql = "INSERT INTO purchaseType(name) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, purchase.getName());
            return statement;

        }, keyHolder);

        purchase.setPurchaseTypeId(keyHolder.getKey().intValue());

        return purchase;
    }

    public List<PurchaseType> getAll() {
        final String sql = "SELECT purchaseTypeId, name FROM purchaseType;";
        return jdbcTemplate.query(sql, new PurchaseTypeMapper());
    }

    public PurchaseType findById(int id) {

        final String sql = "SELECT purchaseTypeId, name "
                + "FROM purchaseType WHERE purchaseTypeId = ?;";

        PurchaseType output = jdbcTemplate.queryForObject(sql, new PurchaseTypeMapper(), id);
        if (output == null) {
            return null;
        } else {
            return output;
        }
    }

    public boolean deleteById(int id) {
        final String sql = "DELETE FROM purchaseType WHERE purchaseTypeId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class PurchaseTypeMapper implements RowMapper<PurchaseType> {

        @Override
        public PurchaseType mapRow(ResultSet rs, int index) throws SQLException {
            PurchaseType purchase = new PurchaseType();
            purchase.setPurchaseTypeId(rs.getInt("PurchaseTypeId"));
            purchase.setName(rs.getString("name"));
            return purchase;
        }
    }
}
