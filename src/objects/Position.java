package objects;

public class Position {

    private int x, y;

    /**
     * Builds a position.
     * @param vX X position value.
     * @param vY Y position value.
     */
    public Position(int vX, int vY){
        this.x = vX;
        this.y = vY;
    }

    /**
     * Deep copy of a given position.
     * @param position Position to copy.
     */
    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    public String toString(){
        return x + " " + y;
    }

    /**
     * Returns the position's x value.
     * @return Integer representing the position's x value.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the position's y value.
     * @return Integer representing the position's y value.
     */
    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()){
            return false;
        }
        Position p = (Position) obj;
        return (p.getX() == x && p.getY() == y);
    }

    /**
     * Returns the position.
     * @return Position.
     */
    public Position getPosition(){
        return this;
    }

    /**
     * Sets the position from a given one.
     * @param toPosition Position to set.
     */
    public void setPosition(Position toPosition) {
        this.x = toPosition.getX();
        this.y = toPosition.getY();
    }
}
