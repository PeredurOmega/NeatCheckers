package objects;

import enums.Type;

class Empty extends Piece {

    /**
     * Builds an empty piece.
     * @param vX X position of this empty piece.
     * @param vY Y position of this empty piece.
     */
    Empty(int vX, int vY) {
        super(vX, vY, Type.EMPTY, false);
    }
}
