package controllers;

import enums.Type;
import graphics.Display;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

public class GameController implements GameListener {
    private Display displayer;
    private Board board;
    private boolean showAvailableMode = false;
    private boolean isRightTurn;
    private Position fromPosition;

    @Override
    public void onClick(Position toPosition) {
        Piece piece = this.board.getSpecificPiece(toPosition);
        //System.out.println(piece.getType());

       /* if(piece.isCoronationTime()){
            board.promote(piece);
            System.out.println("Done");
        }*/

       /*
       if(toPosition.getY()==3){
           Piece test = board.getSpecificPiece(fromPosition); //TODO RETRACT
           System.out.println("WHITE3= " + test.isFromTeamWhite());
           System.out.println("fromPosition3= " + fromPosition.getX() + " " + fromPosition.getY());

           System.out.println("WHITE4= " + piece.isFromTeamWhite());
           System.out.println("toPosition4= " + toPosition.getX() + " " + toPosition.getY());
       }*/

        if (showAvailableMode) {
            Piece test = this.board.getSpecificPiece(fromPosition); //TODO RETRACT
            System.out.println("WHITE3= " + test.isFromTeamWhite());
            System.out.println("TYPE3= " + test.getType());
            System.out.println("fromPosition3= " + fromPosition.getX() + " " + fromPosition.getY());
            showAvailableMode = !displayer.movePiece(fromPosition, toPosition, test);
            if(!showAvailableMode)
                this.board.setTeamWhiteTurn(!this.board.isTeamWhiteTurn());
            if (showAvailableMode) {
                displayer.cleanPossibilities();
                showAvailableMode = false;
            }
        } else {
            System.out.println("WHITE3= " + piece.isFromTeamWhite());
            System.out.println("toPosition3= " + toPosition.getX() + " " + toPosition.getY());
            if (piece.getType() == Type.MAN || piece.getType() == Type.KING) {
                isRightTurn = piece.isFromTeamWhite() == this.board.isTeamWhiteTurn();
                if(isRightTurn) {
                    fromPosition = toPosition;
                    showAvailableMode = true;
                    displayer.showPossibilities(piece.getAvailableMovements(this.board));
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
        this.board = new Board();
        this.displayer = new Display(this.board, GameController.this);
    }
}
