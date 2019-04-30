package objects;

import enums.Type;

public class Empty extends Piece {

    public Empty(int vX, int vY, boolean vIsFromTeamWhite) {
        super(vX, vY, Type.EMPTY, vIsFromTeamWhite);
    }
}
