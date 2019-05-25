package objects;

public class Position {

    private int x, y;

    public Position(int vX, int vY){
        this.x = vX;
        this.y = vY;
    }

    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    public String toString(){
        return x+" "+y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()){
            return false;
        }
        Position p = (Position) obj;
        return (p.getX() == x && p.getY() == y);
    }

    public Position getPosition(){
        return this;
    }

    public void setPosition(Position toPosition) {
        this.x = toPosition.getX();
        this.y = toPosition.getY();
    }
}
