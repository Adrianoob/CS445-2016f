package priv.jj.lf2u.persistence;

import java.io.*;
import java.util.Hashtable;

/**
 * Created by adrianoob on 11/8/16.
 */
public class CatalogSerialization implements CatalogIOInterface {
    private Hashtable<String, String> catalog;
    private final static String fileName = "CatalogSystem.ser";

    public CatalogSerialization() { catalog = new Hashtable<>(); }

    @Override
    public Hashtable<String, String> readCatalogData() {
        catalog.clear();
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                catalog = (Hashtable<String, String>) in.readObject();
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return catalog;
    }

    @Override
    public void addCatalog(String gcpid, String name) {
        write();
    }

    @Override
    public void changeCatalog(String gcpid, String name) {
        write();
    }

    @Override
    public void clearStoredDate() {
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void write() {
        File file = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(catalog);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
