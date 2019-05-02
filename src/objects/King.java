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
        while((x < 10 && y < 10) && (!previousCellOccupied || currentCellOccupied) && (!previousCellOccupied || !currentCellOccupied)){
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY);
            x++;
            y++;
        }
        ArrayList<Position> movements = new ArrayList<Position>();
        if(previousCellOccupied && !currentCellOccupied){
            x--;
            y--;
            while((x < 10 && y < 10) || (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY)){
                movements.add(new Position(x, y));
                x++;
                y++;
            }
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while((x < 10 && y > 0) && (!previousCellOccupied || currentCellOccupied) && (!previousCellOccupied || !currentCellOccupied)){
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY);
            x++;
            y--;
        }
        ArrayList<Position> movements = new ArrayList<Position>();
        if(previousCellOccupied && !currentCellOccupied){
            x--;
            y++;
            while((x < 10 && y < 10) || (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY)){
                movements.add(new Position(x, y));
                x++;
                y--;
            }
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while((x > 0 && y > 0) && (!previousCellOccupied || currentCellOccupied) && (!previousCellOccupied || !currentCellOccupied)){
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY);
            x--;
            y--;
        }
        ArrayList<Position> movements = new ArrayList<Position>();
        if(previousCellOccupied && !currentCellOccupied){
            x++;
            y++;
            while((x < 10 && y < 10) || (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY)){
                movements.add(new Position(x, y));
                x--;
                y--;
            }
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        while((x > 0 && y < 10) && (!previousCellOccupied || currentCellOccupied) && (!previousCellOccupied || !currentCellOccupied)){
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY);
            x--;
            y++;
        }
        ArrayList<Position> movements = new ArrayList<Position>();
        if(previousCellOccupied && !currentCellOccupied){
            x++;
            y--;
            while((x < 10 && y < 10) || (currentBoard.getSpecificPiece(x, y).getType() != Type.EMPTY)){
                movements.add(new Position(x, y));
                x--;
                y++;
            }
        }
        return movements;
    }
}
