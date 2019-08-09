package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.airhockey.bot.BotCreateMessage;
import ru.airhockey.bot.BotLevel;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.replay.DemoPlay;
import ru.airhockey.service.IBotManager;
import ru.airhockey.service.IManager;
import ru.airhockey.waitingList.MatchedUser;
import ru.airhockey.waitingList.SearchPlayerManager;
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
    SearchPlayerManager SearchPlayer;

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

    @RequestMapping("/replay")
    public void demoSay(@RequestParam String gameId) {
        demo.demoPlay(gameId);
    }

    @RequestMapping("/demoinsert")
    public void demoInsert() {
        demo.insertGame();
    }

    @RequestMapping("/start")
    public void startGame(@RequestParam String gameId, @RequestParam int user1,@RequestParam int user2) {
        System.out.println("Controller started");
//        manager.createGame(gameId, user1, user2);
        if (!manager.isGameStarted(gameId)) {
            manager.startGame(gameId);
        }
    }

    @RequestMapping("/end")
    public String endGame(@RequestParam String gameId) {
        manager.endGame(gameId);
        return "redirect:/userStatistics";
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

    @RequestMapping(value = "/matchUser", method = RequestMethod.GET)
    public ModelAndView matchUser(@RequestParam int key) {
        SearchPlayer.addQueue(key);
        MatchedUser matchedUser = SearchPlayer.matchUserAndStart(key);
        if(matchedUser!=null){

            ModelAndView mav = new ModelAndView("redirect:/game");
            mav.addObject("user1",matchedUser.getUser1());
            mav.addObject("user2",matchedUser.getUser2());
            if(key==matchedUser.getUser1()) {
                mav.addObject("userPosition", PlayerPosition.DOWN);
            }  else {
                mav.addObject("userPosition", PlayerPosition.UP);
            }
            mav.addObject("gameID",matchedUser.getGameID());
            manager.createGame(matchedUser.getGameID(), matchedUser.getUser1(), matchedUser.getUser2());
           return mav;
        }
        return null;
    }
}