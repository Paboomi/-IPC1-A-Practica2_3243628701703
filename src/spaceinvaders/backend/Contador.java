package spaceinvaders.backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;
import spaceinvaders.frontend.util.ActualizarTimer;

/**
 *
 * @author saien
 */
public class Contador implements Serializable {

    private int count;
    private Timer timer;
    private ActionListener onTick;
    private Runnable onFinish;
    private ActualizarTimer observador;

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

    public void addTime(int seconds) {
        count += seconds;
        // Si se añade tiempo cuando el contador estaba en 0 y ya había terminado, se reinicia
        if (count > 0 && !timer.isRunning()) {
            timer.start();
        }
        updateTimer();
    }
    
    public void decrementTime(int seconds) {
        count -= seconds;
        // Si se añade tiempo cuando el contador estaba en 0 y ya había terminado, se reinicia
        if (count <= 0) {
            count = 0;
            timer.stop();
            onFinish.run();
        }
        updateTimer();
    }

    private void updateTimer() {
        if (observador != null) {
            observador.actualizarTimer();
        }
    }

    public void start() {
        timer.start();
    }

    public int getCount() {
        return count;
    }

}
