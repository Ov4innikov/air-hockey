package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.airhockey.web.ws.model.TestMessage;

/**
 * Отправитель со стороны сервера по нажатию кнопки на сервере
 * Пример REST запроса
 * По сути это тестовый класс
 * @author folkland
 */
@Controller
public class RestController {

    @Autowired
    SimpMessagingTemplate template;

    @RequestMapping("/app/serversay")
    public void serverSay(@RequestParam String key) {
        System.out.println(key);
        TestMessage message = new TestMessage();
        message.setFrom("John");
        message.setMessage("Server want say");
        template.convertAndSend("/topic/test/"+key, message);
    }
}
