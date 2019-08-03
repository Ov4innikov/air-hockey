package ru.airhockey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.bot.Bot;
import ru.airhockey.bot.BotLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Сервис который отвечает за создание игр с ботами и управлением их жизненными циклами
 * @author folkland
 */
@Component
public class BotManager implements IBotManager {

    @Autowired
    IManager manager;

    private Map<String, Bot> botMap;

    public BotManager() {
        botMap = new HashMap<>();
    }

    @Override
    public void createGame(String gameId, BotLevel botLevel, int user) {
        manager.createGame(gameId, -1, user);
        Bot bot = new Bot(gameId, manager.getGameById(gameId), botLevel);
        botMap.put(gameId, bot);
    }

    @Override
    public void startGame(String gameId) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> result = service.submit(botMap.get(gameId));
        manager.startGame(gameId);
        try {
            String endGame = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    @Override
    public void endGame(String gameId) {
        botMap.get(gameId).endGameForce();
        botMap.remove(gameId);
        manager.endGame(gameId);
    }
}
