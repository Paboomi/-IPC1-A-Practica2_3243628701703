package spaceinvaders.backend.enemigos;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import spaceinvaders.Frontend.GamePanel;

public class Enemy implements Runnable {
    protected int x, y;
    protected int health;
    protected int points;
    protected int speed;
    protected boolean alive;
    private boolean exploding;
    protected Image image;
    protected Image explosionImage;
    protected int width, height;
    protected String imagePath;
    protected String explosionPath = "spaceinvaders/Images/explosion1.gif"; // Placeholder path for explosion image
    protected GamePanel gamePanel;

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
            explosionImage = ImageIO.read(explosionUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        if (alive) {
            g.drawImage(image, x, y, null);
        } else {
            g.drawImage(explosionImage, x, y, null);
        }
    }

    public void move() {
        if (alive) {
            x -= speed;
            // Movimiento vertical alternativo
            if (y <= 0 || y >= gamePanel.getHeight() - height) {
                speed = -speed;
            }
            y += speed;
        }
    }

    public void hit() {
        health--;
        if (health <= 0) {
            alive = false;
            new Thread(() -> {
                try {
                    Thread.sleep(200); // Espera 0.2 segundos antes de desaparecer
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gamePanel.removeEnemy(this);
            }).start();
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void run() {
        while (alive) {
            move();
            try {
                Thread.sleep(40); // Ajustar para la velocidad de movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return points;
    }
}

