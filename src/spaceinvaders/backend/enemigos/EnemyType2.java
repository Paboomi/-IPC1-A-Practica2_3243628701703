package spaceinvaders.backend.enemigos;

import spaceinvaders.frontend.GamePanel;

/**
 *
 * @author saien
 */
public class EnemyType2 extends Enemy{
    
     private static final String PATH_ENEMY = "spaceinvaders/Images/enemigo_2x65.png";
    public EnemyType2(int x, int y,GamePanel gamePanel) {
        super(x, y, 3, 20, 2, PATH_ENEMY, gamePanel);
    }
    
}
