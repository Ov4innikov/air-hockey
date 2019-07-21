package ru.airhockey.playingarea;

import ru.airhockey.playingarea.model.GameResult;
import ru.airhockey.playingarea.model.PlayState;
import ru.airhockey.playingarea.model.PlayerMove;

import java.util.concurrent.Callable;

/**
 * Интерфейс для игры
 *
 * @author Овчинников
 */
public interface Play extends Callable<GameResult> {

    public GameResult call() throws Exception;
    public PlayState getPlayState();
    public void handlePlayerMove(PlayerMove playerMove);
    public void stop();
}
