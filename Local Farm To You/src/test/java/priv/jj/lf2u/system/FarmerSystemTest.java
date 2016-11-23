package priv.jj.lf2u.system;

import org.junit.After;
import org.junit.Test;
import priv.jj.lf2u.entity.Farmer;

import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public class FarmerSystemTest {
    private FarmerSystem fs = FarmerSystem.INSTANCE;
    private boolean last_test = false;

    @Test
    public void addFarmerTest() {
        /* set up */
        fs.clearStoredData();

        /* test */
        String fid = fs.addFarmer("name3", "email3@aa.com", "3333333333",
                "farm3", "address3", "3333333334", "www.farm3.com", new String[]{"33333", "33334"});
        assertEquals("fid is correct", "101", fid);

        Hashtable<String, Object> data = new Hashtable<>();
        data.put("personName", "name4");
        data.put("personEmail", "email4@aaa.com");
        data.put("personPhone", "4444444444");
        data.put("farmName", "farm4");
        data.put("farmAddress", "address4");
        data.put("farmPhone", "4444444445");
        data.put("farmWeb", "www.farm4.com");
        data.put("deliversTo", new String[]{"60616"});
        fid = fs.addFarmer(data);
        assertEquals("fid is correct", "102", fid);
    }

    @Test
    public void getAFarmerTest() {
        /* set up */
        fs.clearStoredData();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});

        /* test */
        Farmer farmer = fs.farmerOfFid("100");
        assertEquals("farmer doesn't exist", null, farmer);

        farmer = fs.farmerOfFid("101");
        assertEquals("farmer found", "name1", farmer.getPersonName());
    }

    @Test
    public void putFarmerTest() {
        /* set up */
        fs.clearStoredData();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});

        /* test */
        boolean s = fs.setFarmer("100","1", "1", "1", "1", "1", "1", "1", new String[]{});
        assertEquals("fail to put non-existing farmer", false, s);

        s = fs.setFarmer("101","1", "1", "1", "1", "1", "1", "1", new String[]{});
        assertEquals("correct feedback from successful put farmer", true, s);

        Farmer farmer = fs.farmerOfFid("101");
        assertEquals("farmer has been changed", "1", farmer.getPersonName());
    }

    @Test
    public void zipTest() {
        /* set up */
        fs.clearStoredData();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});
        fs.addFarmer("name2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"});

        /* test */
        Farmer [] farmers = fs.farmersOfZip("60000");
        assertEquals("no farmers found in this zone", farmers.length, 0);

        farmers = fs.farmersOfZip("60616");
        assertEquals("farmer1 found in this zone", farmers[0].getPersonName(), "name1");
        assertEquals("farmer2 found in this zone", farmers[1].getPersonName(), "name2");
    }

    @Test
    public void searchTest() {
        /* set up */
        fs.clearStoredData();
        fs.addFarmer("abc", "hh@aa.com", "131-232-4323",
                "ABC's Farm", "Fa's Road", "131-232-4323", "www.abc.com", new String[]{"60616", "60647"});
        fs.addFarmer("Will", "will123@gg.com", "667-768-9878",
                "Freshest Market", "Main Street", "667-768-9878", "www.will.com", new String[]{"60616", "60606"});
        fs.addFarmer("Seth", "seth@gmail.com", "555-555-5555",
                "Vegan World", "Main Street", "555-555-5555", "www.seth.com", new String[]{"60606", "60647", "60616"});

        assertEquals("search test 1", 3, fs.farmersByKeyword("@").length);
        assertEquals("search test 2", 3, fs.farmersByKeyword("www").length);
        assertEquals("search test 3", 1, fs.farmersByKeyword("abc's").length);
        assertEquals("search test 4", 0, fs.farmersByKeyword("--").length);
        assertEquals("search test 5", 3, fs.farmersByKeyword("").length);
        assertEquals("search test 6", 3, fs.farmersByKeyword("60616").length);
        assertEquals("search test 6", 2, fs.farmersByKeyword("60606").length);
    }

    @Test
    public void persistenceTest() {
        Farmer farmer = fs.farmerOfFid("101");
        assertEquals("farmer stored in local is found", "name1", farmer.getPersonName());
        farmer = fs.farmerOfFid("102");
        assertEquals("farmer stored in local is found", "1", farmer.getPersonName());


        last_test = true;
    }

    @After
    public void leave_something() {
        fs.clearStoredData();
        if (!last_test) {
            fs.addFarmer("name1", "email1@aa.com", "1111111111",
                    "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});
            fs.addFarmer("name2", "email2@aa.com", "2222222222",
                    "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"});
            fs.setFarmer("102","1", "1", "1", "1", "1", "1", "1", new String[]{});
        }
    }
}