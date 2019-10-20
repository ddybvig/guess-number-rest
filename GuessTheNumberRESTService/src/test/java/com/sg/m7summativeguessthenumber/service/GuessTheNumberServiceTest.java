/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.service;

import com.sg.m7summativeguessthenumber.data.GuessTheNumberDaoException;
import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.Guess;
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
public class GuessTheNumberServiceTest {
    
    @Autowired
    GuessTheNumberService service;
    
    public GuessTheNumberServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startGame method, of class GuessTheNumberService.
     */
    @Test
    public void testStartGame() throws GuessTheNumberDaoException {
        //check to make sure it makes no repeated digits.
        Game game = new Game();
        game = service.startGame(game);
        String answer = game.getAnswer();
        for (int i = 0; i < answer.length(); i++){
            for (int j = i + 1; j < answer.length() - 1; j++) {
                assertTrue(answer.charAt(i)!=answer.charAt(j));
            }
        }
    }

    /**
     * Test of makeGuess method, of class GuessTheNumberService.
     */
    @Test
    public void testMakeGuess() throws GuessTheNumberDaoException, InvalidDataException {
        //check the bad guesses (length not 4, empty, or blank)
        Game g = new Game();
        Guess badGuess = new Guess();
        badGuess.setGuess("1");
        try {
        service.makeGuess(badGuess, g);
        } catch(InvalidDataException ex){
            //expected result
        }
        Guess blankGuess = new Guess();
        blankGuess.setGuess("    ");
        try{
            service.makeGuess(blankGuess, g);
        } catch(InvalidDataException ex){
            //expected
        }
        Guess emptyGuess = new Guess();
        emptyGuess.setGuess("");
        try {
            service.makeGuess(emptyGuess, g);
        } catch(InvalidDataException ex){
            //expected
        }
        //check an expected number of exact and partial
        g = service.startGame(g);
        g.setAnswer("1234");
        Guess testGuess = new Guess();
        testGuess.setGuess("6784");
        testGuess.setGame(g);
        testGuess = service.makeGuess(testGuess, g);
        String resultToCheck = testGuess.getResult();
        assertEquals("e:1p:0", resultToCheck);
        //check that inProgress set to false if they get all the exact matches
        testGuess.setGuess("1234");
        testGuess = service.makeGuess(testGuess, g);
        assertFalse(g.isInProgress());
    }

    /**
     * Test of getGame method, of class GuessTheNumberService.
     */
    @Test
    public void testGetGame() throws GuessTheNumberDaoException, InvalidDataException {
        //good path
        Game game = new Game();
        game = service.startGame(game);
        Game testGame = service.getGame(game.getId());
        assertNotNull(testGame);
        //check it throws the exception if game if i pass in a bogus gameID
        try {
        Game g = service.getGame(99);
        } catch(InvalidDataException ex){
            //expected
        }
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberService.
     */
    @Test
    public void testGetAllGames() throws GuessTheNumberDaoException {
        //check to make sure the right stuff is hidden
        Game game1 = new Game();
        game1 = service.startGame(game1);
        Game game2 = new Game();
        game2 = service.startGame(game2);
        List<Game> games = service.getAllGames();
        for (Game g : games) {
            if (g.isInProgress()==true) {
                assertEquals("HIDDEN", g.getAnswer());
            }
        }
    }

    /**
     * Test of getGuess method, of class GuessTheNumberService.
     */
    @Test
    public void testGetGuess() {
        //pass through
    }

    /**
     * Test of getAllGuesses method, of class GuessTheNumberService.
     */
    @Test
    public void testGetAllGuesses() {
        //pass through??
    }
    
}
