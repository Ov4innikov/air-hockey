package ru.airhockey.playingarea;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.GameResult;
import ru.airhockey.playingarea.model.Player;
import ru.airhockey.playingarea.model.Puck;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class VisualModel extends JFrame {

    private static VisualModel gameWindow;
    private static final Logger logger = LoggerFactory.getLogger(VisualModel.class);

    private static Puck puck;
    private static SimplePlay simplePlay;
    private static Player player1 = new Player();
    private static Player player2 = new Player();

    public static void main(String[] args) throws Exception {
        gameWindow = new VisualModel();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(GameTask.WIDTH_OF_PLAYING_AREA, GameTask.HEIGHT_OF_PLAYING_AREA);
        gameWindow.setResizable(false);
        simplePlay = new SimplePlay(new ForkJoinPool(20), player1, player2);
        GameField gameField = new GameField();
        ExecutorService executorService = new ForkJoinPool(3);
        Future<GameResult> gameResult = executorService.submit(simplePlay);
        logger.info("Simple play started!");
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics g){
        puck = simplePlay.getPlayState().getPuck();
        drawCircle(g, puck.getX(), puck.getY(), puck.RADIUS);
        drawCircle(g, player1.getX(), player1.getY(), player1.RADIUS);
        drawCircle(g, player2.getX(), player2.getY(), player2.RADIUS);
    }

    private static void drawCircle(Graphics g, float x, float y, float r){
        g.fillOval((int) (x - r),(int) (y - r), (int) r*2,(int) r*2);
        //g.fillOval((int) x, (int) y, (int) r*2,(int) r*2);
    }

    private static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }

}