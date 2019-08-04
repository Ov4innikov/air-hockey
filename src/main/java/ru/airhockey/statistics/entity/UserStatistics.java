package ru.airhockey.statistics.entity;

/**
 * Сущность с статистикой игрока
 * @author folkland
 */
public class UserStatistics {

    private int id;
    private int idUser;
    private int matchCount;
    private int winCount;
    private int botMatchCount;
    private int botWinCount;
    private int scoredPuck;
    private int missedPuck;
    private int elo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getBotMatchCount() {
        return botMatchCount;
    }

    public void setBotMatchCount(int botMatchCount) {
        this.botMatchCount = botMatchCount;
    }

    public int getBotWinCount() {
        return botWinCount;
    }

    public void setBotWinCount(int botWinCount) {
        this.botWinCount = botWinCount;
    }

    public int getScoredPuck() {
        return scoredPuck;
    }

    public void setScoredPuck(int scoredPuck) {
        this.scoredPuck = scoredPuck;
    }

    public int getMissedPuck() {
        return missedPuck;
    }

    public void setMissedPuck(int missedPuck) {
        this.missedPuck = missedPuck;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
