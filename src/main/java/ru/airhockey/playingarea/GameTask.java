package ru.airhockey.playingarea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.GameResult;
import ru.airhockey.playingarea.model.PlayStatus;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;

import java.util.concurrent.Callable;

public class GameTask implements Callable<GameResult> {

    private static final Logger logger = LoggerFactory.getLogger(GameTask.class);

    private static final int HEIGHT_OF_PLAYING_AREA = 1000;
    private static final int WIDTH_OF_PLAYING_AREA = 450;
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

        playStatus = PlayStatus.PLAYING;
        while (playStatus == PlayStatus.PLAYING) {
            checkCrash();
            puck.waitNextIteration();
            logger.trace("x = {}; y = {}; ");
            System.out.println("x = " + puck.getX() + "; y = " + puck.getY() + "; y speed = " + puck.getSpeed().getY());
            if (i++ == 100) {
                playStatus = PlayStatus.STOPPING;
                return new GameResult(player1);
            }
        }
        //Проверка на очки
        return new GameResult(player1);
    }

    private void checkCrash() {
        int xDifferenceWithLeftSide = puck.getX() - puck.RADIUS + puck.getSpeed().getX();
        int xDifferenceWithRightSide = WIDTH_OF_PLAYING_AREA - (puck.getX() + puck.RADIUS + puck.getSpeed().getX());
        int yDifferenceWithBottomSide= puck.getY() - puck.RADIUS + puck.getSpeed().getY();
        int yDifferenceWithTopSide = HEIGHT_OF_PLAYING_AREA - (puck.getY() + puck.RADIUS + puck.getSpeed().getY());
        boolean crashWallsRightLeft = false, crashWallsTopBottom = false;
        if ((xDifferenceWithLeftSide < 0) && (puck.getSpeed().getX() < 0)) {
            puck.setX(Math.abs(xDifferenceWithLeftSide) + puck.RADIUS);
            puck.getSpeed().turnX();
        } else if ((xDifferenceWithRightSide < 0) && (puck.getSpeed().getX() > 0)) {
            puck.setX(WIDTH_OF_PLAYING_AREA - Math.abs(xDifferenceWithRightSide) - puck.RADIUS);
            puck.getSpeed().turnX();
        } else {
            crashWallsRightLeft = true;
        }
        if ((yDifferenceWithTopSide < 0) && (puck.getSpeed().getY() > 0)) {
            puck.setY(HEIGHT_OF_PLAYING_AREA - Math.abs(yDifferenceWithTopSide) -  - puck.RADIUS);
            puck.getSpeed().turnY();
        } else if ((yDifferenceWithBottomSide < 0) && (puck.getSpeed().getY() < 0)) {
            puck.setY(Math.abs(yDifferenceWithBottomSide) +  puck.RADIUS);
            puck.getSpeed().turnY();
        } else {
            crashWallsTopBottom = true;
        }
        if(!checkCrashIntoPlayers()) {
            if (crashWallsRightLeft) {
                puck.setX(puck.getX() + puck.getSpeed().getX());
            }
            if (crashWallsTopBottom) {
                puck.setY(puck.getY() + puck.getSpeed().getY());
            }
        }

    }

    private boolean checkCrashIntoPlayers() {
        int distanceBetweenPuckAndPlayer1 = (int) Math.sqrt(puck.getX() + puck.getSpeed().getX() - player1.getX()) * (puck.getX() + puck.getSpeed().getX() - player1.getX()) + (puck.getY() + puck.getSpeed().getY() - player1.getY()) * (puck.getY() + puck.getSpeed().getY() - player1.getY());
        int distanceBetweenPuckAndPlayer2 = (int) Math.sqrt(puck.getX() + puck.getSpeed().getX() - player2.getX()) * (puck.getX() + puck.getSpeed().getX() - player2.getX()) + (puck.getY() + puck.getSpeed().getY() - player2.getY()) * (puck.getY() + puck.getSpeed().getY() - player2.getY());
        if (distanceBetweenPuckAndPlayer1 < (puck.RADIUS + player1.RADIUS)) {
            return true;
        } else if ((distanceBetweenPuckAndPlayer2 < (puck.RADIUS + player1.RADIUS))) {
            return true;
        }
        return false;
    }


    public void stopGame() {
        playStatus = PlayStatus.STOPPING;
    }


}
