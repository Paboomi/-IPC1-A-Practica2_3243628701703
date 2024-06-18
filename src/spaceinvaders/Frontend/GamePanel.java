package spaceinvaders.Frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import spaceinvaders.backend.PlayerShip;

/**
 *
 * @author saien
 */
public class GamePanel extends JPanel implements ActionListener {

    private PlayerShip playerShip;
    private Timer timer;
    private ImageIcon backgroundGif;
    private String PATH_BACKGROUND = "spaceinvaders/Images/space2.gif";

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 662));
        setBackground(Color.BLACK);
        playerShip = new PlayerShip(50, 300); // Posición inicial de la nave

        backgroundGif = new ImageIcon(getClass().getClassLoader().getResource(PATH_BACKGROUND));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                playerShip.keyReleased(e);
            }
        });
        
        setFocusable(true);
        setDoubleBuffered(true);

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Dibujamos el fondo en el panel
        g.drawImage(backgroundGif.getImage(), 0, 0, getWidth(), getHeight(), this);
        playerShip.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.move();
        repaint();
    }
}
