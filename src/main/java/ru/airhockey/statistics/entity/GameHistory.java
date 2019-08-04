package ru.airhockey.statistics.entity;

/**
 * @author folkland
 */
public class GameHistory {

    private int id;
    private String id_game;
    private int id_user;
    private int opponent;
    private String opponentName;
    private boolean isWin;

    public GameHistory(int id, String id_game, int id_user, int opponent, boolean isWin) {
        this.id = id;
        this.id_game = id_game;
        this.id_user = id_user;
        this.opponent = opponent;
        this.isWin = isWin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getOpponent() {
        return opponent;
    }

    public void setOpponent(int opponent) {
        this.opponent = opponent;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    @Override
    public String toString() {
        return "GameHistory{" +
                "id=" + id +
                ", id_game='" + id_game + '\'' +
                ", id_user=" + id_user +
                ", opponent=" + opponent +
                ", opponentName='" + opponentName + '\'' +
                ", isWin=" + isWin +
                '}';
    }
}
