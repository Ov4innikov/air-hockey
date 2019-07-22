package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Игрок на игровом поле
 *
 * @author Овчинников
 */
@Getter
@Setter
public class Player {
    public static final int RADIUS = 40;

    private PlayerPosition playerPosition;
    private float x;
    private float y;
    private float playAccount = 0;
    private float score = 0;

    public Player() {}

    public Player(PlayerPosition playerPosition, float x, float y, float score, float playAccount) {
        this.playerPosition = playerPosition;
        this.x = x;
        this.y = y;
        this.playAccount = playAccount;
        this.score = score;
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

