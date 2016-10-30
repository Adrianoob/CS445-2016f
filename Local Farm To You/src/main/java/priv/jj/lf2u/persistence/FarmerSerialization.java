package priv.jj.lf2u.persistence;


import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by adrianoob on 10/30/16.
 */
public class FarmerSerialization implements FarmerIOInterface {
    private String fileName;
    public FarmerSerialization() {
        fileName = "FarmerObjects.ser";
    }

    public void addFarmer(Hashtable<String, String> farmer) {
        File file = new File(fileName);
        if (fileExists(fileName)) {
            try (AppendingObjectOutputStream out = new AppendingObjectOutputStream(new FileOutputStream(file, true))) {
                out.writeObject(farmer);
            } catch (IOException i) {
                i.printStackTrace();
            }
        } else {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
                out.writeObject(farmer);
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public void loadFarmers() {
        File file = new File(fileName);
        ArrayList<Hashtable<String, String>> list = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Hashtable table = (Hashtable<String, String>) in.readObject();
            list.add(table);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    public boolean fileExists(String file_name) {
        File file = new File(file_name);
        return file.exists() && !file.isDirectory();
    }
}
