package spaceinvaders.backend.items;

/**
 *
 * @author saien
 */
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import spaceinvaders.backend.Contador;
import spaceinvaders.frontend.GamePanel;
import spaceinvaders.backend.jugador.Jugador;

public abstract class Item implements Serializable{

    protected int x, y, speed;
    protected boolean active;
    protected transient Image image;
    protected int width, height;
    protected transient GamePanel gamePanel;
    protected String imagePath;

    public Item(int x, int y, int speed, String imagePath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = true;
        this.gamePanel = gamePanel;
        this.imagePath = imagePath;
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
    
     private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        loadImage(imagePath);
    }
}

