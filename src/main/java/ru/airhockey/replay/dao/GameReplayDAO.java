package ru.airhockey.replay.dao;

import ru.airhockey.replay.entity.GameReplay;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Даошка для повторов игр
 * @author folkland
 */
public interface GameReplayDAO {

    void insertGame(String gameId, String gameText);
    GameReplay getGameByGameId(String gameId);
    GameReplay getGameById(int id);
    void clearTable();
    List<GameReplay> getGames();
}
