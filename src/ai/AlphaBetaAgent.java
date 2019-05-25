package ai;

import enums.Type;
import objects.Board;
import objects.Piece;
import objects.Position;

import java.util.ArrayList;

public class AlphaBetaAgent {

    /*
     * Return an integer giving a perspective of the current board for a given team
     */
    private int evalState(Board board) {
        int value = 0;
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                Piece piece = board.getSpecificPiece(new Position(x, y));
                if(piece.isFromTeamWhite() == board.isTeamWhiteTurn()) {
                    value += getValue(piece.getType());
                } else {
                    value -= getValue(piece.getType());
                }
            }
        }
        return value;
    }

    private int getValue(Type type) {
        switch (type){
            case EMPTY: return 0;
            case OUT: return 0;
            case KING: return 5;
            case MAN: return 1;
            default: return 0;
        }
    }

    private int cases = 0;
    private int b = 0;

    //Min max tree implementation
    public Position[] play(Board currentBoard) {
        long firstTime = System.currentTimeMillis();
        int initialValue = evalState(currentBoard);
        int bestValue = Integer.MIN_VALUE;
        boolean couldEat = currentBoard.couldEat();

        //The size is wide enough to be able to store all equal possible moves (the max possible moves should be 218)
        ArrayList<Position> bestTos = new ArrayList<>();
        ArrayList<Piece> bestPieces = new ArrayList<>();
        int p = 0; //To current index of bestTo and bestPiece

        int depth = 10;

        System.out.println("INITIAL SCORE " + initialValue);

        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                Piece piece = currentBoard.getSpecificPiece(new Position(x, y));
                if(piece.getType() == Type.OUT || piece.getType() == Type.EMPTY){
                    continue;
                }
                if(piece.isFromTeamWhite() != currentBoard.isTeamWhiteTurn()){
                    continue;
                }
                ArrayList<Position> availableMovements = new ArrayList<>();
                ArrayList<Position> eatingMovements = piece.getAllEatingMovements(currentBoard);
                if(eatingMovements.size() == 0 && couldEat){
                    continue;
                }else if(eatingMovements.size() == 0){
                    availableMovements.addAll(piece.getAvailableMovements(currentBoard));
                }else{
                    availableMovements.addAll(eatingMovements);
                }

                //Iterate all possible moves
                for(Position targetedMove: availableMovements) {
                    Board temporaryBoard = new Board(currentBoard);
                    //Simulate movement
                    temporaryBoard.move(piece.getPosition(), targetedMove.getPosition());

                    //Change of Player
                    temporaryBoard.rotatePlayer();

                    //Recursive tree
                    int value = miniMax(temporaryBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

                    //Is it the best one ?
                    if(value > bestValue) {
                        bestValue = value;
                        //Reset bestTo and bestPieces
                        bestTos.clear();
                        bestPieces.clear();
                        p = 0;
                        bestTos.add(p, targetedMove);
                        bestPieces.add(p++, piece);
                    } else if (value == bestValue) { //Just another good one ? Add it
                        bestTos.add(p, targetedMove);
                        bestPieces.add(p++, piece);
                    }
                }
            }
        }
        // Select random equal best moves to create opportunities and avoid repetitive playing.
        int selectedPlay = (int) (Math.random() * p);
        Position bestTo = bestTos.get(selectedPlay);
        Piece bestPiece = bestPieces.get(selectedPlay);

        if(bestTo != null && bestPiece != null) {
            System.out.println("Best move " + bestPiece.getType() +  " from" + bestPiece.isFromTeamWhite() + " " + bestPiece.getPosition() + " to " + bestTo + " with score : " + bestValue + " and done " + cases + " cases ");
            //currentBoard.move(bestPiece.getPosition(), bestTo);
            System.out.println("Alpha beta triggered " + b);
            long timeDif = System.currentTimeMillis() - firstTime;
            System.out.println("Time taken = " + timeDif + "ms");
            System.out.println("__________________________");
            return new Position[] {bestPiece.getPosition(), bestTo};
        }else{
            return null;
        }
    }
    private int miniMax(Board currentBoard, int depth, int alpha, int beta, boolean maximize) {
        cases++;
        boolean couldEat = currentBoard.couldEat();
        if(depth == 0) {
            return evalState(currentBoard);
        }

        int limitValue = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                Piece piece = currentBoard.getSpecificPiece(new Position(x, y));
                if(piece.getType() == Type.OUT || piece.getType() == Type.EMPTY){
                    continue;
                }
                if(piece.isFromTeamWhite() != currentBoard.isTeamWhiteTurn()){
                    continue;
                }
                ArrayList<Position> availableMovements = new ArrayList<>();
                ArrayList<Position> eatingMovements = piece.getAllEatingMovements(currentBoard);
                if(eatingMovements.size() == 0 && couldEat){
                    continue;
                }else if(eatingMovements.size() == 0){
                    availableMovements.addAll(piece.getAvailableMovements(currentBoard));
                }else{
                    availableMovements.addAll(eatingMovements);
                }

                //Iterate all possible moves
                for(Position targetedMove: availableMovements) {
                    Board temporaryBoard = new Board(currentBoard);
                    //Simulate movement
                    temporaryBoard.move(piece.getPosition(), targetedMove.getPosition());

                    //Change of Player
                    temporaryBoard.rotatePlayer();

                    //Recursive tree
                    int value = miniMax(temporaryBoard, (depth - 1), alpha, beta, !maximize);

                    if(!maximize && value <= limitValue) {
                        limitValue = value;
                    } else if(maximize && value >= limitValue) {
                        limitValue = value;
                    }

                    //Update alpha and beta
                    if(maximize) {
                        alpha = Math.max(alpha, limitValue);
                    } else {
                        beta = Math.min(beta, limitValue);
                    }
                    if(beta <= alpha) {
                        b++;
                        return limitValue;
                    }
                }
            }
        }
        return limitValue;
    }

}