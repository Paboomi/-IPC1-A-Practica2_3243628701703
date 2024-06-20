package spaceinvaders.backend.files;

/**
 *
 * @author saien
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import spaceinvaders.backend.Contador;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.Shot;
import spaceinvaders.backend.enemigos.Enemy;
import spaceinvaders.backend.items.Item;
import spaceinvaders.backend.jugador.Jugador;
import spaceinvaders.frontend.GamePanel;

public class GameStateManager {

    public static void saveGameState(PlayerShip playerShip, List<Enemy> enemies, Contador contador, Jugador jugador, List<Item> items, List<Shot> shots) {
        String timestamp = new SimpleDateFormat("HH_mm_dd_MM_yyyy").format(new Date());
        String filename = "src/spaceinvaders/Juegos/" + timestamp + ".bin";

        File directory = new File("spaceinvader/Juegos");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Error: No se pudo crear el directorio Juegos.");
                return;
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(playerShip);
            out.writeObject(enemies);
            out.writeObject(contador);
            out.writeObject(jugador);
            out.writeObject(items);
            out.writeObject(shots);
            System.out.println("Juego guardado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el juego.");
        }
    }

    public static void loadGameState(String filename, GamePanel gamePanel) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            PlayerShip playerShip = (PlayerShip) in.readObject();
            List<Enemy> enemies = (List<Enemy>) in.readObject();
            Contador contador = (Contador) in.readObject();
            Jugador jugador = (Jugador) in.readObject();
            List<Item> items = (List<Item>) in.readObject();
            List<Shot> shots = (List<Shot>) in.readObject();

            gamePanel.setPlayerShip(playerShip);
            gamePanel.setEnemies(enemies);
            gamePanel.setContador(contador);
            gamePanel.setJugador(jugador);
            gamePanel.setItems(items);
            gamePanel.setShots(shots);

            System.out.println("Juego cargado exitosamente.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el juego.");
        }
    }
}
