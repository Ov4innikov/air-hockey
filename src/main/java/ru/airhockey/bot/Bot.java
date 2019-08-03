package ru.airhockey.bot;

import ru.airhockey.playingarea.model.PlayStatus;
import ru.airhockey.playingarea.model.PlayerMove;
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
            case MIDDLE: level = new RadiusBot(); break;
            default: level = new RadiusBot(); break;
        }
        while (game.getPlayStatus() != PlayStatus.BREAK && !breakBotPlaying) {
            PlayerMove playerMove = level.calculateMoving(game.getPuckPosition(), game.getPlayer2());
            game.setPlayerPosition(new ClientMessage(gameId, playerMove.getPlayer().getPlayerPosition(), playerMove.getPlayerMoveStatus(), playerMove.getDirection()));
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
