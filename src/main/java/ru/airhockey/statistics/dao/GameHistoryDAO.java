package ru.airhockey.statistics.dao;

import ru.airhockey.statistics.entity.GameHistory;

import java.util.List;

/**
 * @author folkland
 */
public interface GameHistoryDAO {

    void insertGame(String idGame, int idUser);
    List<GameHistory> getGamesByIdUser(int idUser);
}
