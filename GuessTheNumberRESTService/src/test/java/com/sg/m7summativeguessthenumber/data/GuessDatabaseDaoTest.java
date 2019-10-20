/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.Guess;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daler
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class GuessDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    GuessDao guessDao;
    
    public GuessDatabaseDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws GuessTheNumberDaoException {
        List<Game> games = gameDao.getAllGames();
        //this should delete all games in the test database as well as the guesses associated with them
        for (Game g : games) {
            gameDao.deleteGame(g.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addGuess method, of class GuessDatabaseDao.
     */
    @Test
    public void testAddGetGuess() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        gameDao.addGame(game);
        Guess guess = new Guess();
        guess.setGame(game);
        guess.setGuess("1111");
        guess.setResult("test");
        guess.setTimeOfGuess(LocalDateTime.now().withNano(0));
        guess = guessDao.addGuess(guess);
        Guess fromDao = guessDao.getGuess(guess.getId());
        fromDao.setGame(game);
        assertEquals(guess, fromDao);
    }

    /**
     * Test of getAllGuesses method, of class GuessDatabaseDao.
     */
    @Test
    public void testGetAllGuesses() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        gameDao.addGame(game);
        Guess guess = new Guess();
        guess.setGame(game);
        guess.setGuess("1111");
        guess.setResult("test");
        guess.setTimeOfGuess(LocalDateTime.now().withNano(0));
        guessDao.addGuess(guess);
        Guess guess2 = new Guess();
        guess2.setGame(game);
        guess2.setGuess("2222");
        guess2.setResult("test2");
        guess2.setTimeOfGuess(LocalDateTime.now().withNano(0));
        guessDao.addGuess(guess2);
        List<Guess> guesses = guessDao.getAllGuessesForGame(game);
        assertEquals(2, guesses.size());
        assertTrue(guesses.contains(guess));
        assertTrue(guesses.contains(guess2));
    }
    
}
