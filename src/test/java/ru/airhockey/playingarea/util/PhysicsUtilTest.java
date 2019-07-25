package ru.airhockey.playingarea.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.playingarea.model.Speed;

public class PhysicsUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(PhysicsUtilTest.class);

    @Test
    public void getCorner45() {
        Speed puckSpeed = new Speed(1,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(45.0, corner, 0.001);
    }

    @Test
    public void getCorner135() {
        Speed puckSpeed = new Speed(-1,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(135.0, corner, 0.001);
    }

    @Test
    public void getCorner225() {
        Speed puckSpeed = new Speed(-1,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(225.0, corner, 0.001);
    }

    @Test
    public void getCorner315() {
        Speed puckSpeed = new Speed(1,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(315.0, corner, 0.001);
    }

    @Test
    public void getCorner0() {
        Speed puckSpeed = new Speed(1,0);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(0.0, corner, 0.001);
    }

    @Test
    public void getCorner90() {
        Speed puckSpeed = new Speed(0,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(90.0, corner, 0.001);
    }

    @Test
    public void getCorner180() {
        Speed puckSpeed = new Speed(-1,0);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(180.0, corner, 0.001);
    }

    @Test
    public void getCorner270() {
        Speed puckSpeed = new Speed(0,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(270.0, corner, 0.001);
    }

    @Test
    public void getNewPuckSpeed1and0() {
        Speed puckSpeed = PhysicsUtil.getNewSpeed(1, 0);
        Assert.assertEquals(1, puckSpeed.getX(), 0.1);
        Assert.assertEquals(0, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeed0and1() {
        Speed puckSpeed = PhysicsUtil.getNewSpeed(1, 90);
        Assert.assertEquals(0, puckSpeed.getX(), 0.1);
        Assert.assertEquals(1, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeedMinus1and0() {
        Speed puckSpeed = PhysicsUtil.getNewSpeed(1, 180);
        Assert.assertEquals(-1, puckSpeed.getX(), 0.1);
        Assert.assertEquals(0, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeed0andMinus1() {
        Speed puckSpeed = PhysicsUtil.getNewSpeed(1, 270);
        Assert.assertEquals(0, puckSpeed.getX(), 0.1);
        Assert.assertEquals(-1, puckSpeed.getY(), 0.1);
    }

    @Test
    public void calclateCrashResult1() {
        Puck puck = new Puck(new Speed(10,10), 30, 30);
        Player player = new Player(PlayerPosition.DOWN, 35, 94, 0, 0);
        PhysicsUtil.calclateCrashResult(puck, player);
        logger.info("x speed = {}, y speed = {}", puck.getSpeed().getX(), puck.getSpeed().getY());
    }

    @Test
    public void calclateCrashResult2() {
        Puck puck = new Puck(new Speed(0,10), 30, 30);
        Player player = new Player(PlayerPosition.DOWN, 30, 94, 0, 0);
        PhysicsUtil.calclateCrashResult(puck, player);
        logger.info("x speed = {}, y speed = {}", puck.getSpeed().getX(), puck.getSpeed().getY());
    }

    @Test
    public void calclateCrashResult3() {
        Puck puck = new Puck(new Speed(-3,1), 285, 209);
        Player player = new Player(PlayerPosition.DOWN, 225, 200, 0, 0);
        PhysicsUtil.calclateCrashResult(puck, player);
        logger.info("x speed = {}, y speed = {}", puck.getSpeed().getX(), puck.getSpeed().getY());
    }
}