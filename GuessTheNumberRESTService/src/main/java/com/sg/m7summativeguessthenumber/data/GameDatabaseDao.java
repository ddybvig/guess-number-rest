/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.data;

import com.sg.m7summativeguessthenumber.models.Game;
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
public class GameDatabaseDao implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Game addGame(Game game) throws GuessTheNumberDaoException {
        final String INSERT_GAME = "INSERT INTO game(answer, inProgress) VALUES(?,?)";
        jdbc.update(INSERT_GAME,
                game.getAnswer(),
                game.isInProgress());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(newId);
        return game;
    }

    @Override
    public Game getGame(int id)  throws GuessTheNumberDaoException {
        try {
            Game g = jdbc.queryForObject("SELECT * FROM game WHERE id = ?", new GameMapper(), id);
            return g;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Game> getAllGames()  throws GuessTheNumberDaoException {
        List<Game> games = jdbc.query("SELECT * FROM game", new GameMapper());
        return games;
    }

    @Override
    public void updateGame(Game game)  throws GuessTheNumberDaoException {
        jdbc.update("UPDATE game SET answer = ?, inProgress = ? WHERE id = ?",
                game.getAnswer(),
                game.isInProgress(),
                game.getId());
    }

    @Override
    public void deleteGame(int id)  throws GuessTheNumberDaoException {
        jdbc.update("DELETE FROM guess gu WHERE gu.gameId = ?", id);
        jdbc.update("DELETE FROM game WHERE id = ?", id);
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game g = new Game();
            g.setId(rs.getInt("id"));
            g.setAnswer(rs.getString("answer"));
            g.setInProgress(rs.getBoolean("inProgress"));
            return g;
        }

    }

}
