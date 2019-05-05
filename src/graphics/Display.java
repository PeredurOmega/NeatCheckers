package graphics;

import controllers.GameContent;
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

    public Display(Board b, GameListener gm){
        super("Checkers");
        GameContent gc = new GameContent(b, gm);
        gc.addMouseListener(gc);
        add(gc);
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
