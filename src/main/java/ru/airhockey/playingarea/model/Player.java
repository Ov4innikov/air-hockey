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
}
