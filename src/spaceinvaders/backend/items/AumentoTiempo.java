package spaceinvaders.backend.items;

import spaceinvaders.backend.Contador;
import spaceinvaders.frontend.GamePanel;
import spaceinvaders.backend.PlayerShip;
import spaceinvaders.backend.jugador.Jugador;

/**
 *
 * @author saien
 */
public class AumentoTiempo extends Item{
        private static final String PATH_AUME_TIEMPO = "spaceinvaders/Images/increment_clockx32.png";

    public AumentoTiempo(int x, int y, GamePanel gamePanel) {
        super(x, y, 3, PATH_AUME_TIEMPO, gamePanel);
    }

    @Override
    public void applyEffect(Jugador jugador, Contador contador) {
        contador.addTime(10);
    }
}
