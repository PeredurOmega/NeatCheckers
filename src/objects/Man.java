package objects;

import enums.Type;

import java.util.ArrayList;

public class Man extends Piece {

    public Man(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY, Type.MAN, vIsFromWhiteTeam);
    }

    @Override
    public ArrayList<Position> getAvailableMovements(Board currentBoard) {
        //TODO Use recursivity to get all movements with several components

        return new ArrayList<Position>();
    }
/*
    public Position[] getMovementsToBottomRight(Board currentBoard){
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
    }*/

}
