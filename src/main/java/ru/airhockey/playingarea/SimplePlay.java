package ru.airhockey.playingarea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.direct.PlayDirect;
import ru.airhockey.playingarea.model.*;

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
    private static final long STARTING_TIME = 3;
    private static final long PERIOD_TIME = 180;

    private Player player1;
    private Player player2;
    private Puck puck;
    private Round round = Round.FIRST;
    private PlayStatus playStatus = PlayStatus.STARTING;
    private Future firstPeriod, secondPeriod;
    private GameResult result = null, firstPeriodResult, secondPeriodResult;
    private GameTask task;

    private final ExecutorService executorService;

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
        logger.info("The play is stoped!");
        return result;
    }

    public void play() throws ExecutionException, InterruptedException {
        logger.info("STARTING");
        playDirect.setUpPuckPosition(puck);
        playDirect.setDownPlayerPosition(player1);
        playDirect.setUpPlayerPosition(player2);
        if (playStatus != PlayStatus.BREAK) {
            LockSupport.parkNanos(STARTING_TIME * 1_000_000_000);
            task = new GameTask(player1, player2, puck);
            playStatus = PlayStatus.PLAYING;
            firstPeriod = executorService.submit(task);
        }
        LockSupport.parkNanos(PERIOD_TIME * 1_000_000_000);
        playStatus = PlayStatus.RESULTING;
        stop();
        firstPeriodResult = (GameResult) firstPeriod.get();
        logger.info("Winner = {}", firstPeriodResult.getWinner().getPlayerPosition());
        result = firstPeriodResult;
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
        if (playStatus != PlayStatus.PLAYING) return;
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
