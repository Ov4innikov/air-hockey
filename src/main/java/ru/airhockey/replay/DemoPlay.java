package ru.airhockey.replay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.playingarea.GameTask;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.replay.dao.GameReplayDAO;
import ru.airhockey.replay.entity.GameReplay;
import ru.airhockey.web.ws.sender.ISender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Класс прорисовки демо игры
 *
 * @author i.Sibagatullin
 */
@Component
public class DemoPlay {

    static final String FILE_NAME = "/demoData.txt";

    @Autowired
    ISender sender;

    @Autowired
    GameReplayDAO replay;

    public void demoPlay(String demoSocketName) {
        String gameText = getTextFromDB(demoSocketName);
        List<DemoMassage> messages = parseDBFormat(gameText);
        for (DemoMassage message: messages) {
            sender.send(demoSocketName, message);
            LockSupport.parkNanos(Puck.WAIT_TIME * 1_000_000);
        }
    }

    private String getTextFromDB(String gameId) {
        GameReplay gameReplay = replay.getGameByGameId(gameId);
        return gameReplay.getGameText();
    }

    /**
     * Парсим данные полученные из базы
     * @author farhutdinov
     * @param text вся игра в виде текста
     * @return игра в виде готовых сообщений
     */
    private List<DemoMassage> parseDBFormat(String text) {
        List<DemoMassage> message = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] point = line.split(";");
            long tick = Long.parseLong(point[0]);
            Player player1 = new Player(PlayerPosition.valueOf(point[5]), Float.parseFloat(point[1]), Float.parseFloat(point[2]), Float.parseFloat(point[3]), Float.parseFloat(point[4]));
            Player player2 = new Player(PlayerPosition.valueOf(point[10]), Float.parseFloat(point[6]), Float.parseFloat(point[7]), Float.parseFloat(point[8]), Float.parseFloat(point[9]));
            Speed speed = new Speed(10, 10);
            Puck puck = new Puck(speed, Float.parseFloat(point[11]), Float.parseFloat(point[12]));
            PlayStatus playStatus = PlayStatus.valueOf(point[13]);
            message.add(new DemoMassage(tick, player1, player2, puck, playStatus));
        }
        return message;
    }

    /*
    функция нужна до того момента, пока не появится игровой модели
    заливает демо игру
     */
    public void insertGame() {
        replay.clearTable();
        StringBuilder builder = new StringBuilder();
        try (InputStream fis = DemoPlay.class.getResourceAsStream(FILE_NAME)) {
            int c;
            while ((c = fis.read()) != -1) {
                if ((char) c == '\r') {
                    continue;
                }
                builder.append((char) c);
            }
            replay.insertGame("demoPlay", builder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
