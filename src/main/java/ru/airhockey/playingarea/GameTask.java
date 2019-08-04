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

    public static final int HEIGHT_OF_PLAYING_AREA = 650;
    public static final int WIDTH_OF_PLAYING_AREA = 450;
    public static final int WIDTH_OF_GOAL = 200;
    public static final int DEFAULT_COUNT_OF_ITERATION_AFTER_CRASH_TO_PLAYER = 10;
    public static final int PLAYER_INTERVAL_WITH_BORDER = 10;
    public static final int PLAYER_INTERVAL_WITH_CENTER = 10;

    private static final Logger logger = LoggerFactory.getLogger(GameTask.class);
    private static final PlayDirect playDirect = PlayDirect.getInstance();

    private Player player1;
    private Player player2;
    private PlayerMove player1Move;
    private PlayerMove player2Move;
    private Puck puck;
    private volatile PlayStatus playStatus = PlayStatus.STARTING;
    private int countOfIterationAfterCrashToPlayer = 0;

    public GameTask(Player player1, Player player2, Puck puck) {
        this.player1 = player1;
        this.player2 = player2;
        this.puck = puck;
        player1Move = playDirect.getDefaultPlayerMove(player1);
        player2Move = playDirect.getDefaultPlayerMove(player2);
    }

    @Override
    public GameResult call() throws Exception {
        //тестовое условие победы, после i=100, считается, что player1 выйграл
        int i = 0;
        logger.info("Game task running!");
        playStatus = PlayStatus.PLAYING;
        while (playStatus == PlayStatus.PLAYING) {
            handlePlayersMove();
            checkCrash();
            if (playStatus == PlayStatus.PUCK) {
                logger.info("Wait end of PUCK status...");
                while (playStatus == PlayStatus.PUCK) {
                    LockSupport.parkNanos(1_000_000);
                }

            }
            puck.waitNextIteration();
            logger.trace("x = {}; y = {}; ", puck.getX(), puck.getY());
            logger.trace("player1 x = {}; player1 y = {}; player1 position = {}", player1.getX(), player1.getY(), player1.getPlayerPosition());
            logger.trace("player2 x = {}; player2 y = {}; player2 position = {}", player2.getX(), player2.getY(), player2.getPlayerPosition());
        }
        //Проверка на очки
        logger.info("Game task stopping!");
        if (player1.getPlayAccount() > player2.getPlayAccount()) {
            return new GameResult(player1);
        } else if (player1.getPlayAccount() < player2.getPlayAccount()) {
            return new GameResult(player2);
        } else {
            logger.info("Win by score, player1 = {}, player2 = {}", player1.getScore(), player2.getScore());
            if (player1.getScore() > player2.getScore()) {
                return new GameResult(player1);
            } else {
                return new GameResult(player2);
            }
        }
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
            if (puck.getY() > HEIGHT_OF_PLAYING_AREA/2) {
                logger.info("Up, y = {}", puck.getY());
                if (player1.getPlayerPosition() == PlayerPosition.UP) {
                    player2.setPlayAccount(player2.getPlayAccount() + 1);
                    player1Move = playDirect.getDefaultPlayerMove(player1);
                    player2Move = playDirect.getDefaultPlayerMove(player2);
                    playDirect.setUpPlayerPosition(player1);
                    playDirect.setDownPlayerPosition(player2);
                    playDirect.setUpPuckPosition(puck);
                } else {
                    player1.setPlayAccount(player1.getPlayAccount() + 1);
                    player1Move = playDirect.getDefaultPlayerMove(player1);
                    player2Move = playDirect.getDefaultPlayerMove(player2);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setUpPuckPosition(puck);
                }
            } else {
                logger.info("Down, y = {}", puck.getY());
                if (player1.getPlayerPosition() == PlayerPosition.DOWN) {
                    player2.setPlayAccount(player2.getPlayAccount() + 1);
                    player1Move = playDirect.getDefaultPlayerMove(player1);
                    player2Move = playDirect.getDefaultPlayerMove(player2);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setDownPuckPosition(puck);
                } else {
                    player1.setPlayAccount(player1.getPlayAccount() + 1);
                    player1Move = playDirect.getDefaultPlayerMove(player1);
                    player2Move = playDirect.getDefaultPlayerMove(player2);
                    playDirect.setUpPlayerPosition(player2);
                    playDirect.setDownPlayerPosition(player1);
                    playDirect.setDownPuckPosition(puck);
                }
            }
            playStatus = PlayStatus.PUCK;
            logger.debug("Puck!");
            return true;
        }
        return false;
    }

    private boolean checkCrashIntoPlayers() {
        float distanceBetweenPuckAndPlayer1 = (int) Math.sqrt((puck.getX() + puck.getSpeed().getX() - player1.getX()) * (puck.getX() + puck.getSpeed().getX() - player1.getX()) + (puck.getY() + puck.getSpeed().getY() - player1.getY()) * (puck.getY() + puck.getSpeed().getY() - player1.getY()));
        float distanceBetweenPuckAndPlayer2 = (int) Math.sqrt((puck.getX() + puck.getSpeed().getX() - player2.getX()) * (puck.getX() + puck.getSpeed().getX() - player2.getX()) + (puck.getY() + puck.getSpeed().getY() - player2.getY()) * (puck.getY() + puck.getSpeed().getY() - player2.getY()));
        float futureDistanceBetweenPuckAndPlayer1 = (int) Math.sqrt((puck.getX() + 2 * puck.getSpeed().getX() - player1.getX()) * (puck.getX() + 2 * puck.getSpeed().getX() - player1.getX()) + (puck.getY() + 2 * puck.getSpeed().getY() - player1.getY()) * (puck.getY() + 2 * puck.getSpeed().getY() - player1.getY()));
        float futureDistanceBetweenPuckAndPlayer2 = (int) Math.sqrt((puck.getX() + 2 * puck.getSpeed().getX() - player2.getX()) * (puck.getX() + 2 * puck.getSpeed().getX() - player2.getX()) + (puck.getY() + 2 * puck.getSpeed().getY() - player2.getY()) * (puck.getY() + 2 * puck.getSpeed().getY() - player2.getY()));
        if (distanceBetweenPuckAndPlayer1 < (puck.RADIUS + player1.RADIUS) && countOfIterationAfterCrashToPlayer <= 0) {
            logger.trace("Crash into player1!");
            PhysicsUtil.calclateCrashResult(puck, player1);
            countOfIterationAfterCrashToPlayer = DEFAULT_COUNT_OF_ITERATION_AFTER_CRASH_TO_PLAYER;
            return true;
        } else if ((distanceBetweenPuckAndPlayer2 < (puck.RADIUS + player2.RADIUS)) && countOfIterationAfterCrashToPlayer <= 0) {
            logger.trace("Crash into player2!");
            PhysicsUtil.calclateCrashResult(puck, player2);
            countOfIterationAfterCrashToPlayer = DEFAULT_COUNT_OF_ITERATION_AFTER_CRASH_TO_PLAYER;
            return true;
        }
        countOfIterationAfterCrashToPlayer--;
        return false;
    }

    private void handlePlayersMove() {
        handlePlayerMove(player1);
        handlePlayerMove(player2);
    }

    private void handlePlayerMove(Player player) {
        logger.trace("Start handlePlayerMove()!");
        PlayerMove playerMove = null;
        if (player == player1) {
            playerMove = player1Move;
        } else if (player == player2) {
            playerMove = player2Move;
        }
        logger.trace("playerMove = {}", playerMove.toString());
        if (playerMove.getPlayerMoveStatus() == PlayerMoveStatus.YES) {
            Speed playerSpeed = PhysicsUtil.getNewSpeed(playerMove.DEFAULT_SPEED_OF_PLAYER, playerMove.getDirection());
            checkPlayerMove(player, playerSpeed);
            if (player == player1) {
                player1.setX(player1.getX() + playerSpeed.getX());
                player1.setY(player1.getY() + playerSpeed.getY());
            } else if (player == player2) {
                player2.setX(player2.getX() + playerSpeed.getX());
                player2.setY(player2.getY() + playerSpeed.getY());
            }
        }
        if (player.getPlayerPosition() == PlayerPosition.UP) player.setScore(player.getScore() + (HEIGHT_OF_PLAYING_AREA - player.getY()));
        if (player.getPlayerPosition() == PlayerPosition.DOWN) player.setScore(player.getScore() + player.getY());
        logger.trace("Stop handlePlayerMove()!");
    }

    private void checkPlayerMove(Player player, Speed speed) {
        logger.trace("Start checkPlayerMove()!");
        int topY = HEIGHT_OF_PLAYING_AREA/2 - PLAYER_INTERVAL_WITH_CENTER;
        int bottomY = HEIGHT_OF_PLAYING_AREA/2 + PLAYER_INTERVAL_WITH_CENTER;
        int rightX = WIDTH_OF_PLAYING_AREA - PLAYER_INTERVAL_WITH_BORDER;
        int leftX = 0 + PLAYER_INTERVAL_WITH_BORDER;

        if ((player.getX() + speed.getX() + player.RADIUS) > rightX) {
            player.setX(rightX - player.RADIUS);
            speed.setX(0);
        }
        if ((player.getX() + speed.getX() - player.RADIUS) < leftX) {
            player.setX(leftX + player.RADIUS);
            speed.setX(0);
        }

        if (player.getPlayerPosition() == PlayerPosition.UP) {
            if ((player.getY() + speed.getY() + player.RADIUS) > topY) {
                player.setY(topY - player.RADIUS);
                speed.setY(0);
            }
            if ((player.getY() + speed.getY() - player.RADIUS) < 0) {
                player.setY(player.RADIUS);
                speed.setY(0);
            }
        } else if (player.getPlayerPosition() == PlayerPosition.DOWN) {
            if ((player.getY() + speed.getY() - player.RADIUS) < bottomY) {
                player.setY(bottomY + player.RADIUS);
                speed.setY(0);
            }
            if ((player.getY() + speed.getY() + player.RADIUS) > HEIGHT_OF_PLAYING_AREA) {
                player.setY(HEIGHT_OF_PLAYING_AREA - player.RADIUS);
                speed.setY(0);
            }
        }
        logger.trace("Stop checkPlayerMove()!");
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

    public void updatePlayer1Move(PlayerMove player1Move) {
        if (playStatus == PlayStatus.PLAYING) this.player1Move = player1Move;
    }

    public void updatePlayer2Move(PlayerMove player2Move) {
        if (playStatus == PlayStatus.PLAYING) this.player2Move = player2Move;
    }
}
