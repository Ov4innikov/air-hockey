package ru.airhockey.playingarea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayState {

    PlayStatus playStatus;
    Player player1;
    Player player2;
    Puck puck;
    Player winner;
}
