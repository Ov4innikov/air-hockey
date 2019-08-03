package ru.airhockey.statistics.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.airhockey.statistics.entity.GameHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author folkland
 */
public class GameHistoryMapper implements RowMapper<GameHistory> {

    @Override
    public GameHistory mapRow(ResultSet resultSet, int i) throws SQLException {
        GameHistory history = new GameHistory(resultSet.getInt("id"), resultSet.getString("id_game"), resultSet.getInt("id_user"));
        return history;
    }
}
