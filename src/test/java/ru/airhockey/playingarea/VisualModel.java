package ru.airhockey.playingarea;


import ru.airhockey.playingarea.model.Puck;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ForkJoinPool;

public class VisualModel extends JFrame {

    private static VisualModel gameWindow;

    private static Puck puck;
    private static SimplePlay simplePlay;

    public static void main(String[] args){
        gameWindow = new VisualModel();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(450, 1000);
        gameWindow.setResizable(false);
        simplePlay = new SimplePlay(new ForkJoinPool(20));
        GameField gameField = new GameField();
        simplePlay.play();
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics g){

        puck = simplePlay.getPuck();
        drawCircle(g, puck.getX(), puck.getY(), puck.RADIUS);
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