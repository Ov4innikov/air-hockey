package ru.airhockey.web.ws.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.airhockey.web.ws.model.IMessage;

/**
 * Класс отправитель, отправляет универсальный тип IMessage
 * @author folkland
 */
@Component
public class Sender implements ISender {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void send(String id, IMessage message) {
        template.convertAndSend("/topic/message/" + id, message);
    }
}
