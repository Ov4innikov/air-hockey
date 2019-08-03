package ru.airhockey.service;

import ru.airhockey.bot.BotLevel;

/**
 * Интерфейс для бот менеджера
 * TODO очень похож на интерфейс менеджер, возможно стоит соединить
 * @author folkland
 */
public interface IBotManager {

    void createGame(String gameId, BotLevel botLevel, int user);
    void startGame(String gameId);
    void endGame(String gameId);

}
