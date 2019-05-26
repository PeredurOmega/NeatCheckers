package objects;

import enums.Type;

import java.util.ArrayList;

public class King extends Piece {

    /**
     * Builds a king piece.
     * @param vX X position of this king piece.
     * @param vY Y position of this king piece.
     * @param vIsFromWhiteTeam Boolean representing the team of the king piece (true if white, false otherwise).
     */
    King(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY,  Type.KING,vIsFromWhiteTeam);
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
                if(toPosition.getY() < this.getY() && toPosition.getX() < this.getX()){ // Bottom Left
                    for (int x = this.getX(), y = this.getY(); x > toPosition.getX() && y > toPosition.getY(); x--, y--) {
                        temporaryBoard.eat(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() > this.getX()){ // Top Right
                    for (int x = this.getX(), y = this.getY(); x < toPosition.getX() && y < toPosition.getY(); x++, y++) {
                        temporaryBoard.eat(new Position(x, y));
                    }
                }else if(toPosition.getY() < this.getY() && toPosition.getX() > this.getX()){ // Bottom Right
                    for (int x = this.getX(), y = this.getY(); x < toPosition.getX() && y > toPosition.getY(); x++, y--) {
                        temporaryBoard.eat(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() < this.getX()){ // Top Left
                    for (int x = this.getX(), y = this.getY(); x > toPosition.getX() && y < toPosition.getY(); x--, y++) {
                        temporaryBoard.eat(new Position(x, y));
                    }
                }
                temporaryBoard.eat(this.getPosition());
                King king = new King(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(king);
                ArrayList<Position> nextEatingPosition = king.getAllEatingMovements(temporaryBoard);
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
                ArrayList<Position> tempAtePositionsToSend = new ArrayList<>();
                if(toPosition.getY() < this.getY() && toPosition.getX() < this.getX()){ // Bottom Left
                    for (int x = this.getX() - 1, y = this.getY() - 1; x > toPosition.getX() && y > toPosition.getY(); x--, y--) {
                        temporaryBoard.eat(new Position(x, y));
                        tempAtePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() > this.getX()){ // Top Right
                    for (int x = this.getX() + 1, y = this.getY() + 1; x < toPosition.getX() && y < toPosition.getY(); x++, y++) {
                        temporaryBoard.eat(new Position(x, y));
                        tempAtePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() < this.getY() && toPosition.getX() > this.getX()){ // Bottom Right
                    for (int x = this.getX() + 1, y = this.getY() - 1; x < toPosition.getX() && y > toPosition.getY(); x++, y--) {
                        temporaryBoard.eat(new Position(x, y));
                        tempAtePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() < this.getX()){ // Top Left
                    for (int x = this.getX() - 1, y = this.getY() + 1; x > toPosition.getX() && y < toPosition.getY(); x--, y++) {
                        temporaryBoard.eat(new Position(x, y));
                        tempAtePositionsToSend.add(new Position(x, y));
                    }
                }
                atePositionsToSend.addAll(tempAtePositionsToSend);
                temporaryBoard.eat(this.getPosition());
                King king = new King(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(king);
                ArrayList<Position> nextEatingPosition = king.getAtePositions(temporaryBoard, selectedPosition);
                if(nextEatingPosition.size() > 0){
                    atePositionsToSend.addAll(nextEatingPosition);
                }else if(selectedPosition.getX() != -1 && !toPosition.equals(selectedPosition)){
                    for(Position positionToRemove: tempAtePositionsToSend){
                        atePositionsToSend.remove(positionToRemove);
                    }
                }
            }
            return atePositionsToSend;
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * Returns all non-eating movements on the bottom right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the bottom right axis.
     */
    private ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<>();
        int x = this.getX() + 1, y = this.getY() + 1;
        while ((x < 10 && y < 10) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x++;
            y++;
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the top right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the top right axis.
     */
    private ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<>();
        int x = this.getX() - 1, y = this.getY() + 1;
        while ((x >= 0 && y < 10) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x--;
            y++;
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the top left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the top left axis.
     */
    private ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<>();
        int x = this.getX() - 1, y = this.getY() - 1;
        while ((x >= 0 && y >= 0) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x--;
            y--;
        }
        return movements;
    }

    /**
     * Returns all non-eating movements on the bottom left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all non-eating movements on the bottom left axis.
     */
    private ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<>();
        int x = this.getX() + 1, y = this.getY() - 1;
        while ((x < 10 && y >= 0) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x++;
            y--;
        }
        return movements;
    }

    /**
     * Returns all eating movements on the bottom right axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the bottom right axis.
     */
    private ArrayList<Position> getEatingMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        int xFromEating = 10, yFromEating = 10;
        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean pieceTeam = !this.isFromTeamWhite();
        while ((x < 10 && y < 10) && !(previousCellOccupied && currentCellOccupied) && pieceTeam != this.isFromTeamWhite()) {
            x++;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if(currentCellOccupied){
                pieceTeam = currentBoard.getSpecificPiece(new Position(x, y)).isFromTeamWhite();
            }
            if(currentCellOccupied && xFromEating == 10){
                xFromEating = x;
                yFromEating = y;
            }
        }
        ArrayList<Position> movements = new ArrayList<>();
        while (x > xFromEating && y > yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x--;
            y--;
        }
        return movements;
    }


    /**
     * Returns all eating movements on the bottom left axis from the current board.
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> with all eating movements on the bottom left axis.
     */
    private ArrayList<Position> getEatingMovementsToBottomLeft(Board currentBoard) {
        int x = this.getX(), y = this.getY();
        int xFromEating = 10, yFromEating = 0;
        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean pieceTeam = !this.isFromTeamWhite();
        while ((x < 10 && y >= 0) && !(previousCellOccupied && currentCellOccupied) && pieceTeam != this.isFromTeamWhite()) {
            x++;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if(currentCellOccupied){
                pieceTeam = currentBoard.getSpecificPiece(new Position(x, y)).isFromTeamWhite();
            }
            if(currentCellOccupied && xFromEating == 10){
                xFromEating = x;
                yFromEating = y;
            }
        }
        ArrayList<Position> movements = new ArrayList<>();
        while (x > xFromEating && y < yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x--;
            y++;
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
        int xFromEating = 0, yFromEating = 0;
        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean pieceTeam = !this.isFromTeamWhite();
        while ((x >=0 && y >= 0) && !(previousCellOccupied && currentCellOccupied) && pieceTeam != this.isFromTeamWhite()) {
            x--;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if(currentCellOccupied){
                pieceTeam = currentBoard.getSpecificPiece(new Position(x, y)).isFromTeamWhite();
            }
            if(currentCellOccupied && xFromEating == 0){
                xFromEating = x;
                yFromEating = y;
            }
        }
        ArrayList<Position> movements = new ArrayList<>();
        while (x < xFromEating && y < yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x++;
            y++;
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
        int xFromEating = 0, yFromEating = 10;
        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean pieceTeam = !this.isFromTeamWhite();
        while ((x >=0 && y < 10) && !(previousCellOccupied && currentCellOccupied) && pieceTeam != this.isFromTeamWhite()) {
            x--;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if(currentCellOccupied){
                pieceTeam = currentBoard.getSpecificPiece(new Position(x, y)).isFromTeamWhite();
            }
            if(currentCellOccupied && xFromEating == 0){
                xFromEating = x;
                yFromEating = y;
            }
        }
        ArrayList<Position> movements = new ArrayList<>();
        while (x < xFromEating && y > yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x++;
            y--;
        }
        return movements;
    }
}
