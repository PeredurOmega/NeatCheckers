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
        ArrayList<Position> positions = new ArrayList<Position>();
        positions.addAll(getMovementsToBottomLeft(currentBoard));
        positions.addAll(getMovementsToBottomRight(currentBoard));
        positions.addAll(getMovementsToTopLeft(currentBoard));
        positions.addAll(getMovementsToTopRight(currentBoard));
        for(Position position: positions){
            System.out.println(position);
        }
        return positions;
    }

    public ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.print(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY);
        if ((currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).isFromTeamWhite()&&
                currentBoard.getSpecificPiece(new Position(x + 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x +2, y + 2));
        }else if(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()) {

            movements.add(new Position(x + 1, y + 1));
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY);
        if ((currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.MAN ||
                 currentBoard.getSpecificPiece(new Position(x-1, y+1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y + 2));
        }else if(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY && this.isFromTeamWhite()) {
            movements.add(new Position(x - 1, y + 1));

        }
        return movements;
    }

    public ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY);
        if((currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x-1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y - 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y - 2));
        }else if(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY && this.isFromTeamWhite()){
            movements.add(new Position(x - 1, y - 1));
        }
        return movements;
    }

    public ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.EMPTY);
        if((currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x+1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x + 2, y - 2)).getType() == Type.EMPTY) {
            movements.add(new Position(x + 2, y - 2));
        }else if(currentBoard.getSpecificPiece(new Position(x +1, y - 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()){
            movements.add(new Position(x + 1, y - 1));
        }
        return movements;
    }

    public boolean isCoronationTime(){
        return (this.getX() == 0 && this.isFromTeamWhite() || this.getX() == 9 && !this.isFromTeamWhite());
    }
}
