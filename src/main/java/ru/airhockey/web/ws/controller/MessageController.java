package ru.airhockey.web.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.airhockey.replay.DemoMassage;
import ru.airhockey.service.ClientMessage;
import ru.airhockey.service.Game;
import ru.airhockey.service.GameManager;
import ru.airhockey.service.IManager;
import ru.airhockey.web.ws.model.IMessage;
import ru.airhockey.web.ws.model.TestMessage;

/**
 * Контроллер принимающий сообщения отправленые на вебсокет от клиента
 * @author folkland
 */

@Controller
public class MessageController {

    @Autowired
    IManager manager;

    /**
     * Тестовый пример подключений к вебсокету
     * @param message приходящее сообщение
     * @return возвращает сообщение в сокет
     */
    @MessageMapping("/test/{id}")
    @SendTo("/topic/test/{id}")
    public IMessage getTestMessage(TestMessage message){
        message.setMessage("Server: " + message.getMessage());
        return message;
    }

    /**
     * Рабочее подключение к вебсокетам
     * @param message приходящее сообщение
     */
//    @MessageMapping("/message/{id}")
//    public void getMessage(IMessage message) {
//        System.out.println("Message controller");
//        manager.setPlayerPosition(message);
//    }

    @MessageMapping("/message/{id}")
    public void getMessage(ClientMessage message) {
        System.out.println("Message controller");
        manager.setPlayerPosition(message);
    }
}
