package ru.airhockey.web.ws.sender;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.airhockey.web.ws.model.IMessage;

/**
 * Класс отправитель, отправляет универсальный тип IMessage
 * @author folkland
 */
public class Sender implements ISender {

    private final String ID;
    private SimpMessagingTemplate template;

    public Sender(String id) {
        ID = id;
    }

    @Override
    public void send(IMessage message) {
        template.convertAndSend("/topic/message/" + ID, message.getMessage());
    }
}
