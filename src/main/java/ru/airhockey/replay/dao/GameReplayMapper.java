package ru.airhockey.replay.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.airhockey.replay.entity.GameReplay;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Парсит приходящий от базы данных ответ
 *
 * @author folkland
 */
public class GameReplayMapper implements RowMapper<GameReplay> {

    @Override
    public GameReplay mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String gameId = resultSet.getString("game_id");
        String gameText = resultSet.getString("game_text");
        return new GameReplay(id, gameId, gameText);
    }
}
