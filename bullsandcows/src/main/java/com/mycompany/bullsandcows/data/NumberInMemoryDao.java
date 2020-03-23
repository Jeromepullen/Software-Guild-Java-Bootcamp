/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jeromepullenjr
 */
@Repository
@Profile("memory")
public class NumberInMemoryDao implements NumberDao {

    private static final List<Game> games = new ArrayList<>(); //all games
    private static final List<Round> rounds = new ArrayList<>(); //all rounds

    @Override
    public List<Round> getAllRounds() {
        return new ArrayList<>(rounds);
    }

    @Override
    public List<Round> getRoundsById(int gameId) {
        return new ArrayList<>(
                (Collection<? extends Round>) rounds.stream()
                        .filter(g -> g.getGameId() == gameId)
                        .findFirst()
                        .orElse(null));
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    @Override
    public Game getGameById(int gameId) {
//        Round round = new Round();
//        round.setGameId(gameId);
//        int exact = round.getExact();
//
//                if (exact < 4) {
//            game.setWinningNumbers("****");
//        }

        return games.stream()
                .filter(g -> g.getGameId() == gameId)
                .findFirst()
                .orElse(null);

    }

    @Override
    public Game addGame(Game game) {
        games.add(game);
        return game;
    }

    @Override
    public Round addRound(Round round) {
        rounds.add(round);
        return round;
    }

    @Override
    public void deleteRoundById(int id) {
        rounds.removeIf(i -> i.getGameId() == id);
    }

    @Override
    public void deleteGameById(int id) {
        games.removeIf(i -> i.getGameId() == id);
    }

}
