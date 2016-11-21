package priv.jj.lf2u.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import priv.jj.lf2u.entity.FarmStoreProduct;

import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public class ProductSystemTest {
    private ProductSystem ps = ProductSystem.INSTANCE;
    private FarmerSystem fs = FarmerSystem.INSTANCE;
    private CatalogSystem catalog = CatalogSystem.INSTANCE;
    private boolean last_test = false;

    @Before
    public void setup() {
        // setup farm data
        fs.clearStoredData();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});
        fs.addFarmer("name2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"});

        // setup catalog data
        catalog.clearStoredDate();
        catalog.addCatalog("Potatoes");
        catalog.addCatalog("Eggplant");
        catalog.addCatalog("Tomatoes");

    }


    @Test
    public void add_getProductTest() {
        ps.clearStoredData();
        String fspid = ps.addProduct("101", "103", "note", "start", "end", "price", "unit", "image");
        assertEquals("fspid is correct1", "101", fspid);

        FarmStoreProduct p = ps.productOfId_newestVersion(fspid);
        assertEquals("catalog is correct", "Tomatoes", p.getName());

        fspid = ps.addProduct("101", "101", "note", "start", "end", "price", "unit", "image");
        assertEquals("fspid is correct2", "102", fspid);



        /* failure test */
        fspid = ps.addProduct("103", "101", "note", "start", "end", "price", "unit", "image");
        assertEquals("fail as fid not exist", null, fspid);

        fspid = ps.addProduct("102", "100", "note", "start", "end", "price", "unit", "image");
        assertEquals("fail as gpcid not exist", null, fspid);
    }

    @Test
    public void changeProductTest() {
        // set up
        ps.clearStoredData();
        ps.addProduct("101", "103", "note", "start", "end", "price", "unit", "image"); // fspid = 101
        ps.addProduct("101", "101", "note", "start", "end", "price", "unit", "image"); // fspid = 102

        // test
        Hashtable<String, String> data = new Hashtable<>();
        data.put("price", "12");
        assertEquals("effective product first change", true, ps.changeProductOfId("101", "101", data));
        FarmStoreProduct pr = ps.productOfId_newestVersion("101");
        assertEquals("product version is 1", 1, pr.getVersion());
        assertEquals("price changed", "12", pr.getPrice());

        data.put("price", "1.2");
        data.put("product_unit", "kg");

        assertEquals("effective product second change", true, ps.changeProductOfId("101", "101", data));
        pr = ps.productOfId_newestVersion("101");
        assertEquals("product version is 2", 2, pr.getVersion());
        assertEquals("price changed again", "1.2", pr.getPrice());
        assertEquals("unit changed", "kg", pr.getProduct_unit());

        assertEquals("failure in product change for non-existing fspid",
                false, ps.changeProductOfId("101", "103", data));
        assertEquals("failure in product chagne for non-existing fid",
                false, ps.changeProductOfId("102", "101", data));

        // test version
        pr = ps.productOfId_version("101", 1);
        assertEquals("old version has correct price", "12", pr.getPrice());
    }

    @Test
    public void productsOfFidTest() {
        // set up
        ps.clearStoredData();
        ps.addProduct("101", "103", "note", "start", "end", "price", "unit", "image"); // fid=101, fspid=101
        ps.addProduct("101", "101", "note", "start", "end", "price", "unit", "image"); // fid=101, fspid=102
        ps.addProduct("101", "102", "note", "start", "end", "price", "unit", "image"); // fid=101, fspid=103
        ps.addProduct("102", "101", "note", "start", "end", "price", "unit", "image"); // fid=102, fspid=104
        ps.addProduct("102", "102", "note", "start", "end", "price", "unit", "image"); // fid=102, fspid=105

        // test
        FarmStoreProduct [] pls = ps.getProductsOfFid("101");
        assertEquals("list length correct", 3, pls.length);
        assertEquals("item1 has correct fspid", "101", pls[0].getFspid());
        assertEquals("item2 has correct fspid", "103", pls[2].getFspid());

        pls = ps.getProductsOfFid("102");
        assertEquals("list length correct", 2, pls.length);

        pls = ps.getProductsOfFid("103");
        assertEquals("list length correct", 0, pls.length);
    }

    @Test
    public void persistenceTest() {
        assertNotEquals("old data exists1", null, ps.productOfId_newestVersion("101"));
        assertNotEquals("old data exists2", null, ps.productOfId_newestVersion("102"));
        assertEquals("no more old data", null, ps.productOfId_newestVersion("103"));
        last_test = true;
    }

    @After
    public void leave_something() {
        if (last_test) {
            fs.clearStoredData();
            ps.clearStoredData();
            catalog.clearStoredDate();
        } else {
            ps.clearStoredData();
            ps.addProduct("101", "103", "note", "start", "end", "price", "unit", "image"); // fspid = 101
            ps.addProduct("101", "101", "note", "start", "end", "price", "unit", "image"); // fspid = 102
        }
    }

}