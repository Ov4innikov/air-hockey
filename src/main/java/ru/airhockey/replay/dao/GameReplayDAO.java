package ru.airhockey.replay.dao;

import ru.airhockey.replay.entity.GameReplay;

/**
 * Даошка для повторов игр
 * @author folkland
 */
public interface GameReplayDAO {

    void insertGame(String gameId, String gameText);
    GameReplay getGameById(String gameId);
    void clearTable();
}
