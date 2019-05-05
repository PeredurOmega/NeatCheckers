package graphics;

import controllers.GameContent;
import controllers.GameController;
import interfaces.GameListener;
import objects.Board;
import objects.Man;
import objects.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Display extends JFrame {

    private GameContent gameContent;

    public Display(Board b, GameListener gm){
        super("Checkers");
        gameContent = new GameContent(b, gm);
        gameContent.addMouseListener(gameContent);
        add(gameContent);
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showPossibilities(ArrayList<Position> availableMovements) {
        gameContent.drawAvailablePositions(availableMovements);
    }
}
