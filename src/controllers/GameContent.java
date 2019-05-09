package controllers;

import enums.Type;
import interfaces.GameListener;
import objects.Board;
import objects.Piece;
import objects.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameContent extends JPanel implements MouseListener {

    private int sideSize;
    private Board b;
    private Position clickedPos;
    private GameListener gameListener;
    private ArrayList<Position> shownMovements = new ArrayList<Position>();

    public GameContent(Board b, GameListener gm){
        this.b = b;
        this.gameListener = gm;
        sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;
        clickedPos = new Position(-1,-1);
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
                    g.fillRect(a * sideSize / row, i * sideSize / col, sideSize / row, sideSize/ col);
            }
        }
        // Pawns
        for(int i = 0; i < row; i++){
            for(int a = 0; a < col; a++){
                if(b.getGame()[i][a].getType() == Type.MAN){
                    if(b.getSpecificPiece(new Position(i, a)).isFromTeamWhite()){
                        g.setColor(Color.WHITE);
                        g.fillOval(5+a*60,5+i*60,50,50);
                    }else{
                        g.setColor(Color.BLACK);
                        g.fillOval(5+a*60,5+i*60,50,50);
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
            graphics1.clearRect(position.getY()*60, position.getX()*60,60,60);
            graphics1.setColor(new Color(10504971));
            graphics1.fillRect(position.getY()*60, position.getX()*60,60,60);
        }
    }

    public void cleanPosition(Position position) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        graphics1.clearRect(position.getY()*60, position.getX()*60,60,60);
        graphics1.setColor(new Color(10504971));
        graphics1.fillRect(position.getY()*60, position.getX()*60,60,60);
    }

    public void drawAvailablePositions(ArrayList<Position> positions) {
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics1.setColor(Color.GREEN);
        cleanPositions(shownMovements);
        for(Position position: positions){
            graphics1.fillRect(position.getY()*60, position.getX()*60,60,60);
        }
        this.shownMovements = positions;
    }

    public boolean movePiece(Position fromPosition, Position toPosition, Piece piece) {
        if(shownMovements.contains(toPosition)){
            if(Math.abs(fromPosition.getX()-toPosition.getX()) == 2 && Math.abs(fromPosition.getY()-toPosition.getY()) == 2){
                Position eatingPosition = new Position((int)((fromPosition.getX() + toPosition.getX())/2), (int)((fromPosition.getY() + toPosition.getY())/2));
                cleanPosition(eatingPosition);
                gameListener.eatOnBoard(eatingPosition);
            }
            cleanPositions(shownMovements);
            gameListener.moveOnBoard(fromPosition, toPosition);
            drawMove(fromPosition, toPosition, piece);
            shownMovements = new ArrayList<Position>();
            return true;
        }else{
            return false;
        }
    }

    private void drawMove(Position fromPosition, Position toPosition, Piece piece) {
        System.out.println("fromPosition" + fromPosition + " toPosition " + toPosition);
        cleanPosition(fromPosition);
        Graphics graphics1 = getGraphics();
        ((Graphics2D) graphics1).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        if(piece.isFromTeamWhite()){
            graphics1.setColor(Color.WHITE);
            graphics1.fillOval(5+toPosition.getY()*60,5+toPosition.getX()*60,50,50);
        }else{
            graphics1.setColor(Color.BLACK);
            graphics1.fillOval(5+toPosition.getY()*60,5+toPosition.getX()*60,50,50);
        }
    }
}