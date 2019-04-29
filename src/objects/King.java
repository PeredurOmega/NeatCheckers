package objects;

public class King extends Piece {

    public King(Player vPlayer, int vX, int vY) {
        super(vX, vY);
    }

    @Override
    public void getAvailableMovements(Board currentBoard) {
        super.getAvailableMovements(currentBoard);
        //TODO Use recursivity to get all movements with several components
    }
}
