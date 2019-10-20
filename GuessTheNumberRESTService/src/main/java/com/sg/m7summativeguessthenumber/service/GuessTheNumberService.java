/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.service;

import com.sg.m7summativeguessthenumber.data.GameDao;
import com.sg.m7summativeguessthenumber.data.GuessDao;
import com.sg.m7summativeguessthenumber.data.GuessTheNumberDaoException;
import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.Guess;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daler
 */
@Service
public class GuessTheNumberService {

    @Autowired
    GameDao gameDao;

    @Autowired
    GuessDao guessDao;

    public Game startGame(Game game) throws GuessTheNumberDaoException {
        Random r = new Random();
        int firstDigit = 0;
        int secondDigit = 0;
        int thirdDigit = 0;
        int fourthDigit = 0;
        firstDigit = r.nextInt(10);
        do {
            secondDigit = r.nextInt(10);
        } while (secondDigit == firstDigit);
        do {
            thirdDigit = r.nextInt(10);
        } while (thirdDigit == firstDigit || thirdDigit == secondDigit);
        do {
            fourthDigit = r.nextInt(10);
        } while (fourthDigit == firstDigit || fourthDigit == secondDigit || fourthDigit == thirdDigit);
        String firstDigitString = Integer.toString(firstDigit);
        String secondDigitString = Integer.toString(secondDigit);
        String thirdDigitString = Integer.toString(thirdDigit);
        String fourthDigitString = Integer.toString(fourthDigit);
        game.setAnswer(firstDigitString+secondDigitString+thirdDigitString+fourthDigitString);
        game.setInProgress(true);
        return gameDao.addGame(game);
    }
    
    public Guess makeGuess(Guess guess, Game game) throws GuessTheNumberDaoException, InvalidDataException {
        guess.setTimeOfGuess(LocalDateTime.now().withNano(0)); 
        String answer = game.getAnswer();
        String userGuess = guess.getGuess();
        if (userGuess.length()!=4||userGuess.isBlank()||userGuess.isEmpty()) {
            throw new InvalidDataException("Invalid guess. Please enter exactly 4 numbers with no spaces or other characters.");
        }
        int exactMatches = 0;
        int partialMatches = 0;
        //the way this is written right now, if you guess "4444" and the answer has a 4 in it, you'll get 1 exact and 3 partial, which 
        //seems sort of correct but sort of not depending on how strictly you interpret the rules...
        for (int i = 0; i < 4; i++) {
            if (userGuess.charAt(i)==answer.charAt(i)) {
                exactMatches++;
            }
            for (int j = 0; j < 4; j++) {
                if (userGuess.charAt(i)==answer.charAt(j) && i != j) {
                    partialMatches++;
                }
            }
        }
        guess.setResult("e:"+Integer.toString(exactMatches)+"p:"+Integer.toString(partialMatches));
        if (exactMatches==4) {
            guess.setResult("WINNER!"); 
            game.setInProgress(false);
            gameDao.updateGame(game);
        }
        return guessDao.addGuess(guess);
    }

    public Game getGame(int id) throws GuessTheNumberDaoException, InvalidDataException {
        Game g = gameDao.getGame(id);
        if (g == null) {
            throw new InvalidDataException("Game not found. Please try a different game ID.");
        }
        return g;
    }

    public List<Game> getAllGames() throws GuessTheNumberDaoException {
        List<Game> games = gameDao.getAllGames();
        String hideAnswer = "HIDDEN";
        for (Game g : games) {
            if (g.isInProgress()==true) {
                g.setAnswer(hideAnswer);
            }
        }
        return games;
    }

    public Guess getGuess(int id) throws GuessTheNumberDaoException, InvalidDataException {
        return guessDao.getGuess(id);
    }

    public List<Guess> getAllGuesses(int gameId) throws GuessTheNumberDaoException, InvalidDataException {
        Game game = gameDao.getGame(gameId);
        return guessDao.getAllGuessesForGame(game);
    }
}
