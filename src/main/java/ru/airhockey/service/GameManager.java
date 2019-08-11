package ru.airhockey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.replay.DemoMassage;
import ru.airhockey.replay.dao.GameReplayDAO;
import ru.airhockey.statistics.dao.GameHistoryDAO;
import ru.airhockey.statistics.dao.UserStatisticsDAO;
import ru.airhockey.statistics.entity.GameHistory;
import ru.airhockey.statistics.entity.UserResult;
import ru.airhockey.web.ws.model.IMessage;
import ru.airhockey.web.ws.sender.ISender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class GameManager implements IManager {
    @Autowired
    private ISender sender;
    @Autowired
    private GameReplayDAO template;
    @Autowired
    private GameHistoryDAO historyDAO;
    @Autowired
    private UserStatisticsDAO userStatisticsDAO;

    private Map<String, Game> gameMap;

    public GameManager() {
        gameMap = new HashMap<>();
    }

    @Override
    public void createGame(String gameId, int user1, int user2) {
        Game game = new Game(sender, gameId, user1, user2);
        gameMap.put(gameId, game);
    }

    @Override
    public void startGame(String gameId) {
        try {
            gameMap.get(gameId).startGame(new Player(), new Player());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endGame(String gameId) {
        Game game = gameMap.remove(gameId);
        if (game == null) return;
        List<String> demoMassageList = game.getDemoMassageList();
        StringBuilder builder = new StringBuilder();
        for (String demoMassage : demoMassageList) {
            builder.append(demoMassage);
        }
        template.insertGame(gameId, builder.toString());
        boolean isBot = false;
        if (game.getUser1() == -1 || game.getUser2() == -1) {
            isBot = true;
        }
        boolean isWin = true;
        UserResult user1 = UserResult.WIN;
        UserResult user2 = UserResult.LOSE;
        Player player1 = game.getSimplePlay().getPlayState().getPlayer1();
        Player player2 = game.getSimplePlay().getPlayState().getPlayer2();
        if (player2.equals(game.getSimplePlay().getPlayState().getWinner())) {
            user1 = UserResult.LOSE;
            user2 = UserResult.WIN;
            isWin = false;
        }
        if (game.getUser1() != -1) historyDAO.insertGame(gameId, game.getUser1(), game.getUser2(), isWin, PlayerPosition.DOWN);
        if (game.getUser2() != -1) historyDAO.insertGame(gameId, game.getUser2(), game.getUser1(), !isWin, PlayerPosition.UP);
        userStatisticsDAO.updateStatistics(game.getUser1(), user1, (int) player1.getPlayAccount(), (int) player2.getPlayAccount(), isBot);
        userStatisticsDAO.updateStatistics(game.getUser2(), user2, (int) player2.getPlayAccount(), (int) player1.getPlayAccount(), isBot);
    }

    @Override
    public void setPlayerPosition(IMessage message) {
        ClientMessage clientMessage = (ClientMessage) message;
        gameMap.get(clientMessage.getGameId()).setPlayerPosition(clientMessage);
    }

    @Override
    public Game getGameById(String gameId) {
        return gameMap.get(gameId);
    }

    @Override
    public boolean isGameStarted(String gameId) {
        return gameMap.get(gameId).isStarted();
    }
}
