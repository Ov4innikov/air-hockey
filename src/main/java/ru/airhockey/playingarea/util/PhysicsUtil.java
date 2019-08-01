package ru.airhockey.playingarea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.playingarea.model.Speed;

/**
 * Утилитный класс в котором предусмотренны методы для расчёта отскоков об игроков.
 *
 * @author Овчинников
 */
public class PhysicsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PhysicsUtil.class);
    public static final int DEFAULT_SPEED_OF_PUCK = 5;

    public static float getCorner(Speed puckSpeed) {
        float x = puckSpeed.getX();
        float y = puckSpeed.getY();
        return getCorner(x, y);
    }

    public static float getCorner(float x , float y) {
        logger.trace("getCorner() start!");
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
        logger.trace("getCorner() stop!");
        return corner;
    }

    public static void calclateCrashResult(Puck puck, Player player) {
        logger.trace("calclateCrashResult() start!");
        float xOfPuckAtCrashTime = puck.getX();
        float yOfPuckAtCrashTime = puck.getY();
        float x = xOfPuckAtCrashTime - player.getX();
        float y = yOfPuckAtCrashTime - player.getY();
        float distanceBetweenPuckAndPlayer = (float) Math.sqrt(x * x + y * y);
        logger.trace("distanceBetweenPuckAndPlayer = {}", distanceBetweenPuckAndPlayer);
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
        logger.trace("l  = {}, b = {}", l, b);
        l = (l + 180) % 360;
        l = (2 * b - l + 360) % 360;
        Speed puckSpeed = puck.getSpeed();
        logger.trace("old puckSpeed = {}", puckSpeed);
        float hypotenuse = (float) Math.sqrt(puckSpeed.getX() * puckSpeed.getX() + puckSpeed.getY() * puckSpeed.getY());
        Speed newPuckSpeed;
        if (puck.getSpeed().getX() == 0.0 && puck.getSpeed().getY() == 0.0) {
            newPuckSpeed = getNewSpeed(DEFAULT_SPEED_OF_PUCK, b);
        } else {
            newPuckSpeed = getNewSpeed(hypotenuse, l);
        }
        logger.trace("new puckSpeed = {}", newPuckSpeed);
        puck.setSpeed(newPuckSpeed);
        puck.setX(puck.getX() + puck.getSpeed().getX()/10*(Math.abs(10 - i)));
        puck.setY(puck.getY() + puck.getSpeed().getY()/10*(Math.abs(10 - i)));
    }

    public static Speed getNewSpeed(float hypotenuse, float corner) {
        logger.trace("getNewSpeed() start!");
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
        return new Speed(newXSpeed, newYSpeed);
    }
}
