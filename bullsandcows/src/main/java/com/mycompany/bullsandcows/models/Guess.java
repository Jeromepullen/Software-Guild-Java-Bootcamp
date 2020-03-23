/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.models;

/**
 *
 * @author jeromepullenjr
 */
public class Guess {

    private int gameId;
    private String guessAsString;

    public Guess() {
    }

    public Guess(int gameId, String guessAsString) {
        this.gameId = gameId;
        this.guessAsString = guessAsString;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuessAsString() {
        return guessAsString;
    }

    public void setGuessAsString(String guessAsString) {
        this.guessAsString = guessAsString;
    }
}
