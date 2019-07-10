package ru.airhockey.web.ws.model;

/**
 * Тестовый класс для проверки работы вебсокетов
 * @author folkland
 */
public class TestMessage implements IMessage {

    private String from;
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMesage() {
        return message;
    }

    public void setMesage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "{" +
                "\"from\":\"" + from + '\"' +
                ", \"message\":\"" + message + '\"' +
                '}';
    }

    @Override
    public String toString() {
        return "TestMessage{" +
                "from='" + from + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
