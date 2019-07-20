package ru.airhockey.web.ws.sender;

import ru.airhockey.web.ws.model.IMessage;

/**
 * Интерфейс описывающий работу отправителя
 * @author folkland
 */
public interface ISender {

    /**
     * Метод который описывает непосредственно отправку сообщения
     * @param message
     */
    void send(String id, IMessage message);
}
