package ru.airhockey.service;

import lombok.Getter;
import lombok.Setter;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerMoveStatus;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.web.ws.model.IMessage;

@Getter
@Setter
public class ClientMessage implements IMessage {
    private String gameId;

//    private Player player;
    private PlayerPosition playerPosition;
    private PlayerMoveStatus playerMoveStatus;
    private float direction;

//    public ClientMessage(String gameId, Player player, PlayerMoveStatus playerMoveStatus, float direction) {
//        this.gameId = gameId;
//        this.player = player;
//        this.playerMoveStatus = playerMoveStatus;
//        this.direction = direction;
//    }


    public ClientMessage(String gameId, PlayerPosition playerPosition, PlayerMoveStatus playerMoveStatus, float direction) {
        this.gameId = gameId;
        this.playerPosition = playerPosition;
        this.playerMoveStatus = playerMoveStatus;
        this.direction = direction;
    }
}
