package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.airhockey.playingarea.DemoPlay;
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

    @RequestMapping("/serversay")
    public void serverSay(@RequestParam String key) {
//        System.out.println(key);
        TestMessage message = new TestMessage();
        message.setFrom("John");
        message.setMessage("Server want say");
        sender.send(key, message);
//        template.convertAndSend("/topic/test/"+key, message);
    }

    @RequestMapping("/demosay")
    public void demoSay(@RequestParam String key) {
        DemoPlay demo = new DemoPlay();
        demo.demoPlay("demoPlay", sender);
    }
}
