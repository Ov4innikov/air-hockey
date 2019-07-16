package ru.airhockey.playingarea;

import javafx.concurrent.Task;
import ru.airhockey.playingarea.model.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SimplePlay implements Play {
    private Player player1;
    private Player player2;
    private Puck puck;
    private Round round = Round.FIRST;
    PlayStatus playStatus = PlayStatus.STARTING;
    private Future result;
    private GameTask task;

    private final ExecutorService executorService;

    public SimplePlay(ExecutorService executorService) {
        //TODO: logger
        System.out.println("Starting SimplePlay");
        this.executorService = executorService;
    }


    @Override
    public void play() {
        puck = new Puck(new PuckSpeed(10,10), 100, 200);
        task = new GameTask(new Player(), new Player(), puck);
        result = executorService.submit(task);
    }

    @Override
    public PlayState getPlayState() {
        return null;
    }



    @Override
    public void handlePlayerMove(PlayerMove playerMove) {

    }

    public void stop() {
        playStatus = PlayStatus.BREAK;
        task.stopGame();
    }

    public Puck getPuck() {
      return null;
    }
}
