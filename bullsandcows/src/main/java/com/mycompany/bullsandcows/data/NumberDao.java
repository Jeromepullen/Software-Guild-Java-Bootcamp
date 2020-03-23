/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public interface NumberDao {

    List<Round> getAllRounds();

    List<Round> getRoundsById(int gameId);

    List<Game> getAllGames();

    Game getGameById(int gameId);

    Game addGame(Game game);

    Round addRound(Round round);

    void deleteGameById(int id);

    void deleteRoundById(int id);

}
