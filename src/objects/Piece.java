package objects;

import enums.Type;

import java.awt.*;

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

    public Position[] getAvailableMovements(Board currentBoard){
        return new Position[0];
    }
}
