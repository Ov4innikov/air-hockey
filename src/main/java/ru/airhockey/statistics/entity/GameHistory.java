package ru.airhockey.statistics.entity;

/**
 * @author folkland
 */
public class GameHistory {

    private int id;
    private String id_game;
    private int id_user;

    public GameHistory(int id, String id_game, int id_user) {
        this.id = id;
        this.id_game = id_game;
        this.id_user = id_user;
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

    @Override
    public String toString() {
        return "GameHistory{" +
                "id=" + id +
                ", id_game=" + id_game +
                ", id_user=" + id_user +
                '}';
    }
}
