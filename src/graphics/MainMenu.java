package graphics;

import enums.AgentType;
import objects.Board;
import objects.Position;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JPanel implements MouseListener {

    private Position clickedPos;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Rectangle playAgainstAI;
    private String playAiString;
    private Rectangle playAgainstPlayer;
    private String playerButtonString;
    private Board board;
    private Font mainFont;
    private Font largeFont;

    MainMenu(CardLayout cl, JPanel cardP, Board b){

        cardLayout = cl;
        cardPanel = cardP;
        board = b;


        mainFont = new Font("Calibri",Font.PLAIN,20);
        largeFont = mainFont.deriveFont(50F);


        playAiString = "Play against Astrid";
        playAgainstAI = new Rectangle((600 / 2)-75,(600 / 2)-25,150,50);

        playerButtonString = "Play against a friend";
        playAgainstPlayer = new Rectangle((600 / 2)-75,(600 / 2)-25,150,50);

    }

    public void paint(Graphics g){

        super.paint(g);

        ((Graphics2D) g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        setBackground(new Color(0x94F75F));

        g.setFont(largeFont);

        g.drawString("Checkers", (600-g.getFontMetrics().stringWidth("Checkers"))/2,(int)(3d/12*600));

        drawButtons(g);

    }

    private void drawButtons(Graphics g){

        g.setFont(mainFont);

        int wid = g.getFontMetrics().stringWidth(playAiString);
        int hei = g.getFontMetrics().getHeight();

        playAgainstAI = new Rectangle((600-wid)/2,(int)(5d/12*600),wid+10,hei+10);

        wid = g.getFontMetrics().stringWidth(playerButtonString);

        playAgainstPlayer = new Rectangle((600-wid)/2,(int)(7d/12*600), wid+10,hei+10);

        g.setColor(new Color(0x6B61FA));
        g.fillRect((int)playAgainstAI.getX(),(int)playAgainstAI.getY(),(int)playAgainstAI.getWidth(),(int)playAgainstAI.getHeight());

        g.setColor(Color.black);
        g.drawString(playAiString,(int)playAgainstAI.getX()+5,(int)playAgainstAI.getY()+hei);



        g.setColor(new Color(0x6B61FA));
        g.fillRect((int)playAgainstPlayer.getX(),(int)playAgainstPlayer.getY(),(int)playAgainstPlayer.getWidth(),(int)playAgainstPlayer.getHeight());

        g.setColor(Color.black);
        g.drawString(playerButtonString,(int)playAgainstPlayer.getX()+5,(int)playAgainstPlayer.getY()+hei);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedPos = new Position(e.getX(),e.getY());
        if(playAgainstAI.contains(clickedPos.getX(),clickedPos.getY())) {
            System.out.println("AI");
            board.changeBlackPlayer(AgentType.ALPHABETA);
            cardLayout.show(cardPanel, "" + 2);

        }
        else if (playAgainstPlayer.contains(clickedPos.getX(),clickedPos.getY())){
            System.out.println("Human");
            cardLayout.show(cardPanel,"" + 2);
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
