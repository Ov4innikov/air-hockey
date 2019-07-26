package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;
import ru.airhockey.web.ws.model.IMessage;


@Getter
@Setter
public class PlayerMessage implements IMessage {
    private Player player;

    public PlayerMessage(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PlayerMessage{" +
                "player=" + player +
                '}';
    }
}
