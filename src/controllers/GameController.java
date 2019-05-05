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
    Boolean showAvailableMode = false;
    Position fromPosition;

    @Override
    public void onClick(Position toPosition) {
        Piece piece = board.getSpecificPiece(toPosition);
        System.out.println(piece.getType());
        if(showAvailableMode){
            showAvailableMode = !displayer.movePiece(fromPosition, toPosition, board.getSpecificPiece(fromPosition));
            if(showAvailableMode){
                displayer.cleanPossibilities();
                showAvailableMode = false;
            }
        }else{
            if(piece.getType() == Type.MAN || piece.getType() == Type.KING){
                fromPosition = toPosition;
                showAvailableMode = true;
                displayer.showPossibilities(piece.getAvailableMovements(board));
            }
        }
    }

    @Override
    public void moveOnBoard(Position fromPosition, Position toPosition) {
        this.board.move(fromPosition, toPosition);
    }

    public void startGame(){
        board = new Board();
        displayer = new Display(board, GameController.this);
    }
}
