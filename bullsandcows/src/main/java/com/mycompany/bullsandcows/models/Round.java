/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.models;

import java.sql.Timestamp;

/**
 *
 * @author jeromepullenjr
 */
public class Round {

    private String guess;
    private int exact;
    private int partial;
    private Timestamp time;
    private int gameId;

    public Round() {

    }

    public Round(String guess, int exact, int partial, Timestamp time, int gameId) {
        this.guess = guess;
        this.exact = exact;
        this.partial = partial;
        this.time = time;
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public int getExact() {
        return exact;
    }

    public void setExact(int exact) {
        this.exact = exact;
    }

    public int getPartial() {
        return partial;
    }

    public void setPartial(int partial) {
        this.partial = partial;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
