package ru.airhockey.playingarea;

import ru.airhockey.playingarea.model.GameResult;
import ru.airhockey.playingarea.model.PlayState;
import ru.airhockey.playingarea.model.PlayerMove;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Интерфейс для игры
 *
 * @author Овчинников
 */
public interface Play extends Callable<GameResult> {

    /**
     * Метод для запуска игры.
     * @return GameResult - победитель
     * @throws Exception
     */
    public GameResult call() throws Exception;

    /**
     * Метод для получения слепка игрового состояния.
     * @return PlatState
     */
    public PlayState getPlayState();

    /**
     * Метод для обработки движения игроков.
     * @param playerMove
     */
    public void handlePlayerMove(PlayerMove playerMove);

    /**
     * Метод для остановки игры. Может применяться в случае, если соединения с одним из игроков разорвалось.
     */
    public void stop();
}
