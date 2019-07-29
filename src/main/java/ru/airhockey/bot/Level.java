package ru.airhockey.bot;

import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.service.ClientMessage;

/**
 * Интерфейс для описания любого бота
 * Сделан для простоты написания нового уровня сложности и легкого внедрения
 * @author folkland
 */
public interface Level {

    ClientMessage calculateMoving(Puck puck, Player player);
}
