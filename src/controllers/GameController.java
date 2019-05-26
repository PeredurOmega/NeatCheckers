package controllers;

import ai.AlphaBetaAgent;
import enums.AgentType;
import enums.Type;
import graphics.Display;
import graphics.Toast;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

import java.util.ArrayList;

public class GameController implements GameListener {
    private Display displayer;
    private Board board;
    private boolean showAvailableMode = false;
    private Position fromPosition;
    private boolean couldEat;

    @Override
    public void onClick(Position toPosition) {
        if (showAvailableMode) {
            Piece selectedPiece = this.board.getSpecificPiece(fromPosition);
            ArrayList<Position> eatenPositions = selectedPiece.getAtePositions(this.board, toPosition);
            if((eatenPositions.size() > 0 && couldEat) || !couldEat){
                showAvailableMode = !displayer.movePiece(fromPosition, toPosition, selectedPiece, eatenPositions, board.isAiTurn());
            }else{
                Thread thread = new Thread(){
                    public void run(){
                        new Toast("Dans cette position vous devez manger un pion.").showToastText();
                    }
                };
                thread.start();
            }
            if(!showAvailableMode) {
                this.board.rotatePlayer();
                couldEat = this.board.couldEat();
                if(this.board.getPlayer().getAgentType() == AgentType.ALPHABETA){
                    AlphaBetaAgent alphaBetaAgent = new AlphaBetaAgent();
                    Position[] positions = alphaBetaAgent.play(this.board);
                    System.out.println(positions[0] + " " +  positions[1]);
                    fromPosition = new Position(positions[0]);
                    showAvailableMode = true;
                    onClick(positions[1]);
                }
            }
            if (showAvailableMode) {
                displayer.cleanPossibilities();
                showAvailableMode = false;
            }
        } else {
            Piece piece = this.board.getSpecificPiece(toPosition);
            if (piece.getType() == Type.MAN || piece.getType() == Type.KING) {
                boolean isRightTurn = piece.isFromTeamWhite() == this.board.isTeamWhiteTurn();
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

    /**
     * Starts a new game.
     */
    public void startGame(){
        this.board = new Board();
        this.displayer = new Display(this.board, GameController.this);
    }
}
