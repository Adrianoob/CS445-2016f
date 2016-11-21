package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.FarmStoreProduct;

/**
 * Created by adrianoob on 11/8/16.
 */
public interface ProductIOInterface {
    // read existing products from disk
    FarmStoreProduct[] readProductsData();

    // add a product
    void addProduct(FarmStoreProduct p);

    // clear stored data
    void clearStoredData();


}
