package ru.airhockey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.replay.DemoMassage;
import ru.airhockey.replay.dao.GameReplayDAO;
import ru.airhockey.web.ws.model.IMessage;
import ru.airhockey.web.ws.sender.ISender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class GameManager implements IManager {
    @Autowired
    ISender sender;
    @Autowired
    GameReplayDAO template;

    private Map<String, Game> gameMap;

    public GameManager() {
        gameMap = new HashMap<>();
    }

    @Override
    public void createGame(String gameId) {
        Game game = new Game(sender, gameId);
        gameMap.put(gameId, game);
    }

    @Override
    public void startGame(String gameId) {
        try {
            gameMap.get(gameId).startGame(new Player(PlayerPosition.DOWN, 0, 0, 0, 0), new Player(PlayerPosition.UP, 0, 0, 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endGame(String gameId) {
        List<DemoMassage> demoMassageList = gameMap.get(gameId).getDemoMassageList();
        StringBuilder builder = new StringBuilder();
        for (DemoMassage demoMassage : demoMassageList) {
            builder.append(demoMassage.toDBFormat());
        }
        template.insertGame(gameId, builder.toString());
        gameMap.remove(gameId);
    }

    @Override
    public void setPlayerPosition(IMessage message) {
        ClientMessage clientMessage = (ClientMessage) message;
        Game game = gameMap.get(clientMessage.getGameId());
        gameMap.get(clientMessage.getGameId()).setPlayerPosition(clientMessage);
    }
}
