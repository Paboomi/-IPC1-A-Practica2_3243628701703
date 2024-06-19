package spaceinvaders.backend.enemigos;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import spaceinvaders.Frontend.GamePanel;

public class Enemy implements Runnable {

    protected int x, y;
    protected int health;
    protected int points;
    protected int speed;
    protected boolean alive;
    private boolean exploding;
    protected Image image;
    protected ImageIcon explosionIcon;
    protected int width, height;
    protected String imagePath;
    protected String explosionPath = "spaceinvaders/Images/explosion1.gif"; // Placeholder path for explosion image
    protected GamePanel gamePanel;
    private long explosionStartTime;
    private static final int EXPLOSION_DURATION = 1000; // 1 second
    private boolean movingDown;
    private static final int MOVE_DISTANCE = 20; // Distance to move down when hitting a boundary


    public Enemy(int x, int y, int health, int points, int speed, String imagePath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.points = points;
        this.speed = speed;
        this.alive = true;
        this.exploding = false;
        this.imagePath = imagePath;
        this.gamePanel = gamePanel;
        this.movingDown = false;
        loadImage();
    }

    protected void loadImage() {
        try {
            URL url = getClass().getClassLoader().getResource(imagePath);
            if (url == null) {
                throw new IOException("Image not found: " + imagePath);
            }
            image = ImageIO.read(url);
            width = image.getWidth(null);
            height = image.getHeight(null);

            URL explosionUrl = getClass().getClassLoader().getResource(explosionPath);
            if (explosionUrl == null) {
                throw new IOException("Explosion image not found: " + explosionPath);
            }
            explosionIcon = new ImageIcon(explosionUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        if (exploding) {
            g.drawImage(explosionIcon.getImage(), x, y, null);
        } else if (alive) {
            g.drawImage(image, x, y, null);
        }
    }

     public void move() {
        if (alive) {
            if (movingDown) {
                y += speed;
                if (y + height >= gamePanel.getHeight() || y <= 0) {
                    movingDown = false;
                }
            } else {
                x -= speed;
                if (x <= 0) {
                    movingDown = true;
                }
            }
        }
    }

    public void hit() {
        health--;
        if (health <= 0) {
            alive = false;
            exploding = true;
            explosionStartTime = System.currentTimeMillis();
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void run() {
        while (alive || exploding) {
            move();
            if (exploding) {
                if (System.currentTimeMillis() - explosionStartTime > EXPLOSION_DURATION) {
                    exploding = false;
                    gamePanel.removeEnemy(this);
                }
            }
            try {
                Thread.sleep(50); // Ajustar para la velocidad de movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.repaint();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return points;
    }

    public boolean isExploding() {
        return exploding;
    }
    
    
}
