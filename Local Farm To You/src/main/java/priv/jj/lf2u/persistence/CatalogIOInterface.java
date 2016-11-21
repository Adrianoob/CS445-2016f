package priv.jj.lf2u.persistence;

import java.util.Hashtable;

/**
 * Created by adrianoob on 11/8/16.
 */
public interface CatalogIOInterface {
    // read catalog from local
    Hashtable<String, String> readCatalogData();

    // add catalog
    void addCatalog(String gcpid, String name);

    // change catalog
    void changeCatalog(String gcpid, String name);

    // clear local data
    void clearStoredDate();
}
