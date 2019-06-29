package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuckSpeed {
    private int x = 0;
    private int y = 0;

    public PuckSpeed(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void turnX() {
        x = -x;
    }

    public void turnY() {
        y = -y;
    }
}
