/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

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

public class GameDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    GuessDao guessDao;
    
    public GameDatabaseDaoTest() {
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
     * Test of addGame method, of class GameDatabaseDao.
     */
    @Test
    public void testAddGetGame() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        Game fromDao = gameDao.getGame(game.getId());
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDatabaseDao.
     */
    @Test
    public void testGetAllGames() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        gameDao.addGame(game);
        Game game2 = new Game();
        game2.setAnswer("1111");
        game2.setInProgress(true);
        gameDao.addGame(game2);
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDatabaseDao.
     */
    @Test
    public void testUpdateGame() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        Game fromDao = gameDao.getGame(game.getId());
        
        assertEquals(game, fromDao);
        
        game.setAnswer("1111");
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        fromDao = gameDao.getGame(game.getId());
        assertEquals(game, fromDao);
        
    }

    /**
     * Test of deleteGame method, of class GameDatabaseDao.
     */
    @Test
    public void testDeleteGame() throws GuessTheNumberDaoException {
        Game game = new Game();
        game.setAnswer("0000");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        gameDao.deleteGame(game.getId());
        Game fromDao = gameDao.getGame(game.getId());
        assertNull(fromDao);
    }
    
}
