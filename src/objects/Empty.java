package objects;

import enums.Type;

class Empty extends Piece {

    public Empty(int vX, int vY) {
        super(vX, vY, Type.EMPTY, false);
    }
}
