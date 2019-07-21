package ru.airhockey.playingarea.model;

import lombok.AllArgsConstructor;
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
    public static final int WAIT_TIME = 20;
    public static final int RADIUS = 20;

    private PuckSpeed speed;
    private float x;
    private float y;
    private long updateTime;

    public Puck(PuckSpeed speed, String x, String y) {
        this.speed = speed;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    public Puck(PuckSpeed speed, int x, int y) {
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
