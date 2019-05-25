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
        if(piece.isCoronationTime()){
            board.promote(piece);
            System.out.println("Done");
        }

        if (showAvailableMode) {
            Piece selectedPiece = this.board.getSpecificPiece(fromPosition);
            showAvailableMode = !displayer.movePiece(fromPosition, toPosition, selectedPiece, selectedPiece.getAtePositions(this.board));
            if(!showAvailableMode)
                this.board.setTeamWhiteTurn(!this.board.isTeamWhiteTurn());
            if (showAvailableMode) {
                displayer.cleanPossibilities();
                showAvailableMode = false;
            }
        } else {
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
