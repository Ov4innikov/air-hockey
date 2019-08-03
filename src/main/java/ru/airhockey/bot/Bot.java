package ru.airhockey.bot;

import ru.airhockey.playingarea.model.PlayStatus;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.service.ClientMessage;
import ru.airhockey.service.Game;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.LockSupport;

/**
 * Класс бота, запускается в отдельном потоке
 * @author folkland
 */
public class Bot implements Callable<String> {

    private Game game;
    private BotLevel botLevel;
    private String gameId;
    private boolean breakBotPlaying;

    public Bot(String gameId, Game game, BotLevel botLevel) {
        this.game = game;
        this.botLevel = botLevel;
        this.gameId = gameId;
        breakBotPlaying = false;
    }

    @Override
    public String call() {
        LockSupport.parkNanos(10_000 * 1_000_000);
        Level level = null;
        switch (botLevel) {
            case MIDDLE: level = new RadiusBot(gameId); break;
            default: level = new RadiusBot(gameId); break;
        }
        while (game.getPlayStatus() != PlayStatus.BREAK && !breakBotPlaying) {
            game.setPlayerPosition(level.calculateMoving(game.getPuckPosition(), game.getPlayer2()));
            LockSupport.parkNanos(Puck.WAIT_TIME * 1_000_000);
        }
        return "ok";
    }

    /**
     * Насильное завершение игры
     */
    public void endGameForce() {
        breakBotPlaying = true;
    }
}
