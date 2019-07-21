package ru.airhockey.playingarea.direct;

import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.playingarea.model.PuckSpeed;

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
        upPlayerPositionX = 225;
        upPlayerPositionY = 200;
        downPlayerPositionX = 225;
        downPlayerPositionY = 800;
        upPuckPositionX = 225;
        upPuckPositionY = 350;
        downPuckPositionX = 225;
        downPuckPositionY = 650;
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
        puck.setSpeed(new PuckSpeed(2, 3));
        puck.setX(upPuckPositionX);
        puck.setY(upPuckPositionY);
    }

    public void setDownPuckPosition(Puck puck) {
        puck.setSpeed(new PuckSpeed(2, 3));
        puck.setX(downPuckPositionX);
        puck.setY(downPuckPositionY);
    }
}
