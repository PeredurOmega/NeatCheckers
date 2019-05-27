package objects;

import enums.AgentType;
import enums.Type;

public class Board{

    private Player whitePlayer;
    private Player blackPlayer;
    private boolean isTeamWhiteTurn = true;
    private final int row = 10, col = 10;
    private Piece[][] game = new Piece[10][10];

    /**
     * Game initializer.
     */
    public Board(){
        this.whitePlayer = new Player(true, AgentType.HUMAN);
        this.blackPlayer = new Player(false, AgentType.HUMAN);
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

    /**
     * Deep copier for the board avoiding side effect.
     * @param board Board to copy.
     */
    public Board(Board board){
        this.whitePlayer = board.whitePlayer;
        this.blackPlayer = board.blackPlayer;
        this.isTeamWhiteTurn = board.isTeamWhiteTurn;
        this.game = cloneArray(board.game);
    }

    /**
     * Array deep copier avoiding side effect.
     * @param src Array of Piece[][] to copy.
     * @return Array of Piece[][] copied.
     */
    private Piece[][] cloneArray(Piece[][] src) {
        int length = src.length;
        Piece[][] target = new Piece[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    /**
     * Returns the current game.
     * @return An array of Piece[][] representing the game.
     */
    public Piece[][] getGame() {
        return this.game;
    }

    /**
     * Returns a specific piece from a given position.
     * @param position Position of the piece to return.
     * @return Piece found at the given position.
     */
    public Piece getSpecificPiece(Position position){
        if(position.getX() < 0 || position.getX() > 9 || position.getY() < 0 || position.getY() > 9 ){
            return new Out();
        }else{
            return this.game[position.getX()][position.getY()];
        }
    }

    /**
     * Returns true if the whites have to play.
     * @return Boolean representing the turn .
     */

    public void changeBlackPlayer(AgentType agentType){
        blackPlayer = new Player(false, agentType);
    }
    public boolean isTeamWhiteTurn() { return isTeamWhiteTurn; }

    /**
     * Returns the number of rows.
     * @return Integer representing the number of rows (default 10).
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the number of columns.
     * @return Integer representing the number of columns (default 10).
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the player who have to play.
     * @return Player who have to play.
     */
    public Player getPlayer() {
        if(this.isTeamWhiteTurn){
            return whitePlayer;
        }else{
            return blackPlayer;
        }
    }

    /**
     * Promotes a Man into a King.
     * @param piece Piece to promote.
     */
    private void promote(Piece piece){
        this.game[piece.getX()][piece.getY()] = new King(piece.getX(),piece.getY(),piece.isFromTeamWhite());
    }

    /**
     * Moves a piece from a position to another.
     * @param fromPosition Current position of the piece to move.
     * @param toPosition Position where to move the piece.
     */
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

    /**
     * Eats (or removes) a piece at a given position.
     * @param eatenPosition Position to eat (or remove).
     */
    public void eat(Position eatenPosition){
        this.game[eatenPosition.getX()][eatenPosition.getY()] = new Empty(eatenPosition.getX(), eatenPosition.getY());
    }

    /**
     * Add a piece to the game (simulation purpose only).
     * @param piece Piece to add to the game.
     */
    void addPiece(Piece piece){
        this.game[piece.getX()][piece.getY()] = piece;
    }

    /**
     * Checks if the current player can eat. Returns true if so.
     * @return Boolean true if the player can eat false otherwise.
     */
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

    /**
     * Changes the player.
     */
    public void rotatePlayer() {
        this.isTeamWhiteTurn = !this.isTeamWhiteTurn;
    }

    /**
     * Returns true if the AI has to play.
     * @return Boolean true if the AI has to play, false otherwise.
     */
    public boolean isAiTurn() {
        switch (this.getPlayer().getAgentType()){
            case HUMAN: return false;
            case ALPHABETA: return true;
            default:return false;
        }
    }
}
