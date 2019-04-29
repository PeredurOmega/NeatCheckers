package objects;

import java.util.ArrayList;

public class Player {
    private boolean began;
    private ArrayList<Piece> pieces;

    public Player(boolean vBegan){
        this.began = vBegan;
        this.pieces = new ArrayList<Piece>();
        buildInitialPieces();

    }

    public boolean hasBegun() {
        return this.began;
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    private void buildInitialPieces(){
        int x = 0;
        if(this.began){
            x = 6;
        }
        for(; x < x + 4; x++){
            for(int y = (x+1)%2; y < 10;){
                Man man = new Man(x, y);
                pieces.add(man);
                y += 2;
            }
        }
    }
}
