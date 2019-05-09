package controllers;

import enums.Type;
import graphics.Display;
import interfaces.GameListener;
import javafx.geometry.Pos;
import objects.Board;
import objects.Piece;
import objects.Position;

public class GameController implements GameListener {
    Display displayer;
    Board board;
    boolean showAvailableMode = false;
    boolean isRightTurn;
    Position fromPosition;

    @Override
    public void onClick(Position toPosition) {
        Piece piece = board.getSpecificPiece(toPosition);
        System.out.println(piece.getType());
        if (showAvailableMode) {
            showAvailableMode = !displayer.movePiece(fromPosition, toPosition, board.getSpecificPiece(fromPosition));
            if(!showAvailableMode)
                board.setTeamWhiteTurn(!board.isTeamWhiteTurn());
            if (showAvailableMode) {
                displayer.cleanPossibilities();
                showAvailableMode = false;
            }
        } else {
            if (piece.getType() == Type.MAN || piece.getType() == Type.KING) {
                isRightTurn = !(piece.isFromTeamWhite()^board.isTeamWhiteTurn());
                if(isRightTurn) {
                    fromPosition = toPosition;
                    showAvailableMode = true;
                    displayer.showPossibilities(piece.getAvailableMovements(board));
                }
            }
        }
    }

    @Override
    public void moveOnBoard(Position fromPosition, Position toPosition) {
        this.board.move(fromPosition, toPosition);
    }

    @Override
    public void eatOnBoard(Position eatingPosition) {
        this.board.eat(eatingPosition);
    }

    public void startGame(){
        board = new Board();
        displayer = new Display(board, GameController.this);
    }
}
