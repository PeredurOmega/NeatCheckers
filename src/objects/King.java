package objects;

import enums.Type;

import java.util.ArrayList;

public class King extends Piece {

    King(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY,  Type.KING,vIsFromWhiteTeam);
    }

    @Override
    public ArrayList<Position> getAvailableMovements(Board currentBoard) {
        ArrayList<Position> positions = new ArrayList<Position>();
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

    private ArrayList<Position> getAllEatingMovements(Board board){

        ArrayList<Position> eatingPositions = new ArrayList<Position>();
        eatingPositions.addAll(getEatingMovementsToBottomLeft(board));
        eatingPositions.addAll(getEatingMovementsToBottomRight(board));
        eatingPositions.addAll(getEatingMovementsToTopLeft(board));
        eatingPositions.addAll(getEatingMovementsToTopRight(board));
        ArrayList<Position> eatingPositionsToSend = new ArrayList<Position>();
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
            return new ArrayList<Position>();
        }
    }

    @Override
    public ArrayList<Position> getAtePositions(Board currentBoard){
        ArrayList<Position> eatingPositions = new ArrayList<Position>();
        eatingPositions.addAll(getEatingMovementsToBottomLeft(currentBoard));
        eatingPositions.addAll(getEatingMovementsToBottomRight(currentBoard));
        eatingPositions.addAll(getEatingMovementsToTopLeft(currentBoard));
        eatingPositions.addAll(getEatingMovementsToTopRight(currentBoard));
        ArrayList<Position> atePositionsToSend = new ArrayList<Position>();
        if(eatingPositions.size() > 0){
            for(Position toPosition: eatingPositions){
                Board temporaryBoard = new Board(currentBoard);
                if(toPosition.getY() < this.getY() && toPosition.getX() < this.getX()){ // Bottom Left
                    for (int x = this.getX() - 1, y = this.getY() - 1; x > toPosition.getX() && y > toPosition.getY(); x--, y--) {
                        temporaryBoard.eat(new Position(x, y));
                        atePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() > this.getX()){ // Top Right
                    for (int x = this.getX() + 1, y = this.getY() + 1; x < toPosition.getX() && y < toPosition.getY(); x++, y++) {
                        temporaryBoard.eat(new Position(x, y));
                        atePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() < this.getY() && toPosition.getX() > this.getX()){ // Bottom Right
                    for (int x = this.getX() + 1, y = this.getY() - 1; x < toPosition.getX() && y > toPosition.getY(); x++, y--) {
                        temporaryBoard.eat(new Position(x, y));
                        atePositionsToSend.add(new Position(x, y));
                    }
                }else if(toPosition.getY() > this.getY() && toPosition.getX() < this.getX()){ // Top Left
                    for (int x = this.getX() - 1, y = this.getY() + 1; x > toPosition.getX() && y < toPosition.getY(); x--, y++) {
                        temporaryBoard.eat(new Position(x, y));
                        atePositionsToSend.add(new Position(x, y));
                    }
                }
                temporaryBoard.eat(this.getPosition());
                King king = new King(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(king);
                ArrayList<Position> nextEatingPosition = king.getAtePositions(temporaryBoard);
                if(nextEatingPosition.size() > 0){
                    atePositionsToSend.addAll(nextEatingPosition);
                }
            }
            return atePositionsToSend;
        }else{
            return new ArrayList<Position>();
        }
    }

    private ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<Position>();
        int x = this.getX() + 1, y = this.getY() + 1;
        while ((x < 10 && y < 10) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x++;
            y++;
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<Position>();
        int x = this.getX() - 1, y = this.getY() + 1;
        while ((x >= 0 && y < 10) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x--;
            y++;
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<Position>();
        int x = this.getX() - 1, y = this.getY() - 1;
        while ((x >= 0 && y >= 0) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x--;
            y--;
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        ArrayList<Position> movements = new ArrayList<Position>();
        int x = this.getX() + 1, y = this.getY() - 1;
        while ((x < 10 && y >= 0) && currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
            movements.add(new Position(x, y));
            x++;
            y--;
        }
        return movements;
    }


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
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x > xFromEating && y > yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x--;
            y--;
        }
        return movements;
    }


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
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x > xFromEating && y < yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x--;
            y++;
        }
        return movements;
    }


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
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x < xFromEating && y < yFromEating){
            if (currentBoard.getSpecificPiece(new Position(x, y)).getType() == Type.EMPTY) {
                movements.add(new Position(x, y));
            }
            x++;
            y++;
        }
        return movements;
    }


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
        ArrayList<Position> movements = new ArrayList<Position>();
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
