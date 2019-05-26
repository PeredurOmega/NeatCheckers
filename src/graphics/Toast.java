package graphics;

import java.awt.*;
import javax.swing.*;

public class Toast extends JFrame {

    private final JWindow w;

    /**
     * Builds a toast (not auto-displayed).
     * @param s String text of the toast.
     */
    public Toast(final String s) {
        w = new JWindow();
        w.setBackground(new Color(0, 0, 0, 0));
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g)
            {
                int wid = g.getFontMetrics().stringWidth(s);
                int hei = g.getFontMetrics().getHeight();
                g.setColor(Color.black);
                g.fillRect(10, 10, wid + 30, hei + 10);
                g.setColor(Color.black);
                g.drawRect(10, 10, wid + 30, hei + 10);
                g.setColor(new Color(255, 255, 255, 240));
                g.drawString(s, 25, 27);
                int t = 250;
                for (int i = 0; i < 4; i++) {
                    t -= 60;
                    g.setColor(new Color(0, 0, 0, t));
                    g.drawRect(10 - i, 10 - i, wid + 30 + i * 2,
                            hei + 10 + i * 2);
                }
            }
        };
        w.add(p);
        w.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 150, (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 50);
        w.setSize(300, 100);
    }

    /**
     * Shows a toast text in the middle of the screen.
     */
    public void showToastText() {
        try {
            w.setOpacity(1);
            w.setVisible(true);
            Thread.sleep(1000);
            for (double d = 1.0; d > 0.2; d -= 0.03) {
                Thread.sleep(25);
                w.setOpacity((float)d);
            }
            w.setVisible(false);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
