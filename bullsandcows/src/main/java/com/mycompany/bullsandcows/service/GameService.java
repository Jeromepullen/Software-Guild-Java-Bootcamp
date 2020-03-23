/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.service;

import com.mycompany.bullsandcows.data.NumberDao;
import com.mycompany.bullsandcows.data.NumberDatabaseDao;
import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Guess;
import com.mycompany.bullsandcows.models.GuessResult;
import com.mycompany.bullsandcows.models.GuessStatus;
import com.mycompany.bullsandcows.models.Round;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeromepullenjr
 */
@Service
public class GameService {

    private static final int NUM_NUMBERS = 4;
    private static final Random rand = new Random();

    private final NumberDao numberDao;
    private final NumberDatabaseDao numberDatabaseDao;

    @Autowired
    public GameService(NumberDao numberDao, NumberDatabaseDao numberDatabaseDao) {
        this.numberDao = numberDao;
        this.numberDatabaseDao = numberDatabaseDao;
    }

    public String start() {

        Game g = new Game();
        g.setWinningNumbers(makeWinningNumbers());
        numberDao.addGame(g);
//        return g;
        return "gameId:" + toString().valueOf(g.getGameId());
    }

    public String makeWinningNumbers() {
        Set<Integer> nums = new HashSet<>();
        String output = "";
        while (nums.size() < NUM_NUMBERS) {
            Integer toAdd = rand.nextInt(10);
            nums.add(toAdd);
        }
        List<Integer> list = new ArrayList<>(nums);
        Collections.shuffle(list);
        for (int i = 0; i < NUM_NUMBERS; i++) {
            output += list.get(i);
        }
        return output;
    }
//
//    Game g = dao.findById(gameId);
//
//    return g ;

    public Game findById(int gameId) {
        List<Game> games = numberDao.getAllGames();
//        List<Round> round = numberDatabaseDao.getRoundsById(gameId);
//        int exact = round.();

//        if (exact < 4) {
//            games.toString().equals("****");
//
//        }
//        Game g = dao.findById(gameId);
//if (exactMatches < 4) {
//    g.setAnswer("****");
//}
//return g;
        return games.stream()
                .filter(g -> g.getGameId() == gameId)
                .findFirst()
                .orElse(null);
    }

    public GuessResult makeGuess(Guess guess) {
        List<Game> games = numberDao.getAllGames();
        Round round = new Round();
        GuessResult result = new GuessResult();
        int exact = 0;
        int partial = 0;
        round.setGameId(guess.getGameId());
        round.setGuess(guess.getGuessAsString());
        round.setTime(Timestamp.valueOf(LocalDateTime.now()));
        Game game = games.stream()
                .filter(g -> g.getGameId() == guess.getGameId())
                .findFirst()
                .orElse(null);

        if (game == null) {
            result.setStatus(GuessStatus.NotFound);
            return result;
        }

        //loop to handle exact match assignment
        for (int i = 0; i < guess.getGuessAsString().length(); i++) {
            if (guess.getGuessAsString().charAt(i) == game.getWinningNumbers().charAt(i)) {
                exact++;
            }
        }

        //loop to handle partial match assignment
        for (int i = 0; i < guess.getGuessAsString().length(); i++) {
            for (int j = 0; j < game.getWinningNumbers().length(); j++) {
                if ((guess.getGuessAsString().charAt(i) == game.getWinningNumbers().charAt(j)) && (i != j)) {
                    partial++;
                }
            }
        }

        round.setPartial(partial);
        round.setExact(exact);
        result.setPartialGuess(partial);
        result.setExactGuess(exact);

        if (exact == 4) {
            result.setStatus(GuessStatus.Win);
        } else {
            result.setStatus(GuessStatus.Continue);
        }
        numberDao.addRound(round);
        return result;
    }

    public List<Game> getAllGames() {
        return numberDao.getAllGames();
    }

    public List<Round> getRoundsById(int gameId) {
        return numberDao.getRoundsById(gameId);
    }
}
