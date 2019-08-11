package ru.airhockey.statistics.dao;

import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.statistics.entity.GameHistory;

import java.util.List;

/**
 * @author folkland
 */
public interface GameHistoryDAO {

    void insertGame(String idGame, int idUser, int opponent, boolean is_win, PlayerPosition position);
    List<GameHistory> getGamesByIdUser(int idUser);
}
