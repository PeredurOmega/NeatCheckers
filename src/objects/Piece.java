package objects;

import enums.Type;

import java.util.ArrayList;

public class Piece extends Position {

    private final Type type;
    private final boolean isFromTeamWhite;

    /**
     * Builds a piece.
     * @param vX X position of this piece.
     * @param vY Y position of this piece.
     * @param type Type of this piece.
     * @param vIsFromTeamWhite Boolean representing the team of this piece (true if white, false otherwise).
     */
    Piece(int vX, int vY, Type type, boolean vIsFromTeamWhite) {
        super(vX, vY);
        this.type = type;
        this.isFromTeamWhite = vIsFromTeamWhite;
    }

    /**
     * Returns the piece's type.
     * @return Type of this piece.
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Returns true if the piece is from the white team, false otherwise.
     * @return Boolean representing the piece's team.
     */
    public boolean isFromTeamWhite(){
        return this.isFromTeamWhite;
    }

    /**
     * Returns all available movements (this method should be override for Man and King).
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> containing all the movements that this piece can do.
     */
    public ArrayList<Position> getAvailableMovements(Board currentBoard){
        return new ArrayList<>();
    }

    /**
     * Returns all eating movements (this method should be override for Man and King).
     * @param currentBoard Current board to use.
     * @return ArrayList<Position> containing all the eating movements that this piece can do.
     */
    public ArrayList<Position> getAllEatingMovements(Board currentBoard){
        return new ArrayList<>();
    }

    /**
     * Returns if this piece should become a King (this method should be override for Man).
     * @return Boolean true if we should promote this piece, false otherwise.
     */
    public boolean isCoronationTime(){ return false;}

    /**
     * Returns all eaten positions (this method should be override for Man and King).
     * @param currentBoard Current board to use.
     * @param selectedPosition Position after eating.
     * @return ArrayList<Position> containing all the positions eaten by this piece for this specific path.
     */
    public ArrayList<Position> getAtePositions(Board currentBoard, Position selectedPosition){
        return new ArrayList<>();
    }
}
