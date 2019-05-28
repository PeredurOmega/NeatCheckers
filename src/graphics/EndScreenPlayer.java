package graphics;

import enums.AgentType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndScreenPlayer extends JPanel implements MouseListener {

    private Display display;
    private Boolean whiteTeamWon;

    EndScreenPlayer(Display d){
        display = d;
    }

    public void paint(Graphics g){
        super.paint(g);

        String message;
        if(whiteTeamWon)
            message = "Congrats to the White Team";
        else
            message = "Congrats to the Black Team";

        ((Graphics2D) g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        setBackground(new Color(0x94F75F));

        g.setFont(new Font("Calibri",Font.PLAIN,20));

        g.setColor(Color.black);

        g.drawString(message, (600-g.getFontMetrics().stringWidth(message))/2, (600-g.getFontMetrics().getHeight())/2);

    }

    public void setWinnerTeam(boolean whiteTeamWon){
        this.whiteTeamWon = whiteTeamWon;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { display.dispose(); }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
