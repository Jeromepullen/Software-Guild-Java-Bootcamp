/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jeromepullenjr
 */
@Repository
@Profile("database")
public class NumberDatabaseDao implements NumberDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public NumberDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "select exact, partial, time, gameid, guess from round";
        return jdbcTemplate.query(sql, new RoundMapper());
    }

    @Override
    public List<Round> getRoundsById(int gameId) {
        final String sql = "select exact, partial, time, gameid, guess from round having round.gameid = ?;";
        return jdbcTemplate.query(sql, new RoundMapper(), gameId);
    }

    @Override
    public Game getGameById(int gameId) {
        final String sql = "select gameId, winningNumbers "
                + "from game "
                + "group by game.gameid "
                + "having game.gameid = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "select gameId, winningNumbers from game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game addGame(Game game) {
        final String sql = "INSERT INTO game(winningnumbers) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getWinningNumbers());
            return statement;

        }, keyHolder);
        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public Round addRound(Round round) {
        final String sql = "INSERT INTO round(exact, partial, time, gameid, guess) VALUES (?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, round.getExact());
            statement.setInt(2, round.getPartial());
            statement.setTimestamp(3, round.getTime());
            statement.setInt(4, round.getGameId());
            statement.setString(5, round.getGuess());
            return statement;

        }, keyHolder);
//        round.setRoundNumber(keyHolder.getKey().intValue());
        return round;
    }

    @Override
    public void deleteGameById(int id) {
        jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 0;");
        final String sql1 = "DELETE FROM game WHERE gameid = ?;";
        jdbcTemplate.update(sql1, id);
        jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 1;");
    }

    @Override
    public void deleteRoundById(int id) {
        jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 0;");
        final String sql1 = "DELETE FROM round WHERE roundid = ?;";
        jdbcTemplate.update(sql1, id);
        jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 1;");
    }

    private static final class GameMapper implements RowMapper<Game> {

        //Round(int roundNumber, int exact, int partial, Timestamp time, String guess, int gameId)
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameid"));
            game.setWinningNumbers(rs.getString("winningnumbers"));
            return game;

        }
    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();

            round.setGameId(rs.getInt("gameid"));
            round.setTime(rs.getTimestamp("time"));
            round.setGuess(rs.getString("guess"));
//            round.setRoundNumber(rs.getInt("roundid"));
            round.setExact(rs.getInt("exact"));
            round.setPartial(rs.getInt("partial"));
            return round;
        }
    }
}
