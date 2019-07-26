package ru.airhockey.service;

import ru.airhockey.web.ws.model.IMessage;

public interface IManager {
    void createGame(String gameId);

    void startGame(String gameId);

    void endGame(String gameId);

    void setPlayerPosition(IMessage message);
}
