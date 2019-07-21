package ru.airhockey.replay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.replay.dao.GameReplayDAO;
import ru.airhockey.replay.entity.GameReplay;
import ru.airhockey.web.ws.sender.ISender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTextFromDB(String gameId) {
        GameReplay gameReplay = replay.getGameById(gameId);
        return gameReplay.getGameText();
    }

    private List<DemoMassage> parseDBFormat(String text) {
        List<DemoMassage> message = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] point = line.split(";");
            long tick = Long.parseLong(point[0]);
            Player player1 = new Player(point[1], point[2], point[3], point[4]);
            Player player2 = new Player(point[5], point[6], point[7], point[8]);
            PuckSpeed speed = new PuckSpeed(10, 10);
            Puck puck = new Puck(speed, point[9], point[10]);
            PlayStatus playStatus = PlayStatus.valueOf(point[11]);
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
                if ((char) c == '\r') continue;
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
