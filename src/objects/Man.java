package objects;

import enums.Type;

import java.util.ArrayList;

public class Man extends Piece {

    /**
     * Builds a man piece.
     * @param vX X position of this man piece.
     * @param vY Y position of this man piece.
     * @param vIsFromWhiteTeam Boolean representing the team of the man piece (true if white, false otherwise).
     */
    Man(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY, Type.MAN, vIsFromWhiteTeam);
    }

    @Override
    public ArrayList<Position> getAvailableMovements(Board currentBoard) {
        ArrayList<Position> positions = new ArrayList<>();
        positions.addAll(getMovementsToBottomLeft(currentBoard));
        positions.addAll(getMovementsToBottomRight(currentBoard));
        positions.addAll(getMovementsToTopLeft(currentBoard));
        positions.addAll(getMovementsToTopRight(currentBoard));

        ArrayList<Position> eatingPositions = getAllEatingMovements(currentBoard);

        if(eatingPositions.size() > 0){
            return eatingPositions;
        }else{
            return positions;
        }
    }

    @Override
    public ArrayList<Position> getAllEatingMovements(Board board){
        ArrayList<Position> eatingPositions = new ArrayList<>();
        eatingPositions.addAll(getEatingMovementsToBottomLeft(board));
        eatingPositions.addAll(getEatingMovementsToBottomRight(board));
        eatingPositions.addAll(getEatingMovementsToTopLeft(board));
        eatingPositions.addAll(getEatingMovementsToTopRight(board));
        ArrayList<Position> eatingPositionsToSend = new ArrayList<>();
        if(eatingPositions.size() > 0){
            for(Position toPosition: eatingPositions){
                Board temporaryBoard = new Board(board);
                Position eatingPiecePosition = new Position((this.getX() + toPosition.getX())/2, (this.getY() + toPosition.getY())/2);
                temporaryBoard.eat(eatingPiecePosition);
                temporaryBoard.eat(this.getPosition());
                Man man = new Man(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(man);
                ArrayList<Position> nextEatingPosition = man.getAllEatingMovements(temporaryBoard);
                if(nextEatingPosition.size() > 0){
                    eatingPositionsToSend.addAll(nextEatingPosition);
                }else{
                    eatingPositionsToSend.add(toPosition);
                }
            }
            return eatingPositionsToSend;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Position> getAtePositions(Board currentBoard, Position selectedPosition){
        ArrayList<Position> eatingPositions = new ArrayList<>();
        eatingPositions.addAll(getEatingMovementsToBottomLeft(currentBoard));
        eatingPositions.addAll(getEatingMovementsToBottomRight(currentBoard));
        eatingPositions.addAll(getEatingMovementsToTopLeft(currentBoard));
        eatingPositions.addAll(getEatingMovementsToTopRight(currentBoard));
        ArrayList<Position> atePositionsToSend = new ArrayList<>();
        if(eatingPositions.size() > 0){
            for(Position toPosition: eatingPositions){
                Board temporaryBoard = new Board(currentBoard);
                Position eatenPiecePosition = new Position((this.getX() + toPosition.getX())/2, (this.getY() + toPosition.getY())/2);
                atePositionsToSend.add(eatenPiecePosition);
                temporaryBoard.eat(eatenPiecePosition);
                temporaryBoard.eat(this.getPosition());
                Man man = new Man(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(man);
                ArrayList<Position> nextEatingPosition = man.getAtePositions(temporaryBoard, selectedPosition);
                if(nextEatingPosition.size() > 0){
                    atePositionsToSend.addAll(nextEatingPosition);
                }else if(selectedPosition.getX() != -1 && !toPosition.equals(selectedPosition)){
                    atePositionsToSend.remove(eatenPiecePosition);
                }
            }
            return atePositionsToSend;
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * Returns all eating movements on the bottom right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the bottom right axis.
     */
    private ArrayList<Position> getEatingMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if ((currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).isFromTeamWhite()&&
                currentBoard.getSpecificPiece(new Position(x + 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x +2, y + 2));
        }
        return movements;
    }

    /**
     * Returns all eating movements on the top right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the top right axis.
     */
    private ArrayList<Position> getEatingMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if ((currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.MAN ||
                 currentBoard.getSpecificPiece(new Position(x-1, y+1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y + 2));
        }
        return movements;
    }

    /**
     * Returns all eating movements on the top left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the top left axis.
     */
    private ArrayList<Position> getEatingMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if((currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x-1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y - 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y - 2));
        }
        return movements;
    }

    /**
     * Returns all eating movements on the bottom left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the bottom left axis.
     */
    private ArrayList<Position> getEatingMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if((currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x+1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x + 2, y - 2)).getType() == Type.EMPTY) {
            movements.add(new Position(x + 2, y - 2));
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the bottom right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the bottom right axis.
     */
    private ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()) {

            movements.add(new Position(x + 1, y + 1));
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the top right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the top right axis.
     */
    private ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY && this.isFromTeamWhite()) {
            movements.add(new Position(x - 1, y + 1));

        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the top left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the top left axis.
     */
    private ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY && this.isFromTeamWhite()){
            movements.add(new Position(x - 1, y - 1));
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the bottom left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the bottom left axis.
     */
    private ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<>();
        if(currentBoard.getSpecificPiece(new Position(x +1, y - 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()){
            movements.add(new Position(x + 1, y - 1));
        }
        return movements;
    }

    @Override
    public boolean isCoronationTime(){
        return (this.getX() == 0 && this.isFromTeamWhite() || this.getX() == 9 && !this.isFromTeamWhite());
    }
}
