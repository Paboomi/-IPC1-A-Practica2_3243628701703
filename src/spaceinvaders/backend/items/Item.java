package spaceinvaders.backend.items;

/**
 *
 * @author saien
 */
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import spaceinvaders.backend.Contador;
import spaceinvaders.frontend.GamePanel;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.jugador.Jugador;

public abstract class Item {

    protected int x, y, speed;
    protected boolean active;
    protected Image image;
    protected int width, height;
    protected GamePanel gamePanel;

    public Item(int x, int y, int speed, String imagePath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = true;
        this.gamePanel = gamePanel;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource(imagePath));
            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        if (x > 0) {
            x -= speed;
        } else {
            active = false;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public abstract void applyEffect(Jugador jugador, Contador contador);
}

