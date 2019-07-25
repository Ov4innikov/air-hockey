package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.LockSupport;

/**
 * Игрок на игровом поле
 *
 * @author Овчинников
 */
@Getter
@Setter
public class Player {
    public static final int RADIUS = 40;
    public static final int WAIT_TIME = 5;

    private PlayerPosition playerPosition;
    private float x;
    private float y;
    private float playAccount = 0;
    private float score = 0;
    private long updateTime = 0;

    public Player() {}

    public Player(PlayerPosition playerPosition, float x, float y, float score, float playAccount) {
        this.playerPosition = playerPosition;
        this.x = x;
        this.y = y;
        this.playAccount = playAccount;
        this.score = score;
    }

    public void updateTime() {
        updateTime = System.currentTimeMillis();
    }

    public void waitNextIteration() {
        if (isWaitNextIteration()) {
            LockSupport.parkNanos((WAIT_TIME - (System.currentTimeMillis() - updateTime)) * 1_000_000);
        }
        updateTime();
    }

    public boolean isWaitNextIteration() {
        if (System.currentTimeMillis() - updateTime < WAIT_TIME) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", playAccount=" + playAccount +
                ", score=" + score +
                '}';
    }
}

