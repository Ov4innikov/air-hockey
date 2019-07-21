package ru.airhockey.playingarea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.direct.PlayDirect;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.playingarea.util.PhysicsUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.LockSupport;

/**
 * Класс в котором реализуется логика забиваний шайбы, ударений об границы, ударений об игрока.
 *
 * @author Овчинников
 */
public class GameTask implements Callable<GameResult> {

    public static final int HEIGHT_OF_PLAYING_AREA = 400;
    public static final int WIDTH_OF_PLAYING_AREA = 350;
    public static final int WIDTH_OF_GOAL = 0;

    private static final Logger logger = LoggerFactory.getLogger(GameTask.class);
    private static final PlayDirect playDirect = PlayDirect.getInstance();

    private Player player1;
    private Player player2;
    private Puck puck;
    private volatile PlayStatus playStatus = PlayStatus.STARTING;

    public GameTask(Player player1, Player player2, Puck puck) {
        this.player1 = player1;
        this.player2 = player2;
        this.puck = puck;
    }

    @Override
    public GameResult call() throws Exception {
        //тестовое условие победы, после i=100, считается, что player1 выйграл
        int i = 0;
        logger.info("Game task running!");
        playStatus = PlayStatus.PLAYING;
        while (playStatus == PlayStatus.PLAYING) {
            checkCrash();
            if (playStatus == PlayStatus.PUCK) {
                logger.info("Wait end of PUCK status...");
                while (playStatus == PlayStatus.PUCK) {
                    LockSupport.parkNanos(1_000_000);
                }

            }
            puck.waitNextIteration();
            logger.info("x = {}; y = {}; ", puck.getX(), puck.getY());
            logger.info("player1 x = {}; player1 y = {}; player1 position = {}", player1.getX(), player1.getY(), player1.getPlayerPosition());
            logger.info("player2 x = {}; player2 y = {}; player2 position = {}", player2.getX(), player2.getY(), player2.getPlayerPosition());
        }
        //Проверка на очки
        logger.info("Game task stopping!");
        return new GameResult(player1);
    }

    private void checkCrash() {
        float xDifferenceWithLeftSide = puck.getX() - puck.RADIUS + puck.getSpeed().getX();
        float xDifferenceWithRightSide = WIDTH_OF_PLAYING_AREA - (puck.getX() + puck.RADIUS + puck.getSpeed().getX());
        float yDifferenceWithBottomSide= puck.getY() - puck.RADIUS + puck.getSpeed().getY();
        float yDifferenceWithTopSide = HEIGHT_OF_PLAYING_AREA - (puck.getY() + puck.RADIUS + puck.getSpeed().getY());
        boolean crashWallsRightLeft = true, crashWallsTopBottom = true;
        if ((xDifferenceWithLeftSide < 0) && (puck.getSpeed().getX() < 0)) {
            puck.setX(Math.abs(xDifferenceWithLeftSide) + puck.RADIUS);
            puck.getSpeed().turnX();
        } else if ((xDifferenceWithRightSide < 0) && (puck.getSpeed().getX() > 0)) {
            puck.setX(WIDTH_OF_PLAYING_AREA - Math.abs(xDifferenceWithRightSide) - puck.RADIUS);
            puck.getSpeed().turnX();
        } else {
            crashWallsRightLeft = false;
        }
        if ((yDifferenceWithTopSide < 0) && (puck.getSpeed().getY() > 0)) {
            if (!checkPuckIntoGoal()) {
                puck.setY(HEIGHT_OF_PLAYING_AREA - Math.abs(yDifferenceWithTopSide) - puck.RADIUS);
                puck.getSpeed().turnY();
            }
        } else if ((yDifferenceWithBottomSide < 0) && (puck.getSpeed().getY() < 0)) {
            if (!checkPuckIntoGoal()) {
                puck.setY(Math.abs(yDifferenceWithBottomSide) + puck.RADIUS);
                puck.getSpeed().turnY();
            }
        } else {
            crashWallsTopBottom = false;
        }
        if(!checkCrashIntoPlayers()) {
            if (!crashWallsRightLeft) {
                puck.setX(puck.getX() + puck.getSpeed().getX());
            }
            if (!crashWallsTopBottom) {
                puck.setY(puck.getY() + puck.getSpeed().getY());
            }
        }

    }

    private boolean checkPuckIntoGoal() {
        if ((puck.getX() + puck.getSpeed().getX() > WIDTH_OF_PLAYING_AREA/2 - WIDTH_OF_GOAL/2) && (puck.getX() + puck.getSpeed().getX() < WIDTH_OF_PLAYING_AREA/2 + WIDTH_OF_GOAL/2)) {
            playStatus = PlayStatus.PUCK;
            if (puck.getY() > HEIGHT_OF_PLAYING_AREA/2) {
                if (player1.getPlayerPosition() == PlayerPosition.UP) {
                    player1.setPlayAccount(player1.getPlayAccount() + 1);
                    playDirect.setUpPlayerPosition(player1);
                    playDirect.setDownPlayerPosition(player2);
                    playDirect.setUpPuckPosition(puck);
                } else {
                    player2.setPlayAccount(player2.getPlayAccount() + 1);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setUpPuckPosition(puck);
                }
            } else {
                if (player1.getPlayerPosition() == PlayerPosition.DOWN) {
                    player1.setPlayAccount(player1.getPlayAccount() + 1);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setDownPuckPosition(puck);
                } else {
                    player2.setPlayAccount(player2.getPlayAccount() + 1);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setDownPuckPosition(puck);
                }
            }
            logger.info("Puck!");
            return true;
        }
        return false;
    }

    private boolean checkCrashIntoPlayers() {
        float distanceBetweenPuckAndPlayer1 = (int) Math.sqrt((puck.getX() + puck.getSpeed().getX() - player1.getX()) * (puck.getX() + puck.getSpeed().getX() - player1.getX()) + (puck.getY() + puck.getSpeed().getY() - player1.getY()) * (puck.getY() + puck.getSpeed().getY() - player1.getY()));
        float distanceBetweenPuckAndPlayer2 = (int) Math.sqrt((puck.getX() + puck.getSpeed().getX() - player2.getX()) * (puck.getX() + puck.getSpeed().getX() - player2.getX()) + (puck.getY() + puck.getSpeed().getY() - player2.getY()) * (puck.getY() + puck.getSpeed().getY() - player2.getY()));
        if (distanceBetweenPuckAndPlayer1 < (puck.RADIUS + player1.RADIUS)) {
            logger.info("Crash into player1!");
            PhysicsUtil.calclateCrashResult(puck, player1);
            return true;
        } else if ((distanceBetweenPuckAndPlayer2 < (puck.RADIUS + player2.RADIUS))) {
            logger.info("Crash into player2!");
            PhysicsUtil.calclateCrashResult(puck, player2);
            return true;
        }
        return false;
    }


    public void stopGame() {
        playStatus = PlayStatus.STOPPING;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }
}
