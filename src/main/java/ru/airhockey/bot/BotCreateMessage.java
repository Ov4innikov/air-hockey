package ru.airhockey.bot;

import ru.airhockey.web.ws.model.IMessage;

/**
 * Сообщение, которое должно приходить при создании игры с ботом
 * @author folkland
 */
public class BotCreateMessage implements IMessage {

    private String gameId;
    private BotLevel botLevel;

    public BotCreateMessage(String gameId, BotLevel botLevel) {
        this.gameId = gameId;
        this.botLevel = botLevel;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public BotLevel getBotLevel() {
        return botLevel;
    }

    public void setBotLevel(BotLevel botLevel) {
        this.botLevel = botLevel;
    }
}
