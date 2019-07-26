package ru.airhockey.service;

import lombok.Getter;
import lombok.Setter;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerMoveStatus;
import ru.airhockey.web.ws.model.IMessage;

@Getter
@Setter
public class ClientMessage implements IMessage {
    private String gameId;

    private Player player;
    private PlayerMoveStatus playerMoveStatus;
    private float direction;

    public ClientMessage(String gameId, Player player, PlayerMoveStatus playerMoveStatus, float direction) {
        this.gameId = gameId;
        this.player = player;
        this.playerMoveStatus = playerMoveStatus;
        this.direction = direction;
    }
}
