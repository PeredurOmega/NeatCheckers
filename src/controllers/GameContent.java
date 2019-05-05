package controllers;

import interfaces.GameListener;
import objects.Board;
import objects.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameContent extends JPanel implements MouseListener {

    private int sideSize;
    private Position clickedPos;
    private Board b;
    private GameListener gameListener;

    public GameContent(Board b, GameListener gm){
        this.b = b;
        this.gameListener = gm;
        sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;
        clickedPos = new Position(-1,-1);
        setPreferredSize(new Dimension(sideSize,sideSize));
    }

    public void paint(Graphics g){
        super.paint(g);
        drawGame(g);
    }

    private void drawGame(Graphics g){
        // Board
        setBackground(new Color(16574601));
        ((Graphics2D) (g)).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
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
            for(int a = (i+1)%2; a < col; a+= 2){
                if(b.getGame()[i][a] != null){
                    if(b.getSpecificPiece(i, a).isFromTeamWhite()){
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
        clickedPos = new Position(e.getX()/(sideSize / b.getRow()),e.getY()/(sideSize / b.getCol()));
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
}