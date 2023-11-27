package mancala;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class Saver {
    public static void saveObject(Serializable toSave, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("assets/" + filename))) {
            oos.writeObject(toSave);
        }
    }

    public static Serializable loadObject(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("assets/" + filename))) {
            return (Serializable) ois.readObject();
        }
    }
}