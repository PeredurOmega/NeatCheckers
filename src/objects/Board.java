package objects;

import enums.AgentType;
import enums.Type;

public class Board{

    private Player whitePlayer;
    private Player blackPlayer;

    private boolean isTeamWhiteTurn = true;

    private final int row = 10, col = 10;

    private Piece[][] game = new Piece[10][10];


    public Board(){
        initGame();
    }

    public Board(Board board){
        this.whitePlayer = board.whitePlayer;
        this.blackPlayer = board.blackPlayer;
        this.isTeamWhiteTurn = board.isTeamWhiteTurn;
        this.game = cloneArray(board.game);
    }

    private Piece[][] cloneArray(Piece[][] src) {
        int length = src.length;
        Piece[][] target = new Piece[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    private void initGame(){
        this.whitePlayer = new Player(true, AgentType.HUMAN);
        this.blackPlayer = new Player(false, AgentType.ALPHABETA);
        for(int i = 0; i < row; i++){
            for(int a = (i+1)%2; a < col; a+= 2){
                if(i<4){
                    this.game[i][a] = new Man(i, a, false);
                }
                else if(i>5){
                    this.game[i][a] = new Man(i, a, true);
                }
            }
        }
        for(int i = 0; i < row; i++){
            for(int a = 0; a < col; a++){
                if(this.game[i][a] == null){
                    this.game[i][a] = new Empty(i, a);
                }
            }
        }
    }

    public Piece[][] getGame() {
        return this.game;
    }

    public Piece getSpecificPiece(Position position){
        if(position.getX() < 0 || position.getX() > 9 || position.getY() < 0 || position.getY() > 9 ){
            return new Out();
        }else{
            return this.game[position.getX()][position.getY()];
        }
    }

    public boolean isTeamWhiteTurn() { return isTeamWhiteTurn; }

    public void setTeamWhiteTurn(boolean teamWhiteTurn) { isTeamWhiteTurn = teamWhiteTurn; }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        if(this.isTeamWhiteTurn){
            return whitePlayer;
        }else{
            return blackPlayer;
        }
    }

    private void promote(Piece piece){
        this.game[piece.getX()][piece.getY()] = new King(piece.getX(),piece.getY(),piece.isFromTeamWhite());
    }

    public void move(Position fromPosition, Position toPosition){
        Piece fromPiece = this.game[fromPosition.getX()][fromPosition.getY()];
        if(fromPiece.getType() == Type.MAN){
            this.game[toPosition.getX()][toPosition.getY()] = new Man(toPosition.getX(), toPosition.getY(), fromPiece.isFromTeamWhite());
        }else if(fromPiece.getType() == Type.KING){
            this.game[toPosition.getX()][toPosition.getY()] = new King(toPosition.getX(), toPosition.getY(), fromPiece.isFromTeamWhite());
        }
        this.game[fromPosition.getX()][fromPosition.getY()] = new Empty(fromPosition.getX(), fromPosition.getY());

        if(this.game[toPosition.getX()][toPosition.getY()].isCoronationTime()){
            this.promote(this.game[toPosition.getX()][toPosition.getY()]);
        }
    }
    public void eat(Position eatPosition){
        this.game[eatPosition.getX()][eatPosition.getY()] = new Empty(eatPosition.getX(), eatPosition.getY());
    }
    public void addPiece(Piece piece){
        this.game[piece.getX()][piece.getY()] = piece;
    }

    public boolean couldEat() {
        for(int i = 0; i < row; i++){
            for(int a = (i+1)%2; a < col; a+= 2){
                Piece piece = game[i][a];
                if((piece.getType() == Type.MAN || piece.getType() == Type.KING) && piece.isFromTeamWhite() == isTeamWhiteTurn()){
                    if(piece.getAtePositions(this, new Position(-1, -1)).size() > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void rotatePlayer() {
        this.isTeamWhiteTurn = !this.isTeamWhiteTurn;
    }

    public boolean isAiTurn() {
        switch (this.getPlayer().getAgentType()){
            case HUMAN: return false;
            case ALPHABETA: return true;
            default:return false;
        }
    }
}
