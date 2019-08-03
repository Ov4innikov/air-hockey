package ru.airhockey.service;

import lombok.Getter;
import lombok.Setter;
import ru.airhockey.playingarea.Play;
import ru.airhockey.playingarea.SimplePlay;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.replay.DemoMassage;
import ru.airhockey.web.ws.model.IMessage;
import ru.airhockey.web.ws.sender.ISender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.LockSupport;

@Getter
@Setter
public class Game {
    private Play simplePlay;
    private ISender sender;
    private String gameId;
    private long tick;
    private List<DemoMassage> demoMassageList;

    private int user1;
    private int user2;

    public Game() {
        demoMassageList = new ArrayList<>();
    }

    public Game(ISender sender, String gameId, int user1, int user2) {
        this();
        this.sender = sender;
        this.gameId = gameId;
        this.user1 = user1;
        this.user2 = user2;
    }

    private DemoMassage getDemoMessage() {
        DemoMassage demoMassage = new DemoMassage(tick, simplePlay);
        demoMassageList.add(demoMassage);
        return demoMassage;
    }

    public void setPlayerPosition(ClientMessage clientMessage) {
        SimplePlay simplePlay = (SimplePlay) this.simplePlay;
        PlayerMove playerMove = new PlayerMove();
        if (clientMessage.getPlayerPosition() == PlayerPosition.DOWN) {
            playerMove.setPlayer(simplePlay.getPlayer1());
        } else {
            playerMove.setPlayer(simplePlay.getPlayer2());
        }

        playerMove.setPlayerMoveStatus(clientMessage.getPlayerMoveStatus());
        playerMove.setDirection(clientMessage.getDirection());
        simplePlay.handlePlayerMove(playerMove);
    }

    public void startGame(Player player1, Player player2) {
        System.out.println("Service started on " + gameId + " channel");
        ExecutorService executorService = new ForkJoinPool(20);
        simplePlay = new SimplePlay(executorService, player1, player2);
        tick = System.currentTimeMillis();
        executorService.submit(simplePlay);
        while (simplePlay.getPlayState().getPlayStatus() != PlayStatus.BREAK) {
            sender.send(gameId, getDemoMessage());
            tick += Puck.WAIT_TIME;
            LockSupport.parkNanos(Puck.WAIT_TIME * 1_000_000);
        }
        if (simplePlay.getPlayState().getPlayStatus() == PlayStatus.BREAK) {
            sender.send(gameId, getDemoMessage());
        }
        executorService.shutdown();
    }

    public Puck getPuckPosition() {
        return simplePlay.getPlayState().getPuck();
    }

    public PlayStatus getPlayStatus() {
        return simplePlay.getPlayState().getPlayStatus();
    }

    /**
     * Need for bot
     * I think, that bot always must be on TOP
     * @return
     */
    public Player getPlayer2() {
        return simplePlay.getPlayState().getPlayer2();
    }
}
