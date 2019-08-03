package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.airhockey.bot.BotCreateMessage;
import ru.airhockey.bot.BotLevel;
import ru.airhockey.replay.DemoPlay;
import ru.airhockey.service.IBotManager;
import ru.airhockey.service.IManager;
import ru.airhockey.web.ws.model.TestMessage;
import ru.airhockey.web.ws.sender.ISender;

/**
 * Отправитель со стороны сервера по нажатию кнопки на сервере
 * Пример REST запроса
 * По сути это тестовый класс
 * @author folkland
 */
@Controller
@RequestMapping("/app")
public class RestController {

    @Autowired
    ISender sender;

    @Autowired
    DemoPlay demo;

    @Autowired
    IManager manager;

    @Autowired
    IBotManager botManager;

    @RequestMapping("/serversay")
    public void serverSay(@RequestParam String key) {
        TestMessage message = new TestMessage();
        message.setFrom("John");
        message.setMessage("Server want say");
        sender.send(key, message);
    }

    @RequestMapping("/demosay")
    public void demoSay(@RequestParam String key) {
        demo.demoPlay(key);
    }

    @RequestMapping("/demoinsert")
    public void demoInsert() {
        demo.insertGame();
    }

    @RequestMapping("/start")
    public void startGame(@RequestParam String gameId) {
        System.out.println("Controller started");
        manager.createGame(gameId, 0, 0);
        manager.startGame(gameId);
    }

    @RequestMapping("/end")
    public void endGame(@RequestParam String gameId) {
        manager.endGame(gameId);
    }

//    @RequestMapping("/bot-game")
//    public void gameWithBot(@RequestParam BotCreateMessage message) {
//        botManager.createGame(message.getGameId(), message.getBotLevel());
//        botManager.startGame(message.getGameId());
//    }

    @RequestMapping("/bot-game")
    public void gameWithBot(@RequestParam String gameId, @RequestParam BotLevel level) {
        BotCreateMessage message = new BotCreateMessage(gameId, level);
        botManager.createGame(message.getGameId(), message.getBotLevel(), 0);
        botManager.startGame(message.getGameId());
    }

    @RequestMapping("/bot-game-end")
    public void gameWithBotEnd(@RequestParam String gameId) {
        botManager.endGame(gameId);
    }
}