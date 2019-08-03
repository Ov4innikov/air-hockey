package ru.airhockey.bot;

import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerMove;
import ru.airhockey.playingarea.model.Puck;

/**
 * Интерфейс для описания любого бота
 * Сделан для простоты написания нового уровня сложности и легкого внедрения
 * @author folkland
 */
public interface Level {

    PlayerMove calculateMoving(Puck puck, Player player);
}
