package priv.jj.lf2u.system;

import priv.jj.lf2u.persistence.CatalogIOInterface;
import priv.jj.lf2u.persistence.CatalogSerialization;

import java.util.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public enum CatalogSystem {
    INSTANCE;
    private Hashtable<String, String> catalog; // id:name
    private int id_counter;
    private CatalogIOInterface catalogIO;

    CatalogSystem() {
        catalogIO = new CatalogSerialization();
        id_counter = 100;

        readLocalData();
    }

    private void readLocalData() {
        catalog = catalogIO.readCatalogData();
        Set<String> keys = catalog.keySet();
        for (String k : keys) {
            int id = Integer.parseInt(k);
            if (id > id_counter)
                id_counter = id;
        }
    }

    public Map<String, String> [] getEntireCatalog() {
        Set<String> keys = catalog.keySet();
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (String k : keys) {
            Map<String, String> a = new java.util.LinkedHashMap<>();
            a.put("gcpid", k);
            a.put("name", catalog.get(k));
            list.add(a);
        }
        Collections.reverse(list);
        return list.toArray(new Map[list.size()]);
    }

    public String catalogOfId(String gcpid) {
        return catalog.get(gcpid);
    }

    public String addCatalog(String name) {
        String id = "" + ++id_counter;
        catalog.put(id, name);
        catalogIO.addCatalog(id, name);
        return id;
    }

    public boolean changeCatalog(String gcpid, String name) {
        if (catalog.get(gcpid) == null)
            return false;

        catalog.put(gcpid, name);
        catalogIO.changeCatalog(gcpid, name);
        return true;
    }

    /* IO Method */
    public void setCatalogIO(CatalogIOInterface io) { catalogIO = io; }

    public void clearStoredDate() {
        catalog.clear();
        catalogIO.clearStoredDate();
        id_counter = 100;
    }
}
