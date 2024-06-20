package spaceinvaders.backend.enemigos;

import spaceinvaders.frontend.GamePanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import spaceinvaders.backend.jugador.Jugador;

public class Enemy implements Runnable, ActionListener, Serializable {

    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    protected int x, y;
    protected int health;
    protected int points;
    protected int speed;
    protected boolean alive;
    private boolean exploding;
    protected transient Image image;
    protected transient ImageIcon explosionIcon;
    protected int width, height;
    protected String imagePath;
    protected String explosionPath = "spaceinvaders/Images/explosion1.gif"; // Placeholder path for explosion image
    protected transient GamePanel gamePanel;
    private long explosionStartTime;
    private static final int EXPLOSION_DURATION = 300; // 300 ms
    private boolean movingDown;
    private transient Timer explosionTimer;

    public Enemy(int x, int y, int health, int points, int speed, String imagePath, GamePanel gamePanel) {
       
        this.x = x;
        this.y = y;
        this.health = health;
        this.points = points;
        this.speed = 5;
        this.alive = true;
        this.exploding = false;
        this.imagePath = imagePath;
        this.gamePanel = gamePanel;
        this.movingDown = true;
        loadImage();
        initExplosionTimer();
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

    private void initExplosionTimer() {
        explosionTimer = new Timer(EXPLOSION_DURATION, this);
        explosionTimer.setRepeats(false); // No repetir después de una sola ejecución
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == explosionTimer) {
            exploding = false;
            gamePanel.removeEnemy(this);
        }
    }

    public void draw(Graphics g) {
        if (exploding) {
            if (System.currentTimeMillis() - explosionStartTime < EXPLOSION_DURATION) {
                g.drawImage(explosionIcon.getImage(), x, y, null);
            } else {
                exploding = false;
            }
        } else if (alive) {
            g.drawImage(image, x, y, null);
        }
    }

    public void hit(Jugador jugador) {
        health--;
        if (health <= 0) {
            darPuntos(jugador);
            alive = false;
            exploding = true;
            explosionStartTime = System.currentTimeMillis();
            explosionTimer.start();
        }
    }

    public void darPuntos(Jugador jugador) {
        jugador.incrementPoints(points);
    }

    public void move() {
        if (alive) {
            x -= speed; // Movimiento hacia la izquierda
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public void moveDown(int speed) {
        this.y += speed;
    }

    public void moveUp(int speed) {
        this.y -= speed;
    }

    public void moveLeft(int speed) {
        this.x -= speed;
    }

    public long getExplosionStartTime() {
        return explosionStartTime;
    }

    public static int getEXPLOSION_DURATION() {
        return EXPLOSION_DURATION;
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // No serialize `image` and `explosionIcon` as they are transient
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        loadImage(); // Re-load image after deserialization
        initExplosionTimer(); // Re-initialize the explosion timer
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
