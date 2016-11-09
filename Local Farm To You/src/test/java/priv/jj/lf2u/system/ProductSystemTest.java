package priv.jj.lf2u.system;

import org.junit.Before;
import org.junit.Test;
import priv.jj.lf2u.product.FarmStoreProduct;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public class ProductSystemTest {
    ProductSystem ps = ProductSystem.INSTANCE;
    FarmerSystem fs = FarmerSystem.INSTANCE;
    @Before
    public void setup() {
        // setup farm data
        fs.clearStoredDate();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});
        fs.addFarmer("name2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"});
    }


    @Test
    public void add_getProductTest() {
        String fspid = ps.addProduct("101", "125", "note", "start", "end", "price", "unit", "image");
        assertEquals("fspid is correct", "101", fspid);

        FarmStoreProduct p = ps.productOfId(fspid);
        assertEquals("catalog is correct", "Potatoes", p.getName());

        fspid = ps.addProduct("101", "126", "note", "start", "end", "price", "unit", "image");
        assertEquals("fspid is correct", "102", fspid);



        /* failure test */
        fspid = ps.addProduct("103", "127", "note", "start", "end", "price", "unit", "image");
        assertEquals("fid not exist", null, fspid);

        fspid = ps.addProduct("102", "100", "note", "start", "end", "price", "unit", "image");
        assertEquals("gpcid not exist", null, fspid);
    }

    @Test
    public void productsOfFidTest() {
        FarmStoreProduct [] products = ps.getProductsOfFid("101");
    }

}