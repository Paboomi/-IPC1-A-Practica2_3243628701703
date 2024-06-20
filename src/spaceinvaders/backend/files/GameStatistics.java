package spaceinvaders.backend.files;

/**
 *
 * @author saien
 */
import java.io.*;
import java.util.*;

public class GameStatistics {

    private static final String FILENAME = "src/spaceinvaders/Estadisticas/estadisticas.txt";

    // Método para guardar las estadísticas del jugador
    public static void savePlayerStatistics(String playerName, int score) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            writer.println(playerName + "," + score);
            System.out.println("Estadísticas guardadas en " + FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar estadísticas.");
        }
    }

    // Método para leer todas las estadísticas
    private static List<PlayerScore> readAllStatistics() {
        List<PlayerScore> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new PlayerScore(playerName, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al leer estadísticas.");
        }

        return scores;
    }

    // Método para obtener los 5 puntajes más altos utilizando ordenamiento por inserción
    public static List<PlayerScore> getTop5Scores() {
        List<PlayerScore> scores = readAllStatistics();
        insertionSort(scores);
        return scores.size() > 5 ? scores.subList(0, 5) : scores;
    }

    // Método de ordenamiento por inserción
    private static void insertionSort(List<PlayerScore> scores) {
        for (int i = 1; i < scores.size(); i++) {
            PlayerScore key = scores.get(i);
            int j = i - 1;
            while (j >= 0 && scores.get(j).getScore() < key.getScore()) {
                scores.set(j + 1, scores.get(j));
                j = j - 1;
            }
            scores.set(j + 1, key);
        }
    }

    // Clase interna para representar el puntaje de un jugador
    public static class PlayerScore {
        private final String playerName;
        private final int score;

        public PlayerScore(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }
    }
}

