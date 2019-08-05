package ru.airhockey.playingarea;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.airhockey.playingarea.model.*;
import ru.airhockey.playingarea.util.PhysicsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private static Player player;

    public static void main(String[] args) throws Exception {
        gameWindow = new VisualModel();

        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(GameTask.HEIGHT_OF_PLAYING_AREA, GameTask.WIDTH_OF_PLAYING_AREA + 30);
        gameWindow.setResizable(false);
        simplePlay = new SimplePlay(new ForkJoinPool(20), player1, player2);
        MouseListener winListener = new MyMouseListener(simplePlay);
        gameWindow.addMouseListener(winListener);
        GameField gameField = new GameField();
        ExecutorService executorService = new ForkJoinPool(3);
        Future<GameResult> gameResult = executorService.submit(simplePlay);
        logger.info("Simple play started!");
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics g){
        PlayState playState = simplePlay.getPlayState();
        if (playState.getPlayStatus() == PlayStatus.PUCK) {
            logger.info("Puck! {}:{}", (int) player1.getPlayAccount(), (int) player2.getPlayAccount());
        }
        puck = playState.getPuck();
        drawCircle(g, puck.getY(), puck.getX(), puck.RADIUS);
        drawCircle(g, player1.getY(), player1.getX(), player1.RADIUS);
        drawCircle(g, player2.getY(), player2.getX(), player2.RADIUS);
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

    private static class MyMouseListener implements MouseListener {

        private Play play;

        public MyMouseListener(Play play) {
            this.play = play;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            float x = e.getY();
            float y = e.getX();
            if (y > GameTask.HEIGHT_OF_PLAYING_AREA/2) {
                player = player2;
            } else {
                player = player1;
            }
            logger.info("x = {}, y = {}", x, y);
            float corner = PhysicsUtil.getCorner(x - player.getX(), y - player.getY());
            PlayerMove playerMove = new PlayerMove(player, PlayerMoveStatus.YES, corner);
            play.handlePlayerMove(playerMove);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            PlayerMove playerMove = new PlayerMove(player, PlayerMoveStatus.NO, 0);
            play.handlePlayerMove(playerMove);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}