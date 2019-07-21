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
    private static final long STARTIG_TIME = 3;

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
        puck = new Puck(new PuckSpeed(10, 10), 100, 200);
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
            task = new GameTask(player1, player2, puck);
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
            task = new GameTask(player1, player2, puck);
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
                logger.info("New playStatus = PUCK!");
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

    }

    @Override
    public void stop() {
        playStatus = PlayStatus.BREAK;
        task.stopGame();
    }
}
