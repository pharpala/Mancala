package mancala;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class Saver {
    private static final String SAVED_GAMES_DIR = "assets/savedGames/"; //idk if this will work with teacher


    public static void saveObject(Serializable toSave, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVED_GAMES_DIR + filename))) {
            oos.writeObject(toSave);
        }
    }
    

    public static Serializable loadObject(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVED_GAMES_DIR + filename))) {
            return (Serializable) ois.readObject();
        }
    }
}