package ru.airhockey.web.ws.model;

/**
 * Описание интерфейся для сообщения
 *
 * @author folkland
 */
public interface IMessage {

    /**
     * Метод предназначен для возвращение результата клиенту
     * @return возвращает JSON в виде строки
     */
    String getMessage();
}
