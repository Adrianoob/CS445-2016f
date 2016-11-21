package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.FarmStoreProduct;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by adrianoob on 11/8/16.
 */
public class ProductSerialization implements ProductIOInterface {
    private final static String fileName = "ProductObject.ser";
    private ArrayList<FarmStoreProduct> products;

    public ProductSerialization() { products = new ArrayList<>(); }

    @Override
    public FarmStoreProduct[] readProductsData() {
        products.clear();
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                int size = in.readInt();
                for (int i = 0; i < size; i++) {
                    products.add((FarmStoreProduct) in.readObject());
                }
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return products.toArray(new FarmStoreProduct[products.size()]);
    }

    @Override
    public void addProduct(FarmStoreProduct p) {
        products.add(p);
        write();
    }



    @Override
    public void clearStoredData() {
        products.clear();
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void write() {
        File file = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeInt(products.size());
            for (FarmStoreProduct f : products) {
                out.writeObject(f);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
