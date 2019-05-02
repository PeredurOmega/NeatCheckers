package objects;

import enums.Type;

import java.util.Random;

public class Board {

    private Player playerA;
    private Player playerB;

    private final int row = 10, col = 10;

    private Piece[][] game = new Piece[10][10];


    public Board(){
        initGame();
    }

    private void initGame(){
        boolean aBegin = new Random().nextBoolean();
        this.playerA = new Player(aBegin);
        this.playerB = new Player(!aBegin);
        for(int i = 0; i < row; i++){
            for(int a = (i+1)%2; a < col; a+= 2){
                if(i<4)
                    game[i][a] = new Man(i, a, false);
                else if(i>5)
                    game[i][a] = new Man(i, a, true);
            }
        }
    }

    public Piece getSpecificPiece(int vX, int vY){
        return game[vX][vY];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }
}
