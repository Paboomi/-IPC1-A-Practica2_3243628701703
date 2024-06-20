package spaceinvaders.backend.items;

import spaceinvaders.backend.Contador;
import spaceinvaders.backend.jugador.Jugador;
import spaceinvaders.frontend.GamePanel;

/**
 *
 * @author saien
 */
public class PuntosExtra extends Item {

    private static final String PATH_AUME_PUNTOS = "spaceinvaders/Images/increment_coinx32.png";

    public PuntosExtra(int x, int y, GamePanel gamePanel) {
        super(x, y, 3, PATH_AUME_PUNTOS, gamePanel);
    }

    @Override
    public void applyEffect(Jugador jugador, Contador contador) {
        System.out.println("Puntos incrementados");
        jugador.incrementPoints(10);
    }
}
