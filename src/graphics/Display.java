package graphics;

import controllers.GameContent;
import controllers.GameController;
import interfaces.GameListener;
import objects.Board;
import objects.Man;
import objects.Piece;
import objects.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Display extends JFrame {

    private GameContent gameContent;
    private CardLayout card;

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

    public void cleanPossibilities() {
        gameContent.cleanPossibilities();
    }

    public Boolean movePiece(Position fromPosition, Position toPosition, Piece piece, ArrayList<Position> eatenPositions) {
        return gameContent.movePiece(fromPosition, toPosition, piece, eatenPositions);
    }

    public void displayMenu(){

    }
}
