package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerMove {

    public static final long DEFAULT_SPEED_OF_PLAYER = 3;

    private Player player;
    private PlayerMoveStatus playerMoveStatus;
    private float direction;

    public PlayerMove(Player player, PlayerMoveStatus playerMoveStatus, float direction) {
        this.player = player;
        this.playerMoveStatus = playerMoveStatus;
        this.direction = direction;
    }
}
