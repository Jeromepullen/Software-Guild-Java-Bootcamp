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
public class Game {

    private int gameId;
    private String winningNumbers;

    public Game() {
    }

    public Game(int gameId, String winningNumbers) {
        this.gameId = gameId;
        this.winningNumbers = winningNumbers;

    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(String winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

}
