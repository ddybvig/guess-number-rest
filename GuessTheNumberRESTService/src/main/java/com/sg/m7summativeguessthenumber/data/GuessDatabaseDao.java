/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

import com.sg.m7summativeguessthenumber.data.GameDatabaseDao.GameMapper;
import com.sg.m7summativeguessthenumber.models.Game;
import com.sg.m7summativeguessthenumber.models.Guess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daler
 */
@Repository
public class GuessDatabaseDao implements GuessDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Guess addGuess(Guess guess)  throws GuessTheNumberDaoException {
        final String INSERT_GUESS = "INSERT INTO guess(guess, timeOfGuess, result, gameID) VALUES(?,?,?,?)";
        jdbc.update(INSERT_GUESS,
                guess.getGuess(),
                guess.getTimeOfGuess(),
                guess.getResult(),
                guess.getGame().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        guess.setId(newId);
        return guess;
    }

    @Override
    public Guess getGuess(int id)  throws GuessTheNumberDaoException {
        try {
            Guess g = jdbc.queryForObject("SELECT * FROM guess WHERE id = ?", new GuessMapper(), id);
            return g;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Guess> getAllGuessesForGame(Game game)  throws GuessTheNumberDaoException {
        List<Guess> guesses = jdbc.query("SELECT * FROM guess gu where gu.gameId = ? ORDER BY gu.timeOfGuess", new GuessMapper(), game.getId());
        for (Guess g : guesses) { 
            g.setGame(game);
        }
        return guesses;
    }
    
    //NOT USED LOL
    private Game getGameForGuess(Guess guess)  throws GuessTheNumberDaoException {
        final String SELECT_GAME_FOR_GUESS = "SELECT g.* FROM game g "
                + "JOIN guess gu ON g.id = gu.gameId WHERE gu.gameId = ?";
        return jdbc.queryForObject(SELECT_GAME_FOR_GUESS, new GameMapper(),
                guess.getGame().getId());
    }

    private static final class GuessMapper implements RowMapper<Guess> {

        @Override
        public Guess mapRow(ResultSet rs, int i) throws SQLException {
            Guess g = new Guess();
            g.setId(rs.getInt("id"));
            g.setGuess(rs.getString("guess"));
            g.setTimeOfGuess(rs.getTimestamp("timeOfGuess").toLocalDateTime());
            g.setResult(rs.getString("result"));
            return g;
        }
    }

}
