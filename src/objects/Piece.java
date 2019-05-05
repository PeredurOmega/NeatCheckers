package objects;

import enums.Type;

import java.awt.*;
import java.util.ArrayList;

public class Piece extends Position {

    private Type type;
    private boolean isFromTeamWhite;

    public Piece(int vX, int vY, Type type, boolean vIsFromTeamWhite) {
        super(vX, vY);
        this.type = type;
        this.isFromTeamWhite = vIsFromTeamWhite;
    }

    public Type getType(){
        return this.type;
    }

    public boolean isFromTeamWhite(){
        return isFromTeamWhite;
    }

    public ArrayList<Position> getAvailableMovements(Board currentBoard){
        return new ArrayList<Position>();
    }

    public void setFromTeamWhite(boolean fromTeamWhite) {
        isFromTeamWhite = fromTeamWhite;
    }

}
