package ru.airhockey.playingarea.direct;

import ru.airhockey.playingarea.model.*;

/**
 * Класс управляющий положением шайбы и игроков.
 *
 * @author Овчинников
 */
public class PlayDirect {

    private static final PlayDirect playDirect;

    private final int upPlayerPositionX;
    private final int upPlayerPositionY;
    private final int downPlayerPositionX;
    private final int downPlayerPositionY;

    private final int upPuckPositionX;
    private final int upPuckPositionY;
    private final int downPuckPositionX;
    private final int downPuckPositionY;

    static {
        playDirect = new PlayDirect();
    }

    public PlayDirect () {
        upPlayerPositionX = 260;
        upPlayerPositionY = 50;
        downPlayerPositionX = 260;
        downPlayerPositionY = 640;
        upPuckPositionX = 260;
        upPuckPositionY = 100;
        downPuckPositionX = 260;
        downPuckPositionY = 540;
    }

    public static PlayDirect getInstance() {
        return playDirect;
    }

    public void setDownPlayerPosition(Player player) {
        player.setX(downPlayerPositionX);
        player.setY(downPlayerPositionY);
        player.setPlayerPosition(PlayerPosition.DOWN);
    }

    public void setUpPlayerPosition(Player player) {
        player.setX(upPlayerPositionX);
        player.setY(upPlayerPositionY);
        player.setPlayerPosition(PlayerPosition.UP);
    }

    public void setUpPuckPosition(Puck puck) {
        puck.setSpeed(new Speed(0, 0));
        puck.setX(upPuckPositionX);
        puck.setY(upPuckPositionY);
    }

    public void setDownPuckPosition(Puck puck) {
        puck.setSpeed(new Speed(0, 0));
        puck.setX(downPuckPositionX);
        puck.setY(downPuckPositionY);
    }

    public PlayerMove getDefaultPlayerMove(Player player) {
        return new PlayerMove(player, PlayerMoveStatus.NO, 0);
    }
}
