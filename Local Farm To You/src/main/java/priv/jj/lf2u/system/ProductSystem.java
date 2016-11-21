package priv.jj.lf2u.system;

import priv.jj.lf2u.persistence.ProductIOInterface;
import priv.jj.lf2u.persistence.ProductSerialization;
import priv.jj.lf2u.entity.FarmStoreProduct;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by adrianoob on 10/29/16.
 */
public enum ProductSystem {
    INSTANCE;

    private int id_counter;
    private ArrayList<FarmStoreProduct> products;
    private FarmerSystem fs = FarmerSystem.INSTANCE;
    private ProductIOInterface productIO;

    // CatalogSystem
    private CatalogSystem catalog = CatalogSystem.INSTANCE;

    private ProductSystem() {
        products = new ArrayList<>();
        id_counter = 100;
        productIO = new ProductSerialization();

        readStoredData();
    }

    private void readStoredData() {
        FarmStoreProduct [] ps = productIO.readProductsData();
        for (FarmStoreProduct p : ps) {
            products.add(p);
            int id = Integer.parseInt(p.getFspid());
            if (id > id_counter) id_counter = id;
        }
    }

    public String addProduct(String fid, String gcpid, String note, String start, String end,
                             String price, String unit, String image) {
        if (fs.farmOfFid(fid) != null) {
            String fspid = "" + ++id_counter;
            String name = catalog.catalogOfId(gcpid);
            if (name != null) {
                FarmStoreProduct product = new FarmStoreProduct(fid, name, note, start, end, price, unit, image);
                product.setFspid(fspid);
                products.add(product);
                productIO.addProduct(product);
                return fspid;
            }
        }
        return null;
    }

    public boolean changeProductOfId(String fid, String fspid, Hashtable<String, String> data) {
        FarmStoreProduct product = productOfId_newestVersion(fspid);
        if (product == null || !product.getFid().equals(fid))
            return false;

        FarmStoreProduct copy = product.copy();
        if (data.get("note")        != null) copy.setNote(data.get("note"));
        if (data.get("start_date")  != null) copy.setStart_date(data.get("start_date"));
        if (data.get("end_date")    != null) copy.setEnd_date(data.get("end_date"));
        if (data.get("price")       != null) copy.setPrice(data.get("price"));
        if (data.get("product_unit")!= null) copy.setProduct_unit(data.get("product_unit"));
        if (data.get("image")       != null) copy.setImage(data.get("image"));
        products.add(copy);
        productIO.addProduct(copy);
        return true;
    }

    public FarmStoreProduct productOfId_newestVersion(String fspid) {
        FarmStoreProduct product = null;
        int v = -1;
        for (FarmStoreProduct p : products) {
            if (p.getFspid().equals(fspid) && p.getVersion() > v) {
                v = p.getVersion();
                product = p;
            }
        }
        return product;
    }

    public FarmStoreProduct productOfId_version(String fspid, int version) {
        for (FarmStoreProduct p : products) {
            if (p.getFspid().equals(fspid) && p.getVersion() == version)
                return p;
        }
        return null;
    }

    public FarmStoreProduct[] getProductsOfFid(String fid) {
        ArrayList<FarmStoreProduct> list = new ArrayList<>();
        for (FarmStoreProduct p : products) {
            if (p.getFid().equals(fid)) list.add(p);
        }
        return list.toArray(new FarmStoreProduct[list.size()]);
    }

    public void clearStoredData() {
        products.clear();
        id_counter = 100;
        productIO.clearStoredData();
    }
}
