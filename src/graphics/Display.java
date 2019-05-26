package graphics;

import controllers.GameContent;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

import javax.swing.*;
import java.util.ArrayList;

public class Display extends JFrame {

    private final GameContent gameContent;

    /**
     * Builds a display element.
     * @param b Current board.
     * @param gm GameListener to use.
     */
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

    /**
     * Displays all the possible moves.
     * @param availableMovements ArrayList<Position> containing all the possible moves.
     */
    public void showPossibilities(ArrayList<Position> availableMovements) {
        gameContent.drawAvailablePositions(availableMovements);
    }

    /**
     * Cleans all the possibilities.
     */
    public void cleanPossibilities() {
        gameContent.cleanPossibilities();
    }

    /**
     * Moves a piece graphically.
     * @param fromPosition Current position of the piece to move.
     * @param toPosition Position to move to.
     * @param piece Piece to move.
     * @param eatenPositions ArrayList<Position> containing positions eaten during the move.
     * @param aiMove Boolean true if the move is done by an AI, false otherwise.
     * @return Boolean true if the move succeeded, false otherwise.
     */
    public Boolean movePiece(Position fromPosition, Position toPosition, Piece piece, ArrayList<Position> eatenPositions, boolean aiMove) {
        return gameContent.movePiece(fromPosition, toPosition, piece, eatenPositions, aiMove);
    }
}
