package spaceinvaders.backend.files;

import java.io.Serializable;
import java.util.List;
import spaceinvaders.backend.Contador;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.Shot;
import spaceinvaders.backend.enemigos.Enemy;
import spaceinvaders.backend.items.Item;
import spaceinvaders.backend.jugador.Jugador;

/**
 *
 * @author saien
 */
public class GameState implements Serializable {

    private PlayerShip playerShip;
    private List<Enemy> enemies;
    private Contador contador;
    private Jugador jugador;
    private List<Item> items;
    private List<Shot> shots;

    public GameState() {
    }

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Contador getContador() {
        return contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
    
    

}
