package objects;

public class Position {

    private int x, y;

    public Position(int vX, int vY){
        this.x = vX;
        this.y = vY;
    }

    public String toString(){
        return x+" "+y;
    }

    public boolean equals(Position p){
        return (p.getX()==x && p.getY()==y);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
