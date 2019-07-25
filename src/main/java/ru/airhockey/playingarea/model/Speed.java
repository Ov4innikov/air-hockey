package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Speed {
    private float x = 0;
    private float y = 0;

    public Speed(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void turnX() {
        x = -x;
    }

    public void turnY() {
        y = -y;
    }

    @Override
    public String toString() {
        return "PuckSpeed{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
