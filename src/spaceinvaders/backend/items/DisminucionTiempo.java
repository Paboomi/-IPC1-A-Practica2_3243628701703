package spaceinvaders.backend.items;

import spaceinvaders.backend.Contador;
import spaceinvaders.frontend.GamePanel;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.jugador.Jugador;

/**
 *
 * @author saien
 */
public class DisminucionTiempo extends Item{
    private static final String PATH_DISM_TIEMPO = "spaceinvaders/Images/decrement_clockx32.png";
    public DisminucionTiempo(int x, int y, GamePanel gamePanel) {
        super(x, y, 3,PATH_DISM_TIEMPO, gamePanel);
    }

    @Override
    public void applyEffect(Jugador jugador, Contador contador) {
        contador.addTime(-10);
    }
}

