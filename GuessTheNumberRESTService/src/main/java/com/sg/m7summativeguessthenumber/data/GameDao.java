/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

import com.sg.m7summativeguessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author daler
 */
public interface GameDao {
    Game addGame(Game game) throws GuessTheNumberDaoException;
    Game getGame(int id) throws GuessTheNumberDaoException;
    List<Game> getAllGames() throws GuessTheNumberDaoException;
    void updateGame(Game game) throws GuessTheNumberDaoException;
    void deleteGame(int id) throws GuessTheNumberDaoException;
}
