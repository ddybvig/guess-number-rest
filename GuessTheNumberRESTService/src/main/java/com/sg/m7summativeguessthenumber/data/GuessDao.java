/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.Guess;
import java.util.List;

/**
 *
 * @author daler
 */
public interface GuessDao {
    Guess addGuess(Guess guess) throws GuessTheNumberDaoException;
    Guess getGuess(int id) throws GuessTheNumberDaoException;
    List<Guess> getAllGuessesForGame(Game game) throws GuessTheNumberDaoException;

}
