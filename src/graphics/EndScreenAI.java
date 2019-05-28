package graphics;

import enums.AgentType;
import sun.management.Agent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EndScreenAI extends JPanel implements MouseListener {

    private Display display;
    private Image finalGif;

    EndScreenAI(Display d){
        display = d;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int height = (int)((600*finalGif.getHeight(null))/(double)finalGif.getWidth(null));
        if (finalGif != null) {
            g.drawImage(finalGif, 0,(600-height)/2,600, height, this);
        }
    }

    public void setWinnerAgentType(AgentType agentType){
        String path;
        if(agentType == AgentType.ALPHABETA)
            path = "res/loseAI.gif";
        else
            path = "res/winAI.gif";
        finalGif = Toolkit.getDefaultToolkit().createImage(path);

    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        display.dispose();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
