package graphics;

import controllers.GameController;
import interfaces.GameListener;
import objects.Board;
import objects.Man;
import objects.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Display extends JFrame {
    private static Display game = null;
    private GameContent gc;
    private GameListener gameListener;

    public Display(Board b, GameListener gm){
        super("Checkers");
        gc = new GameContent(b);
        gc.addMouseListener(gc);
        gameListener = gm;
        add(gc);
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void displayGame(Board b){
        if(game == null){
            game = new Display(b, gameListener);
        }
        game.gc.b = b;
    }

    public Position getCaseClicked(){
        return game.gc.getClickedPos();
    }

    class GameContent extends JPanel implements MouseListener {

        int sideSize;
        Position clickedPos;
        Board b;

        GameContent(Board b){
            this.b = b;
            sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;
            clickedPos = new Position(-1,-1);
            setPreferredSize(new Dimension(sideSize,sideSize));
        }

        public void paint(Graphics g){
            super.paint(g);
            drawGame(g);
        }

        void drawGame(Graphics g){
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

        public Position getClickedPos(){
            return clickedPos;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            clickedPos = new Position(e.getX()/(sideSize / b.getRow()),e.getY()/(sideSize / b.getCol()));

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

}
