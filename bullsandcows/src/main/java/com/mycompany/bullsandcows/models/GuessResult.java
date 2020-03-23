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
public class GuessResult {

    private GuessStatus status = GuessStatus.NotFound;
    private int partialGuess;
    private int exactGuess;

    public int getPartialGuess() {
        return partialGuess;
    }

    public void setPartialGuess(int partialGuess) {
        this.partialGuess = partialGuess;
    }

    public int getExactGuess() {
        return exactGuess;
    }

    public void setExactGuess(int exactGuess) {
        this.exactGuess = exactGuess;
    }

    public GuessStatus getStatus() {
        return status;
    }

    public void setStatus(GuessStatus status) {
        this.status = status;
    }
}
