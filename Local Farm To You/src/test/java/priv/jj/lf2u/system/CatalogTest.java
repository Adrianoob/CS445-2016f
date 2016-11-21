package priv.jj.lf2u.system;

import org.junit.After;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public class CatalogTest {
    private CatalogSystem ca = CatalogSystem.INSTANCE;
    private boolean last_test = false;

    @Test
    public void add_getCatalogTest() {
        // set up
        ca.clearStoredDate();

        // test
        String gpcid = ca.addCatalog("Potatoes");
        assertEquals("gpcid is correct1", "101", gpcid);

        gpcid = ca.addCatalog("Eggplant");
        assertEquals("gpcid is correct2", "102", gpcid);

        assertEquals("get name correctly 1", "Potatoes", ca.catalogOfId("101"));
        assertEquals("get name correctly 2", "Eggplant", ca.catalogOfId("102"));
        assertEquals("fail to find non-existing catalog", null, ca.catalogOfId("103"));
    }

    @Test
    public void entireCatalogTest() {
        // set up
        ca.clearStoredDate();
        ca.addCatalog("Potatoes");
        ca.addCatalog("Eggplant");
        Map<String,String>[] catalogs = ca.getEntireCatalog();

        // test
        assertEquals("correct catalog size", 2, catalogs.length);
        assertEquals("correct gpcid 1", "101", catalogs[0].get("gcpid"));
        assertEquals("correct name 1", "Potatoes", catalogs[0].get("name"));
        assertEquals("correct gpcid 2", "102", catalogs[1].get("gcpid"));
        assertEquals("correct name 2", "Eggplant", catalogs[1].get("name"));
    }

    @Test
    public void changeCatalogTest() {
        // set up
        ca.clearStoredDate();
        ca.addCatalog("Potatoes");
        ca.addCatalog("Eggplant");

        // test
        assertEquals("effective change in catalog 1", true, ca.changeCatalog("101", "Meat"));
        assertEquals("effective change in catalog 2", "Meat", ca.catalogOfId("101"));

        assertEquals("fail change in catalog 1", false, ca.changeCatalog("103", "Meat"));
        assertEquals("fail change in catalog 2", null, ca.catalogOfId("103"));
    }

    @Test
    public void persistenceTest() {
        assertEquals("catalog 101 exists", "Potatoes", ca.catalogOfId("101"));
        assertEquals("catalog 102 exists", "Eggplant", ca.catalogOfId("102"));
        assertEquals("catalog 103 not exists", null, ca.catalogOfId("103"));

        last_test = true;
    }

    @After
    public void leave_something() {
        ca.clearStoredDate();
        if (!last_test) {
            ca.addCatalog("Potatoes");
            ca.addCatalog("Eggplant");
        }
    }

}