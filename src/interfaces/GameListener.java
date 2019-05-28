package interfaces;

import objects.Position;

public interface GameListener {
    /**
     * Method to manage click.
     * @param position Clicked position.
     */
    void onClick(Position position);

    /**
     * Method to move a piece on board.
     * @param fromPosition Current position of the piece.
     * @param toPosition Position to go.
     */
    void moveOnBoard(Position fromPosition, Position toPosition);

    /**
     * Method to eat a piece on board.
     * @param eatingPosition Position to eat on board.
     */
    void eatOnBoard(Position eatingPosition);

    /**
     * Ask Astrid to play.
     */
    void playWithAstrid();
}
