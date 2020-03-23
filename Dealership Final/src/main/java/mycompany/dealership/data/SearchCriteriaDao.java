package mycompany.dealership.data;

import mycompany.dealership.models.SearchCriteria;
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
public class SearchCriteriaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchCriteriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchCriteria add(SearchCriteria search) {

        final String sql = "INSERT INTO search(make, model, minYear, maxYear, minPrice, maxPrice, year) VALUES (?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, search.getMake());
            statement.setString(2, search.getModel());
            statement.setInt(3, search.getMinYear());
            statement.setInt(4, search.getMaxYear());
            statement.setInt(5, search.getMinPrice());
            statement.setInt(6, search.getMaxPrice());
            statement.setInt(7, search.getYear());
            return statement;

        }, keyHolder);

        search.setSearchId(keyHolder.getKey().intValue());

        return search;
    }

    public List<SearchCriteria> getAll() {
        final String sql = "SELECT make, model, minYear, maxYear, minPrice, maxPrice, year FROM search;";
        return jdbcTemplate.query(sql, new SearchMapper());
    }

    public SearchCriteria findById(int id) {
        final String sql = "SELECT make, model, minYear, maxYear, minPrice, maxPrice, year FROM search WHERE searchId = ?;";
        return jdbcTemplate.queryForObject(sql, new SearchMapper(), id);
    }

    private static final class SearchMapper implements RowMapper<SearchCriteria> {

        @Override
        public SearchCriteria mapRow(ResultSet rs, int index) throws SQLException {
            SearchCriteria search = new SearchCriteria();
            search.setSearchId(rs.getInt("searchId"));
            search.setMake(rs.getString("make"));
            search.setModel(rs.getString("model"));
            search.setMinYear(rs.getInt("minYear"));
            search.setMaxYear(rs.getInt("maxYear"));
            search.setMinPrice(rs.getInt("minPrice"));
            search.setMaxPrice(rs.getInt("maxPrice"));
            search.setYear(rs.getInt("year"));
            return search;
        }
    }

}
