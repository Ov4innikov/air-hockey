package ru.airhockey.web.ws.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.airhockey.web.ws.model.IMessage;
import ru.airhockey.web.ws.model.TestMessage;

/**
 * Контроллер принимающий сообщения отправленые на вебсокет от клиента
 * @author folkland
 */

@Controller
public class MessageController {

    /**
     * Тестовый пример подключений к вебсокету
     * @param message приходящее сообщение
     * @return возвращает сообщение в сокет
     */
    @MessageMapping("/test/{id}")
    @SendTo("/topic/test/{id}")
    public String getTestMessage(TestMessage message){
        message.setMesage("Server: " + message.getMesage());
        return message.getMessage();
    }

    /**
     * Рабочее подключение к вебсокетам
     * @param message приходящее сообщение
     */
    @MessageMapping("/message/{id}")
    @SendTo("/topic/message/{id}")
    public void getMessage(IMessage message){

    }
}
