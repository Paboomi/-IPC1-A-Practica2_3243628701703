package spaceinvaders.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import spaceinvaders.backend.Contador;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.Shot;
import spaceinvaders.backend.enemigos.*;
import spaceinvaders.backend.files.GameState;
import spaceinvaders.backend.files.GameStateManager;
import spaceinvaders.backend.items.*;
import spaceinvaders.backend.jugador.Jugador;

/**
 *
 * @author saien
 */
public class GamePanel extends JPanel implements ActionListener {

    private PlayerShip playerShip;
    private Timer timer;
    private transient ImageIcon backgroundGif;
    private String PATH_BACKGROUND = "spaceinvaders/Images/space2.gif";
    private List<Enemy> enemies;
    private boolean movingDown = true;
    private boolean verticalMoveComplete = false;
    private int enemySpeed = 6;
    private Contador contador;
    private Jugador jugador;
    private List<Item> items;
    private Principal principal;
    private SpaceInvaders spaceInvaders;
    private List<Shot> shots;

    public GamePanel(Principal principal) {
        setPreferredSize(new Dimension(1280, 662));
        setBackground(Color.BLACK);
        this.principal = principal;
        this.spaceInvaders = principal.getSpaceInvaders();
        playerShip = new PlayerShip(50, 300); // Posición inicial de la nave

        backgroundGif = new ImageIcon(getClass().getClassLoader().getResource(PATH_BACKGROUND));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.keyPressed(e);
                handleKeyPress(e);
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
        items = new ArrayList<>();
        shots = new ArrayList<>();
        initEnemies();
        initItems();
    }
    
    

    private void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            closeGame();
        } else if (key == KeyEvent.VK_S) {
            GameStateManager.saveGameState(playerShip, enemies, contador, jugador, items, shots);
        }
    }

    private void closeGame() {
        timer.stop();
        principal.setVisible(true);
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public void loadGameState(GameState gameState) {
        if (gameState == null) {
            System.out.println("Error: GameState is null.");
            return;
        }

        // Set player ship
        setPlayerShip(gameState.getPlayerShip());

        // Set enemies
        setEnemies(gameState.getEnemies());

        // Set contador
        setContador(gameState.getContador());

        // Set jugador
        setJugador(gameState.getJugador());

        // Set items
        setItems(gameState.getItems());

        // Set shots
        setShots(gameState.getShots());

        // Restart timer if needed
        if (!timer.isRunning()) {
            timer.start();
        }

        // Repaint the panel to reflect loaded state
        repaint();

        System.out.println("Game state loaded successfully.");
    }

    public SpaceInvaders getSpaceInvaders() {
        return spaceInvaders;
    }

    public void setSpaceInvaders(SpaceInvaders spaceInvaders) {
        this.spaceInvaders = spaceInvaders;
    }
    
    

    private void initItems() {
        Timer itemTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generateRandomItem();
            }
        });
        itemTimer.start();
    }

    private void generateRandomItem() {
        int randomY = (int) (Math.random() * getHeight());
        int randomType = (int) (Math.random() * 4); // Suponiendo 4 tipos de ítems
        Item item;
        switch (randomType) {
            case 0:
                item = new AumentoTiempo(getWidth(), randomY, this);
                break;
            case 1:
                item = new PuntosExtra(getWidth(), randomY, this);
                break;
            case 2:
                item = new DisminucionTiempo(getWidth(), randomY, this);
                break;
            case 3:
                item = new Penalizacion(getWidth(), randomY, this);
                break;
            default:
                return;
        }
        items.add(item);
    }

    private void initEnemies() {
        // Posicionamos a los enemigos
        int startX = 1200;
        int startY = 10;
        int spacing = 70;

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

    }

    public void removeEnemy(Enemy enemy) {
        synchronized (enemies) {
            enemies.remove(enemy);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el fondo, nave y enemigos
        g.drawImage(backgroundGif.getImage(), 0, 0, getWidth(), getHeight(), this);
        playerShip.draw(g);

        synchronized (enemies) {
            Iterator<Enemy> iterator = enemies.iterator();
            while (iterator.hasNext()) {
                Enemy enemy = iterator.next();
                enemy.draw(g);
                if (!enemy.isAlive() && !enemy.isExploding()) {
                    iterator.remove(); // Eliminar el enemigo si ya no está vivo ni explotando
                }
            }
        }

        // Dibujar ítems
        for (Item item : items) {
            item.draw(g);
        }
        // Dibujar balas
        for (Shot shot : shots) {
            shot.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.move();
        moveEnemies();
        checkCollisions();
        moveItems();
        moveShots();
        repaint();
    }

    private void moveItems() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            item.move();
            if (!item.isActive()) {
                iterator.remove();
            }
        }
    }

    private void moveShots() {
        Iterator<Shot> iterator = shots.iterator();
        while (iterator.hasNext()) {
            Shot shot = iterator.next();
            shot.move();
            if (!shot.isVisible()) {
                iterator.remove();
            }
        }
    }

    private void moveEnemies() {
        if (enemies.isEmpty()) {
            return;
        }

        boolean atEdge = false;

        // Revisar si hay algún enemigo en el borde superior o inferior
        for (Enemy enemy : enemies) {
            if (movingDown) {
                if (enemy.getY() + enemy.getHeight() + enemySpeed >= getHeight()) {
                    atEdge = true;
                    break;
                }
            } else {
                if (enemy.getY() - enemySpeed <= 0) {
                    atEdge = true;
                    break;
                }
            }
        }

        // Cambiar dirección si estamos en un borde
        if (atEdge) {
            movingDown = !movingDown;
            verticalMoveComplete = true;
        }

        // Mover enemigos
        for (Enemy enemy : enemies) {

            if (verticalMoveComplete) {
                enemy.moveLeft(enemySpeed);
            } else {
                if (movingDown) {
                    enemy.moveDown(enemySpeed);
                } else {
                    enemy.moveUp(enemySpeed);
                }
            }
        }

        verticalMoveComplete = false; // Reiniciar después de mover hacia la izquierda
    }

    //Evaluamos las colisiones
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
                    // Chequea si la bala está en la misma columna que el enemigo
                    if (shotHitbox.x >= enemyHitbox.x && shotHitbox.x <= enemyHitbox.x + enemyHitbox.width) {
                        if (enemyHitbox.intersects(shotHitbox)) {
                            enemy.hit(jugador);
                            shotIterator.remove();
                        }
                    }

                }
            }
        }

        // Verificar colisiones de ítems y la nave del jugador
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (playerShip.getHitbox().intersects(item.getHitbox())) {
                item.applyEffect(jugador, contador);
                iterator.remove();
            }
        }
    }

    public Contador getContador() {
        return contador;
    }

    // Getters y setters necesarios para la deserialización
    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }

}
