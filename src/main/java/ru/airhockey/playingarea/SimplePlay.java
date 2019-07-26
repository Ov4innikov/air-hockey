package ru.airhockey.playingarea;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.direct.PlayDirect;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.playingarea.util.PhysicsUtil;
import ru.airhockey.web.ws.sender.ISender;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

/**
 * Класс реализующий игру, в котором предусмотренно управление периодами и определение победителей.
 *
 * @author Овчинников
 */

public class SimplePlay implements Play {

    private static final Logger logger = LoggerFactory.getLogger(SimplePlay.class);
    private static final PlayDirect playDirect = PlayDirect.getInstance();
    private static final long STARTIG_TIME = 3;

    @Getter
    @Setter
    private Player player1;
    @Getter
    @Setter
    private Player player2;
    @Getter
    @Setter
    private Puck puck;
    private Round round = Round.FIRST;
    @Getter
    @Setter
    private PlayStatus playStatus = PlayStatus.STARTING;
    private Future firstPeriod, secondPeriod;
    private GameResult result = null, firstPeriodResult, secondPeriodResult;
    private GameTask task;

    private ISender sender;
    private String gameId;

    private final ExecutorService executorService;

    public SimplePlay(ExecutorService executorService, Player player1, Player player2, ISender sender, String gameId) {
        //TODO: logger
        logger.info("Starting SimplePlay");
        this.executorService = executorService;
        puck = new Puck(new Speed(10, 10), 100, 200);
        this.player1 = player1;
        this.player2 = player2;

        this.sender = sender;
        this.gameId = gameId;
    }

    public SimplePlay(ExecutorService executorService, Player player1, Player player2) {
        //TODO: logger
        logger.info("Starting SimplePlay");
        this.executorService = executorService;
        puck = new Puck(new Speed(10, 10), 100, 200);
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public GameResult call() throws Exception {
        play();
        return result;
    }

    public void play() throws ExecutionException, InterruptedException {
        logger.info("STARTING 1");
        if (playStatus != PlayStatus.BREAK) {
            LockSupport.parkNanos(STARTIG_TIME * 1_000_000_000);
            playDirect.setUpPuckPosition(puck);
            playDirect.setDownPlayerPosition(player1);
            playDirect.setUpPlayerPosition(player2);
            task = new GameTask(player1, player2, puck, sender, gameId);
            playStatus = PlayStatus.PLAYING;
            firstPeriod = executorService.submit(task);
        }
        firstPeriodResult = (GameResult) firstPeriod.get();

        logger.info("STARTING 2");
        if (playStatus != PlayStatus.BREAK) {
            playStatus = PlayStatus.STARTING;
            LockSupport.parkNanos(STARTIG_TIME * 1_000_000_000);
            playDirect.setDownPuckPosition(puck);
            playDirect.setDownPlayerPosition(player1);
            playDirect.setUpPlayerPosition(player2);
            task = new GameTask(player1, player2, puck, sender, gameId);
            playStatus = PlayStatus.PLAYING;
            secondPeriod = executorService.submit(task);
        }
        secondPeriodResult = (GameResult) secondPeriod.get();
        if ((firstPeriodResult.getWinner() == player1) && (secondPeriodResult.getWinner() == player1)) result = new GameResult(player1);
        if ((firstPeriodResult.getWinner() == player2) && (secondPeriodResult.getWinner() == player2)) result = new GameResult(player2);
    }

    @Override
    public PlayState getPlayState() {
        PlayState playState = new PlayState();
        PlayStatus newPlayStatus;
        if (playStatus == PlayStatus.PLAYING) {
            newPlayStatus = task.getPlayStatus();
            if (newPlayStatus == PlayStatus.PUCK) {
                logger.trace("New playStatus = PUCK!");
                task.setPlayStatus(PlayStatus.PLAYING);
            }
        } else {
            newPlayStatus = playStatus;
        }
        playState.setPlayStatus(newPlayStatus);
        playState.setPlayer1(player1);
        playState.setPlayer2(player2);
        playState.setPuck(puck);
        return playState;
    }

    @Override
    public void handlePlayerMove(PlayerMove playerMove) {
        if (task != null) {
            if (playerMove.getPlayer() == player1) {
                task.updatePlayer1Move(playerMove);
            } else if (playerMove.getPlayer() == player2) {
                task.updatePlayer2Move(playerMove);
            }
        }
    }

    @Override
    public void stop() {
        playStatus = PlayStatus.BREAK;
        task.stopGame();
    }
}
