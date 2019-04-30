package objects;

import enums.Type;

import java.util.Random;

public class Board {

    private Player playerA;
    private Player playerB;

    private final int row = 10, col = 10;

    private Piece[] blackPawns = new Piece[20];

    private Piece[] whitePawns = new Piece[20];

    public Board(){
        initGame();
    }

    private void initGame(){
        boolean aBegin = new Random().nextBoolean();
        this.playerA = new Player(aBegin);
        this.playerB = new Player(!aBegin);
        int nbr = 0;
        for(int i = 0; i < 4; i++){
            for(int a = (i+1)%2; a < col; a+= 2){
                blackPawns[nbr] = new Man(i, a, false);
                whitePawns[nbr] = new Man(i+6, a, true);
                nbr++;
            }
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
