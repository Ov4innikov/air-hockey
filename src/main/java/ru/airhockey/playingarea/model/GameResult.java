package ru.airhockey.playingarea.model;

public class GameResult {
    private Player winner;

    public GameResult(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
