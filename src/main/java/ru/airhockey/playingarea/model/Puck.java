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
    public static final int WAIT_TIME = 50;
    public static final int RADIUS = 20;

    private PuckSpeed speed;
    private int direction;
    private int x;
    private int y;
    private long updateTime;

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
            LockSupport.parkNanos((WAIT_TIME - (System.currentTimeMillis() - updateTime)) * 1000000);
        }
        updateTime();
    }
}
