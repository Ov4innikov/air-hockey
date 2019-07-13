package ru.airhockey.playingarea;

import ru.airhockey.playingarea.model.PlayState;
import ru.airhockey.playingarea.model.PlayerMove;

/**
 * Интерфейс для игры
 *
 * @author Овчинников
 */
public interface Play {

    public void play();
    public PlayState getPlayState();
    public void handlePlayerMove(PlayerMove playerMove);
    public void stop();
}
