package priv.jj.lf2u.persistence;


import priv.jj.lf2u.entity.Farmer;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by adrianoob on 10/30/16.
 */
public class FarmerSerialization implements FarmerIOInterface {
    private final static String fileName = "FarmerObjects.ser";
    private ArrayList<Farmer> farmers;

    public FarmerSerialization() {
        farmers = new ArrayList<>();
    }

    public Farmer [] readFarmersData() {
        farmers.clear();
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                int size = in.readInt();
                for (int i = 0; i < size; i++) {
                    farmers.add((Farmer) in.readObject());
                }
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return farmers.toArray(new Farmer[farmers.size()]);
    }

    public void addFarmer(Farmer farmer) {
        farmers.add(farmer);
        write();
    }

    public void setFarmer(Farmer farmer) {
        Farmer f_to_change = null;
        for (Farmer f : farmers) {
            if (farmer.getFid().equals(f.getFid())) {
                f_to_change =f;
                break;
            }
        }
        farmers.remove(f_to_change);
        farmers.add(farmer);
        write();
    }

    public void clearStoredData() {
        farmers.clear();
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void write() {
        File file = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeInt(farmers.size());
            for (Farmer f : farmers) {
                out.writeObject(f);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
