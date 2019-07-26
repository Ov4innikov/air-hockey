package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.replay.DemoMassage;
import ru.airhockey.replay.DemoPlay;
import ru.airhockey.service.Game;
import ru.airhockey.service.IManager;
import ru.airhockey.web.ws.model.TestMessage;
import ru.airhockey.web.ws.sender.ISender;

import java.util.concurrent.ExecutionException;

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
        manager.createGame(gameId);
        manager.startGame(gameId);
    }
}