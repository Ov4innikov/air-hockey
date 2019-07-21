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

    private int x;
    private int y;
    private int playAccount = 0;
    private int score = 0;

    public Player() {
    }

    public Player(int x, int y, int scope, int playAccount) {
        this.x = x;
        this.y = y;
        this.score = scope;
        this.playAccount = playAccount;
    }

    public Player(String x, String y, String scope, String playAccount) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.score = Integer.parseInt(scope);
        this.playAccount = Integer.parseInt(playAccount);
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

