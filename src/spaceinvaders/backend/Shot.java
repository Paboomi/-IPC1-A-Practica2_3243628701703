package spaceinvaders.backend;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import java.net.URL;

public class Shot implements Serializable{
    private int x, y;
    private int speed;
    private transient Image image;
    private int width, height;
    private boolean visible;
    private String PATH_SHOT_IMAGE = "spaceinvaders/Images/bulletx16.png";

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 10;
        this.visible = true;
        loadImage();
    }

    private void loadImage() {
        try {
            URL url = getClass().getClassLoader().getResource(PATH_SHOT_IMAGE);
            if (url == null) {
                throw new IOException("Image not found: " + PATH_SHOT_IMAGE);
            }
            image = ImageIO.read(url);
            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        x += speed;
        if (x > 1280) { // Ancho del panel
            visible = false;
        }
    }

    public void draw(Graphics g) {
        if (visible) {
            g.drawImage(image, x, y, null);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
}

