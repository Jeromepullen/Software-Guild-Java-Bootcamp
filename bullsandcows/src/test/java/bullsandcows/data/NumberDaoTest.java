/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullsandcows.data;

import bullsandcows.TestConfig;
import com.mycompany.bullsandcows.data.NumberDatabaseDao;
import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author jeromepullenjr
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
public class NumberDaoTest {

    @Autowired
    NumberDatabaseDao dao;

    public NumberDaoTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Round> rounds = dao.getAllRounds();
//        for (Round r : rounds) {
//            if (r != null) {
//                dao.deleteRoundById(r.getRoundNumber());
//            }
//        }
        List<Game> games = dao.getAllGames();
        for (Game g : games) {
            if (g != null) {
                dao.deleteGameById(g.getGameId());
            }
        }

    }

    /*
     * Test of getAllGames method, of class NumberDao.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game(1, "1234");
//        Game game2 = new Game(2, "4321");
        Round round = new Round(1, 1, 1, Timestamp.valueOf(LocalDateTime.now()), "1234", 1);

        assertTrue(dao.getAllGames().isEmpty());
//        dao.addGame(game2);
        dao.addGame(game);
        assertEquals(2, dao.getAllGames().size());
    }

    /**
     * Test of getAllRounds method, of class NumberDao. public Round(int
     * roundNumber, int exact, int partial, Timestamp time, String guess, int
     * gameId)
     */
    @Test
    public void testGetAllRounds() {
        assertTrue(dao.getAllRounds().isEmpty());
        Game game = new Game();
        game.setWinningNumbers("1234");
        dao.addGame(game);
        Round round = new Round();
        round.setExact(4);
        round.setGameId(game.getGameId());
        round.setGuess("1234");
        round.setPartial(0);
        round.setTime(Timestamp.valueOf(LocalDateTime.now()));
        dao.addRound(round);
        assertTrue(dao.getAllRounds().size() > 0);
    }

    /**
     * Test of getRoundsById method, of class NumberDao.
     */
    @Test
    public void testGetRoundsById() {
        assertTrue(dao.getAllRounds().isEmpty());
        Game game = new Game();
        game.setWinningNumbers("1234");
        dao.addGame(game);
        int gameId = game.getGameId();
        Round round = new Round();
        round.setExact(4);
        round.setGameId(gameId);
        round.setGuess("1234");
        round.setPartial(0);
        round.setTime(Timestamp.valueOf(LocalDateTime.now()));
        dao.addRound(round);
        Round round2 = new Round();
        round2.setExact(3);
        round2.setGameId(gameId);
        round2.setGuess("1274");
        round2.setPartial(0);
        round2.setTime(Timestamp.valueOf(LocalDateTime.now()));
        dao.addRound(round2);
        assertTrue(dao.getRoundsById(gameId).size() == 2);
    }

}
