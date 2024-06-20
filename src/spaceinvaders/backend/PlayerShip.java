package spaceinvaders.backend;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class PlayerShip implements Serializable{

    private int x, y;
    private int width, height;
    private int speed;
    private boolean moveUp, moveDown;
    private transient Image image;
    private String PATH_NAVE_PLAYER = "spaceinvaders/Images/nave2x65.png";
    private List<Shot> shots;
    private boolean canShoot;

    public PlayerShip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
        this.speed = 5;
        this.shots = new ArrayList<>();
        this.canShoot = true;
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
        //Dibujamos los disparos
        for (Shot shot : shots) {
            shot.draw(g);
        }
//        move();
    }

    public void move() {
        if (moveUp && y > 0) {
            y -= speed;
        }
        if (moveDown && y < (662 - height)) { // 600 (height of the panel) - 30 (height of the ship)
            y += speed;
        }
        updateShots();
    }

    private void updateShots() {
        List<Shot> toRemove = new ArrayList<>();
        for (Shot shot : shots) {
            shot.move();
            if (!shot.isVisible()) {
                toRemove.add(shot);
            }
        }
        shots.removeAll(toRemove);
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
            shoot();
            canShoot = false;
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
         if (key == KeyEvent.VK_SPACE) {
            canShoot = true;
        }
    }
    
    //Aregamos disparos a la lista
    private void shoot() {
        shots.add(new Shot(x + width, y + height / 2));
    }

    //Devuelve el hitbox de la nave
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public List<Shot> getShots() {
        return shots;
    }
    
    
}
