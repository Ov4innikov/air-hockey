package ru.airhockey.playingarea;

import ru.airhockey.playingarea.model.PlayStatus;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;
import ru.airhockey.web.ws.model.IMessage;

/**
 * Класс Демо сообщения имплементируется от IMessage, заполняется данными игроков, шайбы и статуса игры
 * на конкрентный момент времени
 *
 * @i.sibagatullin
 *
 */
public class DemoMassage implements IMessage {
        private Long tick;
        private Player player1;
        private Player player2;
        private Puck puck;
        private PlayStatus playStatus;

        public DemoMassage(Long tick, Player player1, Player player2, Puck puck, PlayStatus playStatus)
        {
            this.tick = tick;
            this.player1 = player1;
            this.player2 = player2;
            this.puck = puck;
            this.playStatus = playStatus;
        }
    public Long getTick() {
        return tick;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Puck getPuck() {
        return puck;
    }

    public void setPuck(Puck puck) {
        this.puck = puck;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }

    @Override
    public String toString() {
        return "DemoMassage{" +
                "tick=" + tick +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", puck=" + puck +
                ", playStatus=" + playStatus +
                '}';
    }
}
