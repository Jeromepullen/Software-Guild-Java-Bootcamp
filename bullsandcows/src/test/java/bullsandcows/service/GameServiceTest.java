/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullsandcows.service;

import bullsandcows.TestConfig;
import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Guess;
import com.mycompany.bullsandcows.service.GameService;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
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
public class GameServiceTest {

    @Autowired
    GameService service;

    public GameServiceTest() {
    }

    /**
     * Test of start method, of class GameService.
     */
//    @Test
//    public void testStart() {
//        Game game = service.start();
//        assertNotNull(game.getGameId()); //asserts the game was created
//        assertNotNull(game.getWinningNumbers()); //asserts that winning numbers were assigned;
//    }
    /**
     * Test of makeWinningNumbers method, of class GameService. test's
     * uniqueness and length
     */
    @Test
    public void testMakeWinningNumbers() {
        String numbers = service.makeWinningNumbers();
        boolean isIdentical = false;
        for (int i = 0; i < numbers.length(); i++) {
            for (int j = 0; j < numbers.length(); j++) {
                if ((numbers.charAt(i) == numbers.charAt(j)) && (i != j)) {
                    isIdentical = true;
                }
            }
        }

        assertFalse(isIdentical);
        assertTrue(numbers.length() == 4);
    }

    /**
     * Test of makeGuess method, of class GameService.
     */
//    @Test
//    public void testMakeGuess() {
//        Game game = service.start();
//        Guess guess = new Guess();
//        guess.setGuessAsString(game.getWinningNumbers());
//        guess.setGameId(game.getGameId());
//        Guess guess2 = new Guess();
//        guess2.setGuessAsString("9999"); //impossible to win
//        guess2.setGameId(game.getGameId());
//        assertTrue(service.makeGuess(guess).getExactGuess() == 4);
//        assertTrue(service.makeGuess(guess).getPartialGuess() == 0);
//        assertFalse(service.makeGuess(guess2).getExactGuess() == 4);
//    }
}
