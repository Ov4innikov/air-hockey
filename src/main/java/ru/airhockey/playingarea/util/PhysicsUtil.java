package ru.airhockey.playingarea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.GameTask;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.playingarea.model.PuckSpeed;

/**
 * Утилитный класс в котором предусмотренны методы для расчёта отскоков об игроков.
 *
 * @author Овчинников
 */
public class PhysicsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PhysicsUtil.class);

    public static float getCorner(PuckSpeed puckSpeed) {
        float x = puckSpeed.getX();
        float y = puckSpeed.getY();
        return getCorner(x, y);
    }

    public static float getCorner(float x , float y) {
        logger.info("getCorner() start!");
        float corner = 0;
        if (x >= 0 && y >= 0) {
            corner = (float) (Math.atan(Math.abs(y) / Math.abs(x)) * 180 / Math.PI) % 360;
        } else if (x < 0 && y >= 0) {
            corner = (float) (Math.atan(Math.abs(x) / Math.abs(y)) * 180 / Math.PI + 90) % 360;
        } else if (x < 0 && y < 0) {
            corner = (float) (Math.atan(Math.abs(y) / Math.abs(x)) * 180 / Math.PI + 180) % 360;
        } else if (x >= 0 && y < 0) {
            corner = (float) (Math.atan(Math.abs(x) / Math.abs(y)) * 180 / Math.PI + 270) % 360;
        }
        return corner;
    }

    public static void calclateCrashResult(Puck puck, Player player) {
        logger.info("calclateCrashResult() start!");
        float xOfPuckAtCrashTime = puck.getX();
        float yOfPuckAtCrashTime = puck.getY();
        float x = xOfPuckAtCrashTime - player.getX();
        float y = yOfPuckAtCrashTime - player.getY();
        float distanceBetweenPuckAndPlayer = (float) Math.sqrt(x * x + y * y);
        logger.info("distanceBetweenPuckAndPlayer = {}", distanceBetweenPuckAndPlayer);
        int i = 0;
        while (distanceBetweenPuckAndPlayer > Puck.RADIUS + Player.RADIUS) {
            i++;
            xOfPuckAtCrashTime = xOfPuckAtCrashTime + puck.getSpeed().getX() / 10;
            yOfPuckAtCrashTime = yOfPuckAtCrashTime + puck.getSpeed().getY() / 10;
            x = xOfPuckAtCrashTime - player.getX();
            y = yOfPuckAtCrashTime - player.getY();
            distanceBetweenPuckAndPlayer = (float) Math.sqrt(x * x + y * y);
        }
        float l = getCorner(puck.getSpeed());
        float b = getCorner(xOfPuckAtCrashTime - player.getX(), yOfPuckAtCrashTime - player.getY());
        l = 2 * b - l;
        PuckSpeed puckSpeed = puck.getSpeed();
        PuckSpeed newPuckSpeed;
        float hypotenuse = (float) Math.sqrt(puckSpeed.getX() * puckSpeed.getX() + puckSpeed.getY() * puckSpeed.getY());
        puck.setSpeed(getNewPuckSpeed(hypotenuse, l));
        puck.setX(puck.getX() + puck.getSpeed().getX()/10*(Math.abs(10 - i)));
        puck.setY(puck.getY() + puck.getSpeed().getY()/10*(Math.abs(10 - i)));
    }

    protected static PuckSpeed getNewPuckSpeed(float hypotenuse, float corner) {
        logger.info("getNewPuckSpeed() start!");
        float newXSpeed = 0;
        float newYSpeed = 0;
        float nCorner = corner % 90;
        float radiantCorner = (float) (nCorner / 180 * Math.PI);
        if (corner >= 0 && corner < 90) {
            newXSpeed = (float) (Math.cos(radiantCorner) * hypotenuse);
            newYSpeed = (float) (Math.sin(radiantCorner) * hypotenuse);
        } else if (corner >= 90 && corner < 180) {
            newXSpeed = (float) -(Math.sin(radiantCorner) * hypotenuse);
            newYSpeed = (float) (Math.cos(radiantCorner) * hypotenuse);
        } else if (corner >= 180 && corner < 270) {
            newXSpeed = (float) -(Math.cos(radiantCorner) * hypotenuse);
            newYSpeed = (float) -(Math.sin(radiantCorner) * hypotenuse);
        } else {
            newXSpeed = (float) (Math.sin(radiantCorner) * hypotenuse);
            newYSpeed = (float) -(Math.cos(radiantCorner) * hypotenuse);
        }
        return new PuckSpeed(newXSpeed, newYSpeed);
    }
}
