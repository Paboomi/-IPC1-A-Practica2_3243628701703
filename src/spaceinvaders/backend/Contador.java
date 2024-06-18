package spaceinvaders.backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;

/**
 *
 * @author saien
 */
public class Contador implements Serializable {

    private int count;
    private Timer timer;
    private ActionListener onTick;
    private Runnable onFinish;

    public Contador(int startCont, ActionListener onTick, Runnable onFinish) {
        this.count = startCont;
        this.onTick = onTick;
        this.onFinish = onFinish;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                if (count >= 0) {
                    onTick.actionPerformed(e);
                } else {
                    timer.stop();
                    onFinish.run();
                }
            }
        });
    }

    public void start() {
        timer.start();
    }

    public int getCount() {
        return count;
    }

}
