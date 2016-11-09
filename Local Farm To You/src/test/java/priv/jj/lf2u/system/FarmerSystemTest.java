package priv.jj.lf2u.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import priv.jj.lf2u.role.Farmer;

import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/8/16.
 */
public class FarmerSystemTest {
    FarmerSystem fs = FarmerSystem.INSTANCE;

    @Test
    public void addFarmerTest() {
        /* set up */
        fs.clearStoredDate();

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
        fs.clearStoredDate();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});

        /* test */
        Farmer farmer = fs.farmOfFid("100");
        assertEquals("farmer doesn't exist", null, farmer);

        farmer = fs.farmOfFid("101");
        assertEquals("farmer found", "name1", farmer.getPersonName());
    }

    @Test
    public void putFarmerTest() {
        /* set up */
        fs.clearStoredDate();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});

        /* test */
        boolean s = fs.setFarmer("100","1", "1", "1", "1", "1", "1", "1", new String[]{});
        assertEquals("fail to put non-existing farmer", false, s);

        s = fs.setFarmer("101","1", "1", "1", "1", "1", "1", "1", new String[]{});
        assertEquals("correct feedback from successful put farmer", true, s);

        Farmer farmer = fs.farmOfFid("101");
        assertEquals("farmer has been changed", "1", farmer.getPersonName());
    }

    @Test
    public void zipTest() {
        /* set up */
        fs.clearStoredDate();
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
    public void dataIsPersistentTest() {
        Farmer farmer = fs.farmOfFid("101");
        assertEquals("farmer stored in local is found", "name1", farmer.getPersonName());
    }

    @After
    public void leave_something() {
        fs.clearStoredDate();
        fs.addFarmer("name1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"});
        fs.addFarmer("name2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"});
    }
}