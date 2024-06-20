package spaceinvaders.backend.jugador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import spaceinvaders.frontend.util.ActualizarScore;

/**
 *
 * @author saien
 */
public class Jugador implements Serializable {

    private static final long serialVersionUID = 3L;
    private int puntaje;
    private String nombre;
    private transient ActualizarScore observador;

    public Jugador() {
        this.puntaje = 0;
    }

    private void updateScore() {
        if (observador != null) {
            observador.actualizarScore();
        } else {
            System.out.println("Sin observador inicializado");
        }
    }

    public void incrementPoints(int points) {
        this.puntaje += points;
        updateScore();
    }

    public void decrementPoints(int points) {
        if (puntaje <= 0) {
            this.puntaje = 0;
        } else {

            this.puntaje -= points;
        }
        updateScore();
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ActualizarScore getObservador() {
        return observador;
    }

    public void setObservador(ActualizarScore observador) {
        this.observador = observador;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }

}
