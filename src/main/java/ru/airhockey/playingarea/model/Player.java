package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * Игрок на игровом поле
 *
 * @author Овчинников
 */
@Getter
@Setter
public class Player {
    public static final int RADIUS = 20;
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
                "playerPosition=" + playerPosition +
                ", x=" + x +
                ", y=" + y +
                ", playAccount=" + playAccount +
                ", score=" + score +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Float.compare(player.x, x) == 0 &&
                Float.compare(player.y, y) == 0 &&
                Float.compare(player.playAccount, playAccount) == 0 &&
                Float.compare(player.score, score) == 0 &&
                updateTime == player.updateTime &&
                playerPosition == player.playerPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerPosition, x, y, playAccount, score, updateTime);
    }
}

