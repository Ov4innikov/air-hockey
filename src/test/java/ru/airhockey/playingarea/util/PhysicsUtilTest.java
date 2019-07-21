package ru.airhockey.playingarea.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.PuckSpeed;

public class PhysicsUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(PhysicsUtilTest.class);

    @Test
    public void getCorner45() {
        PuckSpeed puckSpeed = new PuckSpeed(1,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(45.0, corner, 0.001);
    }

    @Test
    public void getCorner135() {
        PuckSpeed puckSpeed = new PuckSpeed(-1,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(135.0, corner, 0.001);
    }

    @Test
    public void getCorner225() {
        PuckSpeed puckSpeed = new PuckSpeed(-1,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(225.0, corner, 0.001);
    }

    @Test
    public void getCorner315() {
        PuckSpeed puckSpeed = new PuckSpeed(1,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(315.0, corner, 0.001);
    }

    @Test
    public void getCorner0() {
        PuckSpeed puckSpeed = new PuckSpeed(1,0);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(0.0, corner, 0.001);
    }

    @Test
    public void getCorner90() {
        PuckSpeed puckSpeed = new PuckSpeed(0,1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(90.0, corner, 0.001);
    }

    @Test
    public void getCorner180() {
        PuckSpeed puckSpeed = new PuckSpeed(-1,0);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(180.0, corner, 0.001);
    }

    @Test
    public void getCorner270() {
        PuckSpeed puckSpeed = new PuckSpeed(0,-1);
        float corner = PhysicsUtil.getCorner(puckSpeed);
        logger.info("Corner = {}", corner);
        Assert.assertEquals(270.0, corner, 0.001);
    }

    @Test
    public void getNewPuckSpeed1and0() {
        PuckSpeed puckSpeed = PhysicsUtil.getNewPuckSpeed(1, 0);
        Assert.assertEquals(1, puckSpeed.getX(), 0.1);
        Assert.assertEquals(0, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeed0and1() {
        PuckSpeed puckSpeed = PhysicsUtil.getNewPuckSpeed(1, 90);
        Assert.assertEquals(0, puckSpeed.getX(), 0.1);
        Assert.assertEquals(1, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeedMinus1and0() {
        PuckSpeed puckSpeed = PhysicsUtil.getNewPuckSpeed(1, 180);
        Assert.assertEquals(-1, puckSpeed.getX(), 0.1);
        Assert.assertEquals(0, puckSpeed.getY(), 0.1);
    }

    @Test
    public void getNewPuckSpeed0andMinus1() {
        PuckSpeed puckSpeed = PhysicsUtil.getNewPuckSpeed(1, 270);
        Assert.assertEquals(0, puckSpeed.getX(), 0.1);
        Assert.assertEquals(-1, puckSpeed.getY(), 0.1);
    }
}