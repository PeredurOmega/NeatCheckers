package objects;

import enums.Type;

import java.util.ArrayList;

public class King extends Piece {

    public King(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY,  Type.KING,vIsFromWhiteTeam);
    }


    @Override
    public ArrayList<Position> getAvailableMovements(Board currentBoard) {
        //TODO Use recursivity to get all movements with several components
        ArrayList<Position> positions = new ArrayList<Position>();

        positions.addAll(getMovementsToBottomLeft(currentBoard));

        positions.addAll(getMovementsToBottomRight(currentBoard));

        positions.addAll(getMovementsToTopLeft(currentBoard));

        positions.addAll(getMovementsToTopRight(currentBoard));


        return positions;
    }

    public ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while ((x < 10 && y < 10) && !(previousCellOccupied && currentCellOccupied)) {
            x++;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x > this.getX() && y > this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x--;
            y--;
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToBottomLeft(Board currentBoard) {
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while ((x < 10 && y >= 0) && !(previousCellOccupied && currentCellOccupied)) {
            x++;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x > this.getX() && y < this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x--;
            y++;
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while ((x >=0 && y >= 0) && !(previousCellOccupied && currentCellOccupied)) {
            x--;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x < this.getX() && y < this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x++;
            y++;
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while ((x >=0 && y < 10) && !(previousCellOccupied && currentCellOccupied)) {
            x--;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x < this.getX() && y > this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x++;
            y--;
        }
        return movements;
    }
}
