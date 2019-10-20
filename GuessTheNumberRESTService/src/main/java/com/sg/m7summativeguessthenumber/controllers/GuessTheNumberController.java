/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.controllers;

import com.sg.m7summativeguessthenumber.data.GuessTheNumberDaoException;
import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.GameGuess;
import com.sg.m7summativeguessthenumber.models.Guess;
import com.sg.m7summativeguessthenumber.service.GuessTheNumberService;
import com.sg.m7summativeguessthenumber.service.InvalidDataException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daler
 */
@RestController
public class GuessTheNumberController {
    
    @Autowired
    GuessTheNumberService service;
    
    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game startGame()  throws GuessTheNumberDaoException {
        Game g = new Game();
        g = service.startGame(g);
        g.setAnswer("HIDDEN");
        return g;
    }
    
    @PostMapping("guess")
    public Guess makeGuess(@RequestBody GameGuess gameGuess)  throws GuessTheNumberDaoException, InvalidDataException {
        Guess guess = new Guess();
        if (gameGuess.getUserGuess()==null){
            throw new InvalidDataException("Guess cannot be empty.");
        }
        guess.setGuess(gameGuess.getUserGuess());
        Game game = service.getGame(gameGuess.getGameId());
        guess.setGame(game);
        Guess checkedGuess = service.makeGuess(guess, game);
        if (checkedGuess.getGame().isInProgress()==true) {
            checkedGuess.getGame().setAnswer("HIDDEN");
        }
        return checkedGuess;
    }
    
    @GetMapping("game")
    public List<Game> getAllGames()  throws GuessTheNumberDaoException {
        return service.getAllGames();
    }
    
    @GetMapping("game/{gameId}")
    public Game getGame(@PathVariable int gameId)  throws GuessTheNumberDaoException, InvalidDataException {
        Game g = service.getGame(gameId);
        if (g.isInProgress()==true) {
            g.setAnswer("HIDDEN");
        }
        return g;
    }
    
    @GetMapping("rounds/{gameId}")
    public List<Guess> getRounds(@PathVariable int gameId)  throws GuessTheNumberDaoException, InvalidDataException {
        List<Guess> guesses = service.getAllGuesses(gameId);
        for (Guess g : guesses) {
            if (g.getGame().isInProgress()==true) {
                g.getGame().setAnswer("HIDDEN");
            }
        }
        return guesses;
    }
    
}
