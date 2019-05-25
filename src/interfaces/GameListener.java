package interfaces;

import objects.Position;

public interface GameListener {
    void onClick(Position position);
    void moveOnBoard(Position fromPosition, Position toPosition);
    void eatOnBoard(Position eatingPosition);
}
