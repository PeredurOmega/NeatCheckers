package controllers;

import enums.Type;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameContent extends JPanel implements MouseListener {

    private final int sideSize;
    private final int caseSize;
    private final int pieceGap;
    private final int crownGap;
    private final int pieceSize;
    private final int crownSize;
    private final Board b;
    private Position clickedPos;
    private final GameListener gameListener;
    private ArrayList<Position> shownMovements = new ArrayList<>();
    private BufferedImage whiteCrown;
    private BufferedImage blackCrown;

    /**
     * Builds a game content.
     * @param b Current board.
     * @param gm GameListener to use.
     */
    public GameContent(Board b, GameListener gm){
        this.b = b;
        this.gameListener = gm;
        sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;
        caseSize = sideSize/b.getRow();
        double pieceRatio = 5 / 6d;
        double crownRatio = 1 / 2d;
        pieceGap = (int)((1- pieceRatio)*caseSize/2);
        crownGap = (int)((1- crownRatio)*caseSize/2);
        pieceSize = (int)(pieceRatio *caseSize);
        crownSize = (int)(crownRatio *caseSize);
        clickedPos = new Position(-1,-1);
        try{
            whiteCrown = ImageIO.read(new File("res/white-crown.png"));
            blackCrown = ImageIO.read(new File("res/black-crown.png"));
        }catch(IOException ex){
            System.out.println("Failed Displaying Image");
        }
        setPreferredSize(new Dimension(sideSize,sideSize));
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawGame(g);
    }

    /**
     * Draws the complete game.
     * @param g Graphics to use.
     */
    private void drawGame(Graphics g){
        // Board
        setBackground(new Color(16574601));
        ((Graphics2D) g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setColor(new Color(10504971));

        int row = b.getRow();
        int col = b.getCol();

        for(int i = 0; i< row;i++) { //b.getRow()
            for (int a = 0; a < col; a++) { //b.getCol()
                if((a+i)%2 == 1){
                    g.fillRect(a * caseSize, i * caseSize, caseSize, caseSize);
                }
            }
        }

        // Pieces
        for(int i = 0; i < row; i++){
            for(int a = 0; a < col; a++){
                if(b.getGame()[i][a].getType() == Type.MAN){
                    if(b.getSpecificPiece(new Position(i, a)).isFromTeamWhite()){
                        g.setColor(Color.WHITE);
                        g.fillOval(pieceGap +a*caseSize, pieceGap +i*caseSize,pieceSize,pieceSize);
                    }else{
                        g.setColor(Color.BLACK);
                        g.fillOval(pieceGap +a*caseSize, pieceGap +i*caseSize,pieceSize,pieceSize);
                    }
                }
                else if(b.getGame()[i][a].getType() == Type.KING){
                    if(b.getSpecificPiece(new Position(i, a)).isFromTeamWhite()){
                        g.setColor(Color.WHITE);
                        g.fillOval(pieceGap +a*caseSize, pieceGap +i*caseSize,pieceSize,pieceSize);
                        g.drawImage(blackCrown, crownGap+a*caseSize,crownGap+i*caseSize,crownSize,crownSize,null);
                    }else{
                        g.setColor(Color.BLACK);
                        g.fillOval(pieceGap +a*caseSize, pieceGap +i*caseSize,pieceSize,pieceSize);
                        g.drawImage(whiteCrown, crownGap+a*caseSize,crownGap+i*caseSize,crownSize,crownSize, null);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //EMPTY
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedPos = new Position(e.getY()/(sideSize / b.getCol()),e.getX()/(sideSize / b.getRow()));
        gameListener.onClick(clickedPos);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //EMPTY
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //EMPTY
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //EMPTY
    }

    /**
     * Cleans all possibilities.
     */
    public void cleanPossibilities() {
        cleanPositions(this.shownMovements);
    }

    /**
     * Cleans specific positions.
     * @param positions ArrayList<Position> containing positions to clean.
     */
    private void cleanPositions(ArrayList<Position> positions) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        for(Position position: positions){
            graphics1.clearRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
            graphics1.setColor(new Color(10504971));
            graphics1.fillRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
        }
    }

    /**
     * Cleans specific position.
     * @param position Position to clean.
     */
    private void cleanPosition(Position position) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        graphics1.clearRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
        graphics1.setColor(new Color(10504971));
        graphics1.fillRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
    }

    /**
     * Draws available positions.
     * @param positions ArrayList<Position> to draw as available positions.
     */
    public void drawAvailablePositions(ArrayList<Position> positions) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        cleanPositions(shownMovements);
        for(Position position: positions){
            graphics1.fillRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
        }
        this.shownMovements = positions;
    }

    /**
     * Moves a piece.
     * @param fromPosition Current position of the piece to move.
     * @param toPosition Position to move to.
     * @param piece Piece to move.
     * @param eatenPositions ArrayList<Position> containing the positions eaten during the move.
     * @param aiMove Boolean true if the move is done by an AI, false otherwise.
     * @return Boolean true if the move succeeded, false otherwise.
     */
    public boolean movePiece(Position fromPosition, Position toPosition, Piece piece, ArrayList<Position> eatenPositions, boolean aiMove) {
        if(shownMovements.contains(toPosition) || aiMove){
            for(Position eatenPosition: eatenPositions){
                cleanPosition(eatenPosition);
                gameListener.eatOnBoard(eatenPosition);
            }
            cleanPositions(shownMovements);
            drawMove(fromPosition, toPosition, piece);
            gameListener.moveOnBoard(fromPosition, toPosition);
            shownMovements = new ArrayList<>();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Draws a move.
     * @param fromPosition Current position of the piece to move.
     * @param toPosition Position to move to.
     * @param piece Piece to move.
     */
    private void drawMove(Position fromPosition, Position toPosition, Piece piece) {
        cleanPosition(fromPosition);
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        piece.setPosition(toPosition);
        if(piece.isFromTeamWhite()){
            graphics1.setColor(Color.WHITE);
            graphics1.fillOval(pieceGap+toPosition.getY()*caseSize,pieceGap+toPosition.getX()*caseSize,pieceSize,pieceSize);
            if(piece.getType() == Type.KING || piece.isCoronationTime()){
                graphics1.drawImage(blackCrown, crownGap+toPosition.getY()*caseSize,crownGap+toPosition.getX()*caseSize,crownSize,crownSize,null);
            }
        }else{
            graphics1.setColor(Color.BLACK);
            graphics1.fillOval(pieceGap+toPosition.getY()*caseSize,pieceGap+toPosition.getX()*caseSize,pieceSize,pieceSize);
            if(piece.getType() == Type.KING || piece.isCoronationTime()){
                graphics1.drawImage(whiteCrown, crownGap+toPosition.getY()*caseSize,crownGap+toPosition.getX()*caseSize,crownSize,crownSize,null);
            }
        }
    }
}