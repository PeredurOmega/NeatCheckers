package objects;

import enums.Type;

import java.util.ArrayList;

public class Piece extends Position {

    private final Type type;
    private boolean isFromTeamWhite;

    Piece(int vX, int vY, Type type, boolean vIsFromTeamWhite) {
        super(vX, vY);
        this.type = type;
        this.isFromTeamWhite = vIsFromTeamWhite;
    }

    public Type getType(){
        return this.type;
    }

    public boolean isFromTeamWhite(){
        return this.isFromTeamWhite;
    }

    public ArrayList<Position> getAvailableMovements(Board currentBoard){
        return new ArrayList<>();
    }
    public ArrayList<Position> getAllEatingMovements(Board currentBoard){
        return new ArrayList<>();
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    public void setFromTeamWhite(boolean fromTeamWhite) {
        isFromTeamWhite = fromTeamWhite;
    }

    public boolean isCoronationTime(){ return false;}

    public ArrayList<Position> getAtePositions(Board currentBoard, Position selectedPosition){
        return new ArrayList<>();
    }
}
