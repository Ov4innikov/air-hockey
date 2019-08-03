package ru.airhockey.bot;

import ru.airhockey.playingarea.GameTask;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerMove;
import ru.airhockey.playingarea.model.PlayerMoveStatus;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.playingarea.util.PhysicsUtil;

/**
 * Алгоритм бота в том, чтобы двигаться по определенному радиусу вокруг ворот
 * @author folkland
 */
public class RadiusBot implements Level {

    private final float MAX_X;
    private final float MIN_X;
    private final float MAX_Y;
    private final float MIN_Y;
    private final float RADIUS;

    public RadiusBot() {
        RADIUS = GameTask.WIDTH_OF_GOAL;
        MAX_X = GameTask.WIDTH_OF_PLAYING_AREA/2 + RADIUS;
        MIN_X = GameTask.WIDTH_OF_PLAYING_AREA/2 - RADIUS;
        MIN_Y = 0;
        MAX_Y = RADIUS;
    }

    @Override
    public PlayerMove calculateMoving(Puck puck, Player player) {
        return analysisDirection(puck, player);
    }

    private PlayerMove generateMessage(Player player, PlayerMoveStatus playerMoveStatus, float direction) {
        return new PlayerMove(player, playerMoveStatus, direction);
    }

    private PlayerMove analysisDirection(Puck puck, Player player) {
        if (puck.getSpeed().getX() == 0 && puck.getSpeed().getY() == 0) {
            if (puck.getY() > GameTask.HEIGHT_OF_PLAYING_AREA / 2) return generateMessage(player, PlayerMoveStatus.NO, 0);
            float corner = PhysicsUtil.getCorner(puck.getX() - player.getX(), puck.getY() - player.getY());
            return generateMessage(player, PlayerMoveStatus.YES, corner);
        }
        float xProcentPuck = puck.getX() / GameTask.WIDTH_OF_PLAYING_AREA;
        float yProcentPuck = puck.getY() / GameTask.HEIGHT_OF_PLAYING_AREA;
        float xBot = (MAX_X - MIN_X) * xProcentPuck + MIN_X;
        float yBot = (MAX_Y - MIN_Y) * yProcentPuck + MIN_Y;
        if (xBot == player.getX() && yBot == player.getY()) return generateMessage(player, PlayerMoveStatus.NO, 0);
        float corner = PhysicsUtil.getCorner(xBot - player.getX(), yBot - player.getY());
        return generateMessage(player, PlayerMoveStatus.YES, corner);
    }
}
