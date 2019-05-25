package interfaces;

import objects.Piece;
import objects.Position;

public interface GameListener {
    public void onClick(Position position);
    public void moveOnBoard(Position fromPosition, Position toPosition);
    public void eatOnBoard(Position eatingPosition);
}
