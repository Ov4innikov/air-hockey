package ru.airhockey.playingarea;

import lombok.Cleanup;
import lombok.SneakyThrows;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.web.ws.sender.ISender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *Класс прорисовки демо игры
 *
 * @i.Sibagatullin
 */
public class DemoPlay {

    static final String FILE_NAME = "/demoData.txt";

    @SneakyThrows
    public static void doWithFileLines(String fileName, Consumer<String> stringConsumer) {
        @Cleanup
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        DemoPlay.class.getResourceAsStream(fileName), UTF_8));
        String line;
        while ((line = reader.readLine()) != null)
            stringConsumer.accept(line);
    }

    public void demoPlay(String demoSocketName, ISender sender) {
        Player player1 = new Player();
        Player player2 = new Player();
        final Long[] tick = new Long[1];

        PuckSpeed speed = new PuckSpeed(10, 10);
        Puck puck = new Puck(speed, 0, 0);
        final PlayStatus[] playStatus = new PlayStatus[1];
        doWithFileLines(FILE_NAME, line -> {

                    String[] point = line.split(";");
                    tick[0] = Long.parseLong(point[0]);
                    player1.player(point[1], point[2], point[3], point[4]);
                    player2.player(point[5], point[6], point[7], point[8]);
                    puck.Puck(speed, point[9], point[10]);
                    playStatus[0] = PlayStatus.valueOf(point[11]);

                    sender.send(demoSocketName, new DemoMassage(tick[0], player1, player2, puck, playStatus[0]));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
