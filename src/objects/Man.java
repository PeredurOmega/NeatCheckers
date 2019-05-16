package objects;

import enums.Type;
import javafx.geometry.Pos;

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
        ArrayList<Position> eatingPositions = getAllEatingMovements(currentBoard);
        ArrayList<Position> eatingPositionsToSend = new ArrayList<Position>();

        if(eatingPositions.size() > 0){
            for(Position toPosition: eatingPositions){
                System.out.println(toPosition);
                Board temporaryBoard = (Board) currentBoard.clone();
                Position eatingPiecePosition = new Position((int)((this.getX() + toPosition.getX())/2), (int)((this.getY() + toPosition.getY())/2));
                temporaryBoard.eat(eatingPiecePosition);
                temporaryBoard.eat(this.getPosition());
                Man man = new Man(toPosition.getX(), toPosition.getY(), this.isFromTeamWhite());
                temporaryBoard.addPiece(man);
                ArrayList<Position> nextEatingPosition = man.getAllEatingMovements(temporaryBoard);
                System.out.println(nextEatingPosition);
                if(nextEatingPosition.size() > 0){
                    eatingPositionsToSend.addAll(nextEatingPosition);
                }else{
                    eatingPositionsToSend.add(toPosition);
                }
            }
            return eatingPositionsToSend;
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
        return eatingPositions;
    }

    private ArrayList<Position> getEatingMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.print(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY);
        if ((currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).isFromTeamWhite()&&
                currentBoard.getSpecificPiece(new Position(x + 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x +2, y + 2));
        }
        return movements;
    }

    private ArrayList<Position> getEatingMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY);
        if ((currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.MAN ||
                 currentBoard.getSpecificPiece(new Position(x-1, y+1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y + 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y + 2));
        }
        return movements;
    }

    private ArrayList<Position> getEatingMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY);
        if((currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x-1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x - 2, y - 2)).getType() == Type.EMPTY){
            movements.add(new Position(x - 2, y - 2));
        }
        return movements;
    }

    private ArrayList<Position> getEatingMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.EMPTY);
        if((currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.MAN ||
                currentBoard.getSpecificPiece(new Position(x+1, y-1)).getType() == Type.KING) &&
                this.isFromTeamWhite() != currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).isFromTeamWhite() &&
                currentBoard.getSpecificPiece(new Position(x + 2, y - 2)).getType() == Type.EMPTY) {
            movements.add(new Position(x + 2, y - 2));
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.print(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY);
        if(currentBoard.getSpecificPiece(new Position(x + 1, y + 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()) {

            movements.add(new Position(x + 1, y + 1));
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY);
        if(currentBoard.getSpecificPiece(new Position(x - 1, y + 1)).getType() == Type.EMPTY && this.isFromTeamWhite()) {
            movements.add(new Position(x - 1, y + 1));

        }
        return movements;
    }

    private ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY);
        if(currentBoard.getSpecificPiece(new Position(x - 1, y - 1)).getType() == Type.EMPTY && this.isFromTeamWhite()){
            movements.add(new Position(x - 1, y - 1));
        }
        return movements;
    }

    private ArrayList<Position> getMovementsToBottomLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();
        ArrayList<Position> movements = new ArrayList<Position>();
        System.out.println(currentBoard.getSpecificPiece(new Position(x + 1, y - 1)).getType() == Type.EMPTY);
        if(currentBoard.getSpecificPiece(new Position(x +1, y - 1)).getType() == Type.EMPTY && !this.isFromTeamWhite()){
            movements.add(new Position(x + 1, y - 1));
        }
        return movements;
    }

    public boolean isCoronationTime(){
        return (this.getX() == 0 && this.isFromTeamWhite() || this.getX() == 9 && !this.isFromTeamWhite());
    }
}
