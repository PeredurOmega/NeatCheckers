package objects;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Player playerA;
    private Player playerB;

    public Board(){
        boolean aBegin = new Random().nextBoolean();
        this.playerA = new Player(aBegin);
        this.playerB = new Player(!aBegin);
    }

    public ArrayList<Piece> getAllPieces(){
        ArrayList<Piece> allPieces = new ArrayList<Piece>();
        allPieces.addAll(playerA.getPieces());
        allPieces.addAll(playerB.getPieces());
        return allPieces;
    }
}
