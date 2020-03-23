/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.controllers;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Guess;
import com.mycompany.bullsandcows.models.GuessResult;
import com.mycompany.bullsandcows.models.Round;
import com.mycompany.bullsandcows.service.GameService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jeromepullenjr
 */
@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public String start() {
        return service.start();
    }

    @PostMapping("/guess")
    public GuessResult guess(@RequestBody Guess g) {
        return service.makeGuess(g);
    }

    @GetMapping("/game")
    public List<Game> all() {
        return service.getAllGames();
    }

//     @GetMapping("/game") //DONE
//    @ResponseStatus(HttpStatus.OK)
//    public List<Game> getAllGames() {
//        List<Game> allGames = service.getAllGames();
//        for (Game g : allGames) {
//            if (!g.isIsFinished()) {
//                g.setAnswer("****");
//            }
//        }
//        return allGames;
//    }
    @GetMapping("/game/{gameId}")
    public Game game(@PathVariable int gameId) {
        return service.findById(gameId);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> rounds(@PathVariable int gameId) {
        return service.getRoundsById(gameId);
    }

}
