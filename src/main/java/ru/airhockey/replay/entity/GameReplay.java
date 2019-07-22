package ru.airhockey.replay.entity;

/**
 * Объект базы данных
 *
 * @author folkland
 */
public class GameReplay {

    private int id;
    private String gameId;
    private String gameText;

    public GameReplay(int id, String gameId, String gameText) {
        this.id = id;
        this.gameId = gameId;
        this.gameText = gameText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameText() {
        return gameText;
    }

    public void setGameText(String gameText) {
        this.gameText = gameText;
    }

    @Override
    public String toString() {
        return "GameReplay{" +
                "id=" + id +
                ", gameId='" + gameId + '\'' +
                ", gameText='" + gameText + '\'' +
                '}';
    }
}
