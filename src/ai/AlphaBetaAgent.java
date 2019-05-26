package ai;

import enums.Type;
import objects.Board;
import objects.Piece;
import objects.Position;

import java.util.ArrayList;

public class AlphaBetaAgent {

    private boolean searchCutoff = false;
    private final long TIME_LIMIT_MILLIS;
    private int cases = 0;
    private int b = 0;

    /**
     * Builds a piece.
     * @param TIME_LIMIT_IN_MILLIS_FOR_EACH_INITIAL_MOVE Maximum time in milliseconds allocated to current agent to cumpute.
     */
    public AlphaBetaAgent(long TIME_LIMIT_IN_MILLIS_FOR_EACH_INITIAL_MOVE){
        this.TIME_LIMIT_MILLIS = TIME_LIMIT_IN_MILLIS_FOR_EACH_INITIAL_MOVE;
    }

    /**
     * Evaluation method used for miniMax.
     * @param board Board to evaluate.
     * @return Integer value representing the score of the current board (for the team to play).
     */
    private int evalState(Board board) {
        int value = 0;
        for(int x = 0; x < 10; x++) {
            for(int y = (x+1)%2; y < 10; y+= 2) {
                Piece piece = board.getSpecificPiece(new Position(x, y));
                if(!piece.isFromTeamWhite()) {
                    value += getValue(piece);
                } else {
                    value -= getValue(piece);
                }
            }
        }
        return value;
    }

    /**
     * Returns the value of piece according to a variety of parameters.
     * @param piece Type of the piece.
     * @return Integer value representing the value of the piece.
     */
    private int getValue(Piece piece) {
        switch (piece.getType()){
            case KING: return 20;
            case MAN:
                if(piece.getY() == 0 || piece.getY() == 9 || piece.getX() == 0 || piece.getX() == 9){
                    return 10;
                }else if(piece.getY() == 1 || piece.getY() == 8 || piece.getX() == 1 || piece.getX() == 8){
                    return 9;
                }else if(piece.getY() == 2 || piece.getY() == 7 || piece.getX() == 2 || piece.getX() == 7){
                    return 8;
                }else if(piece.getY() == 3 || piece.getY() == 6 || piece.getX() == 3 || piece.getX() == 6){
                    return 7;
                }else{
                    return 6;
                }
            default: return 0;
        }
    }

    /**
     * Returns the best move for the AlphaBetaAgent.
     * @param currentBoard Actual state of the board.
     * @return An array of two positions with [0] the fromPosition and [1] the toPosition.
     */
    public Position[] play(Board currentBoard) {
        long firstTime = System.currentTimeMillis();
        int initialValue = evalState(currentBoard);
        int bestValue = Integer.MIN_VALUE;
        boolean couldEat = currentBoard.couldEat();

        //The size is wide enough to be able to store all equal possible moves (the max possible moves should be 218)
        ArrayList<Position> bestTos = new ArrayList<>();
        ArrayList<Piece> bestPieces = new ArrayList<>();
        int p = 0; //To current index of bestTo and bestPiece

        System.out.println("INITIAL SCORE " + initialValue);

        for(int x = 0; x < 10; x++) {
            for(int y = (x+1)%2; y < 10; y+= 2) {
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
                    //Deep copy of the board
                    Board temporaryBoard = new Board(currentBoard);

                    //Simulate eating
                    if(couldEat){
                        for(Position eatenPosition: piece.getAtePositions(temporaryBoard, targetedMove)){
                            temporaryBoard.eat(eatenPosition);
                        }
                    }

                    //Simulate movement
                    temporaryBoard.move(piece.getPosition(), targetedMove.getPosition());

                    //Change of Player
                    temporaryBoard.rotatePlayer();

                    //Recursive tree
                    int value = progressiveDeepeningSearch(temporaryBoard);

                    //Is it the best one ?
                    if(value > bestValue) {
                        bestValue = value;
                        //Reset bestTo and bestPieces
                        bestTos.clear();
                        bestPieces.clear();
                        p = 0;
                        bestTos.add(targetedMove);
                        bestPieces.add(piece);
                    } else if (value == bestValue) { //Just another good one ? Add it
                        bestTos.add(targetedMove);
                        bestPieces.add(piece);
                    }
                }
            }
        }
        // Select random equal best moves to create opportunities and avoid repetitive playing.
        int selectedPlay = (int) (Math.random() * p);
        Position bestTo = bestTos.get(selectedPlay);
        Piece bestPiece = bestPieces.get(selectedPlay);
        System.out.println(bestTo);

        if(bestTo != null && bestPiece != null) {
            System.out.println("Best move " + bestPiece.getType() +  " from" + bestPiece.isFromTeamWhite() + " " + bestPiece.getPosition() + " to " + bestTo + " with score : " + bestValue + " and done " + cases + " cases ");
            System.out.println("Alpha beta triggered " + b);
            long timeDif = System.currentTimeMillis() - firstTime;
            System.out.println("Time taken = " + timeDif + "ms");
            System.out.println("__________________________");
            return new Position[] {bestPiece.getPosition(), bestTo};
        }else{
            return null;
        }
    }

    /**
     * Returns the evaluated value with miniMax + alpha-beta layer.
     * @param currentBoard Actual state of the board.
     * @return Integer value representing the limit of the tree leaf.
     */
    private int miniMax(Board currentBoard, int depth, int alpha, int beta, boolean maximize, long startTime, long timeLimit) {
        cases++;

        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - startTime);

        if (elapsedTime >= timeLimit) {
            searchCutoff = true;
        }

        if(depth == 0 || searchCutoff) {
            return evalState(currentBoard);
        }

        boolean couldEat = currentBoard.couldEat();
        int limitValue = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(int x = 0; x < 10; x++) {
            for(int y = (x+1)%2; y < 10; y+= 2) {
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
                    //Deep copy of the board
                    Board temporaryBoard = new Board(currentBoard);

                    //Simulate eating
                    if(couldEat){
                        for(Position eatenPosition: piece.getAtePositions(temporaryBoard, targetedMove)){
                            temporaryBoard.eat(eatenPosition);
                        }
                    }

                    //Simulate movement
                    temporaryBoard.move(piece.getPosition(), targetedMove.getPosition());

                    //Change of Player
                    temporaryBoard.rotatePlayer();

                    //Recursive tree
                    int value = miniMax(temporaryBoard, (depth - 1), alpha, beta, !maximize, startTime, timeLimit);

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

    /**
     * Returns the evaluated value with miniMax + alpha-beta layer + insurance policy with progressive deepening.
     * @param currentBoard Actual state of the board.
     * @return Integer value representing the limit of the tree leaf.
     */
    private int progressiveDeepeningSearch(Board currentBoard) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TIME_LIMIT_MILLIS;
        int value = 0;
        int depth = 0;
        searchCutoff = false;

        while (System.currentTimeMillis() < endTime) {
            int searchResult = miniMax(currentBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false, System.currentTimeMillis(), endTime - System.currentTimeMillis());
            if (!searchCutoff) {
                value = searchResult;
            }
            depth++;
        }

        return value;
    }

}