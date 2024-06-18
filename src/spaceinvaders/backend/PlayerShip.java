package spaceinvaders.backend;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PlayerShip {
    private int x, y;
    private int width, height;
    private int speed;
    private boolean moveUp, moveDown;
    private Image image;
    private String PATH_NAVE_PLAYER = "spaceinvaders/Images/nave2x65.png";

    public PlayerShip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
        this.speed = 5;
        loadImage();
    }
    
    //Cargamos la imagen de la nave desde la ruta predefinida
     private void loadImage() {
        try {
            URL url = getClass().getClassLoader().getResource(PATH_NAVE_PLAYER);
            if (url == null) {
                throw new IOException("Image not found: " + PATH_NAVE_PLAYER);
            }
            image = ImageIO.read(url);
            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     //Dibujamos la imagen de la nave
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
        move();
    }

    public void move() {
        if (moveUp && y > 0) {
            y -= speed;
        }
        if (moveDown && y < (662-height)) { // 600 (height of the panel) - 30 (height of the ship)
            y += speed;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            moveUp = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            moveDown = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            // Disparar (Implementaremos esto más adelante)
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            moveUp = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            moveDown = false;
        }
    }
    //Devuelve el hitbox de la nave
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
}
    
