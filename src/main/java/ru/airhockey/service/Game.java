package ru.airhockey.service;

import lombok.Getter;
import lombok.Setter;
import ru.airhockey.playingarea.Play;
import ru.airhockey.playingarea.SimplePlay;
import ru.airhockey.playingarea.model.PlayStatus;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.PlayerMove;
import ru.airhockey.playingarea.model.Puck;
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

    public Game() {
        demoMassageList = new ArrayList<>();
    }

    public Game(ISender sender, String gameId) {
        this();
        this.sender = sender;
        this.gameId = gameId;
    }

    private DemoMassage getDemoMessage(Play play) {
        SimplePlay simplePlay = (SimplePlay) play;
        DemoMassage demoMassage = new DemoMassage(tick, simplePlay.getPlayer1(), simplePlay.getPlayer2(), simplePlay.getPuck(), simplePlay.getPlayStatus());
        demoMassageList.add(demoMassage);
        return demoMassage;
    }

    public void setPlayerPosition(IMessage message) {
        ClientMessage clientMessage = (ClientMessage) message;

        PlayerMove playerMove = new PlayerMove(clientMessage.getPlayer(), clientMessage.getPlayerMoveStatus(), clientMessage.getDirection());
        simplePlay.handlePlayerMove(playerMove);
    }

    public void startGame(Player player1, Player player2) throws Exception {
        System.out.println("Service started on " + gameId + " channel");
        ExecutorService executorService = new ForkJoinPool(20);
        simplePlay = new SimplePlay(executorService, player1, player2, sender, gameId);
        tick = System.currentTimeMillis();
        executorService.submit(simplePlay);
        while (simplePlay.getPlayState().getPlayStatus() == PlayStatus.BREAK) {
            sender.send(gameId, getDemoMessage(simplePlay));
            LockSupport.parkNanos(Puck.WAIT_TIME * 1_000_000);
        }
    }

}
