package spaceinvaders.backend.items;

import spaceinvaders.backend.Contador;
import spaceinvaders.frontend.GamePanel;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.jugador.Jugador;

/**
 *
 * @author saien
 */
public class Penalizacion extends Item {

    private static final String PATH_DISM_PUNTOS = "spaceinvaders/Images/decrement_coinx32.png";
    public Penalizacion(int x, int y, GamePanel gamePanel) {
        super(x, y, 3, PATH_DISM_PUNTOS, gamePanel);
    }

    @Override
    public void applyEffect(Jugador jugador, Contador contador) {
        jugador.decrementPoints(-10);
    }
}
