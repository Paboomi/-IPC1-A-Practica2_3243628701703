package spaceinvaders.backend;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import java.net.URL;

public class Shot implements Serializable {

    private static final long serialVersionUID = 6L;
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
    
    // Método para serializar y deserializar la instancia de Shot
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serializa todos los campos excepto los transient

        // Guardar la imagen como datos de imagen (bytes)
        if (image != null) {
            ImageIO.write((BufferedImage) image, "png", out); // Guardar la imagen como PNG
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Lee todos los campos excepto los transient

        // Cargar la imagen desde los datos de imagen (bytes)
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
