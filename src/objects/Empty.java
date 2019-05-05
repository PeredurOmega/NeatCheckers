package objects;

import enums.Type;

public class Empty extends Piece {

    public Empty(int vX, int vY) {
        super(vX, vY, Type.EMPTY, true);
    }
}
