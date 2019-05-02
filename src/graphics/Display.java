package graphics;

import objects.Board;
import objects.Man;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    private static Display game = null;
    private GameContent gc;

    public Display(Board b){
        super("Checkers");
        gc = new GameContent(b);
        add(gc);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void displayGame(Board b){
        if(game == null){
            game = new Display(b);
        }
        game.gc.b = b;
    }

    class GameContent extends JPanel{

        int sideSize;
        Board b;

        GameContent(Board b){

            this.b = b;

            sideSize = (Toolkit.getDefaultToolkit().getScreenSize().height/600)*600;


            setPreferredSize(new Dimension(sideSize,sideSize));

        }

        public void paint(Graphics g){

            super.paint(g);

            drawGame(g);
        }

        void drawGame(Graphics g){
            // Board
            setBackground(new Color(16574601));
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
                        if(b.getGame()[i][a].isFromTeamWhite() == true){
                            g.setColor(Color.WHITE);
                        }
                        else
                            g.setColor(Color.BLACK);
                        g.fillOval(5+a*60,5+i*60,50,50);
                    }
                }
            }
        }
    }

}
