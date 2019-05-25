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
import java.util.Arrays;
import java.util.Collections;

public class GameContent extends JPanel implements MouseListener {

    private int sideSize;
    private int caseSize;
    private double pieceRatio;
    private double crownRatio;
    private int pieceGap;
    private int crownGap;
    private int pieceSize;
    private int crownSize;
    private Board b;
    private Position clickedPos;
    private GameListener gameListener;
    private ArrayList<Position> shownMovements = new ArrayList<Position>();
    private BufferedImage whiteCrown;
    private BufferedImage blackCrown;

    public GameContent(Board b, GameListener gm){
        this.b = b;
        this.gameListener = gm;
        sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;
        caseSize = sideSize/b.getRow();
        pieceRatio = 5/6d;
        crownRatio = 1/2d;
        pieceGap = (int)((1-pieceRatio)*caseSize/2);
        crownGap = (int)((1-crownRatio)*caseSize/2);
        pieceSize = (int)(pieceRatio*caseSize);
        crownSize = (int)(crownRatio*caseSize);
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

    private void drawGame(Graphics g){

        // Board
        setBackground(new Color(16574601));
        ((Graphics2D) g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setColor(new Color(10504971));

        int row = b.getRow();
        int col = b.getCol();

        for(int i = 0; i< row;i++) { //b.getRow()
            for (int a = 0; a < col; a++) { //b.getCol()
                if((a+i)%2 == 1)
                    g.fillRect(a * caseSize, i * caseSize, caseSize, caseSize);
            }
        }
        // Pawns
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
                    //Bug in Crown Display to resolve
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedPos = new Position(e.getY()/(sideSize / b.getCol()),e.getX()/(sideSize / b.getRow()));
        gameListener.onClick(clickedPos);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void cleanPossibilities() {
        cleanPositions(this.shownMovements);
    }

    public void cleanPositions(ArrayList<Position> positions) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        for(Position position: positions){
            graphics1.clearRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
            graphics1.setColor(new Color(10504971));
            graphics1.fillRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
        }
    }

    public void cleanPosition(Position position) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        graphics1.clearRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
        graphics1.setColor(new Color(10504971));
        graphics1.fillRect(position.getY()*caseSize, position.getX()*caseSize,caseSize,caseSize);
    }

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

    public boolean movePiece(Position fromPosition, Position toPosition, Piece piece, ArrayList<Position> eatenPositions, boolean aiMove) {
        if(shownMovements.contains(toPosition) || aiMove){
            for(Position eatenPosition: eatenPositions){
                cleanPosition(eatenPosition);
                gameListener.eatOnBoard(eatenPosition);
            }
            cleanPositions(shownMovements);
            drawMove(fromPosition, toPosition, piece);
            gameListener.moveOnBoard(fromPosition, toPosition);
            shownMovements = new ArrayList<Position>();
            return true;
        }else{
            return false;
        }
    }

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