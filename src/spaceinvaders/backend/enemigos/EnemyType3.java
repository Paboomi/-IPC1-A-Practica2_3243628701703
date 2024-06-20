package spaceinvaders.backend.enemigos;

import spaceinvaders.frontend.GamePanel;

/**
 *
 * @author saien
 */
public class EnemyType3 extends Enemy{
    
   private static final String PATH_ENEMY = "spaceinvaders/Images/enemigo_3x65.png";
    public EnemyType3(int x, int y,GamePanel gamePanel) {
        super(x, y, 4, 30, 2, PATH_ENEMY, gamePanel);
    }
    
}
