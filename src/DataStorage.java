import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class DataStorage implements Serializable {
    private HashMap<String, Page> urlToPage;
    private HashSet<String> uniqueWords;

    public DataStorage(){
        this.uniqueWords = new HashSet<String>();
        this.urlToPage = new HashMap<String, Page>();
    }

    public HashMap<String, Page> getUrlToPageMap() {return urlToPage;}
    public HashSet<String> getUniqueWords(){return uniqueWords;}

    public void saveToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dataStorage.dat"));
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataStorage readFromFile() {
        DataStorage dataStorage = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("dataStorage.dat"));
            dataStorage = (DataStorage) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataStorage;
    }
}
