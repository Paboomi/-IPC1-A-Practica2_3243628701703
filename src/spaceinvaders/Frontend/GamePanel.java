package spaceinvaders.Frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.Shot;
import spaceinvaders.backend.enemigos.*;

/**
 *
 * @author saien
 */
public class GamePanel extends JPanel implements ActionListener {

    private PlayerShip playerShip;
    private Timer timer;
    private ImageIcon backgroundGif;
    private String PATH_BACKGROUND = "spaceinvaders/Images/space2.gif";
    private List<Enemy> enemies;

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

        enemies = new ArrayList<>();
        initEnemies();
    }

    private void initEnemies() {
        // Posicionamos a los enemigos
        int startX = 1000; 
        int startY = 50;  
        int spacing = 40;  

        // Columna de enemigos de primer tipo
        for (int i = 0; i < 5; i++) {
            enemies.add(new EnemyType1(startX, startY + i * spacing, this));
        }

        // Columna de enemigos de segundo tipo
        for (int i = 0; i < 5; i++) {
            enemies.add(new EnemyType2(startX + spacing, startY + i * spacing, this));
            enemies.add(new EnemyType2(startX + 2 * spacing, startY + i * spacing, this));
        }

        // Columna de enemigos de tercer tipo
        for (int i = 0; i < 5; i++) {
            enemies.add(new EnemyType3(startX + 3 * spacing, startY + i * spacing, this));
            enemies.add(new EnemyType3(startX + 4 * spacing, startY + i * spacing, this));
        }

        // Start enemy threads
        for (Enemy enemy : enemies) {
            new Thread(enemy).start();
        }
    }

    public void removeEnemy(Enemy enemy) {
        synchronized (enemies) {
            enemies.remove(enemy);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Dibujamos el fondo en el panel
        g.drawImage(backgroundGif.getImage(), 0, 0, getWidth(), getHeight(), this);
        playerShip.draw(g);
        synchronized (enemies) {
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.move();
        checkCollisions();
        repaint();
    }
    
    private void checkCollisions() {
        List<Shot> shots = playerShip.getShots();
        synchronized (enemies) {
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                Rectangle enemyHitbox = enemy.getHitbox();

                Iterator<Shot> shotIterator = shots.iterator();
                while (shotIterator.hasNext()) {
                    Shot shot = shotIterator.next();
                    Rectangle shotHitbox = shot.getHitbox();

                    if (enemyHitbox.intersects(shotHitbox)) {
                        enemy.hit();
                        shotIterator.remove();
                        if (!enemy.isAlive()) {
                            enemyIterator.remove();
                        }
                    }
                }
            }
        }
    }
}
