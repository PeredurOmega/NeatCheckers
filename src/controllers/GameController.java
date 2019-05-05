package controllers;

import enums.Type;
import graphics.Display;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

public class GameController implements GameListener {
    Display displayer;
    Board board;

    @Override
    public void onClick(Position position) {
        Piece piece = board.getSpecificPiece(position);
        if(piece.getType() == Type.MAN || piece.getType() == Type.KING){
            displayer.showPossibilities(piece.getAvailableMovements(board));
        }

        System.out.println("Clicked : " + position);
        System.out.println("Clicked : " + piece.getType());
    }

    public void startGame(){
        board = new Board();
        displayer = new Display(board, GameController.this);
    }
}
