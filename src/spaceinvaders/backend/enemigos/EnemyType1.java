package spaceinvaders.backend.enemigos;

import spaceinvaders.Frontend.GamePanel;

/**
 *
 * @author saien
 */
public class EnemyType1 extends Enemy {

    private static final String PATH_ENEMY = "spaceinvaders/Images/enemigo_1x65.png";

    public EnemyType1(int x, int y, GamePanel gamePanel) {
        super(x, y, 1, 10, 2, PATH_ENEMY, gamePanel);
    }

}
