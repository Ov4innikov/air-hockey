package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.LockSupport;

/**
 * Шайба
 *
 * @author Овчинников
 */
@Getter
@Setter
public class Puck {
    public static final int WAIT_TIME = 15;
    public static final int RADIUS = 20;

    private Speed speed;
    private float x;
    private float y;
    private long updateTime = 0;

    public Puck(Speed speed, float x, float y) {
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    public void updateTime() {
        updateTime = System.currentTimeMillis();
    }

    public void waitNextIteration() {
        if (System.currentTimeMillis() - updateTime < WAIT_TIME) {
            LockSupport.parkNanos((WAIT_TIME - (System.currentTimeMillis() - updateTime)) * 1_000_000);
        }
        updateTime();
    }

    @Override
    public String toString() {
        return "Puck{" +
                "speed=" + speed +
                ", x=" + x +
                ", y=" + y +
                ", updateTime=" + updateTime +
                '}';
    }
}
