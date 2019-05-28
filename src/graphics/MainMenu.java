package graphics;

import enums.AgentType;
import interfaces.GameListener;
import objects.Board;
import objects.Position;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainMenu extends JPanel implements MouseListener {

    private Position clickedPos;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Rectangle playAgainstAI;
    private String playAiString;
    private Rectangle watchAgainstAI;
    private String watchAiString;
    private Rectangle playAgainstPlayer;
    private String playerButtonString;
    private Board board;
    private GameListener gameListener;
    private Font mainFont;
    private Font largeFont;

    MainMenu(CardLayout cl, JPanel cardP, Board b, GameListener gl){
        this.cardLayout = cl;
        this.cardPanel = cardP;
        this.board = b;
        this.gameListener = gl;

        try {
            mainFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/memphis5.ttf"));
            mainFont = mainFont.deriveFont(25F);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            mainFont = new Font("Arial",Font.PLAIN,20);
        }

        largeFont = mainFont.deriveFont(50F);

        playAiString = "Jouer avec Astrid";
        playAgainstAI = new Rectangle((600 / 2)-75,(600 / 2),150,50);

        playerButtonString = "Jouer avec un ami";
        playAgainstPlayer = new Rectangle((600 / 2)-75,(600 / 2),150,50);

        watchAiString = "Regarder Astrid";
        watchAgainstAI = new Rectangle((600 / 2)-75,(600 / 2),150,50);
    }

    public void paint(Graphics g){
        super.paint(g);
        ((Graphics2D) g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        setBackground(Color.white);
        g.setColor(new Color(58,15,7));
        for(int i = 0; i< 10;i++) { //b.getRow()
            for (int a = 0; a < 10; a++) { //b.getCol()
                if((a+i)%2 == 1 && (i == 0 || i == 9 || a == 0 || a == 9 || i == 1 || i == 8 || a == 1 || a == 8)){
                    g.fillRect(a * 60, i * 60, 60, 60);
                }
            }
        }
        g.setFont(largeFont);
        g.setColor(new Color(255,90,6));
        g.drawString("CHECKERS", (600-g.getFontMetrics().stringWidth("CHECKERS"))/2,(int)(40+3d/12*600));
        g.setFont(mainFont);
        g.setColor(new Color(255,90,6));
        g.drawString("by TimePlanner", (600-g.getFontMetrics().stringWidth("by TimePlanner"))/2,(int)(70+3d/12*600));
        drawButtons(g);
    }

    private void drawButtons(Graphics g){
        g.setFont(mainFont);

        int wid = g.getFontMetrics().stringWidth(playAiString);
        int hei = g.getFontMetrics().getHeight();

        playAgainstAI = new Rectangle((600-wid-20)/2,(int)(5d/12*600),wid+40,hei+10);

        wid = g.getFontMetrics().stringWidth(playerButtonString);

        playAgainstPlayer = new Rectangle((600-wid-20)/2,(int)(20+6d/12*600), wid+40,hei+10);

        wid = g.getFontMetrics().stringWidth(watchAiString);

        watchAgainstAI = new Rectangle((600-wid-20)/2,(int)(40+7d/12*600), wid+40,hei+10);

        g.setColor(new Color(255,90,6));
        Stroke oldStroke = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawRect((int)playAgainstAI.getX(),(int)playAgainstAI.getY(),(int)playAgainstAI.getWidth(),(int)playAgainstAI.getHeight());
        ((Graphics2D) g).setStroke(oldStroke);

        g.drawString(playAiString,(int)playAgainstAI.getX()+20,(int)playAgainstAI.getY()+hei);



        Stroke oldStroke2 = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawRect((int)playAgainstPlayer.getX(),(int)playAgainstPlayer.getY(),(int)playAgainstPlayer.getWidth(),(int)playAgainstPlayer.getHeight());
        ((Graphics2D) g).setStroke(oldStroke2);
        g.drawString(playerButtonString,(int)playAgainstPlayer.getX()+20,(int)playAgainstPlayer.getY()+hei);


        Stroke oldStroke3 = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawRect((int)watchAgainstAI.getX(),(int)watchAgainstAI.getY(),(int)watchAgainstAI.getWidth(),(int)watchAgainstAI.getHeight());
        ((Graphics2D) g).setStroke(oldStroke3);
        g.drawString(watchAiString,(int)watchAgainstAI.getX()+20,(int)watchAgainstAI.getY()+hei);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedPos = new Position(e.getX(),e.getY());
        if(playAgainstAI.contains(clickedPos.getX(),clickedPos.getY())) {
            board.changeBlackPlayer(AgentType.ALPHABETA);
            cardLayout.show(cardPanel, "gameContent");

        }else if (playAgainstPlayer.contains(clickedPos.getX(),clickedPos.getY())){
            cardLayout.show(cardPanel,"gameContent");
        }else if (watchAgainstAI.contains(clickedPos.getX(),clickedPos.getY())){
            board.changeWhitePlayer(AgentType.ALPHABETA);
            board.changeBlackPlayer(AgentType.ALPHABETA);
            cardLayout.show(cardPanel,"gameContent");
            new Thread(){
                public void run(){
                    gameListener.playWithAstrid();
                }
            }.start();
        }
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
