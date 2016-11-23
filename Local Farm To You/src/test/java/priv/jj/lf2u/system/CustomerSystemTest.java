package priv.jj.lf2u.system;

import org.junit.After;
import org.junit.Test;
import priv.jj.lf2u.entity.Customer;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/20/16.
 */
public class CustomerSystemTest {
    private CustomerSystem cs = CustomerSystem.INSTANCE;
    private boolean last_test = false;

    @Test
    public void add_getFarmerTest() {
        /* set up */
        cs.clearStoredData();

        /* test */
        String cid = cs.addCustomer("name1", "email1@aa.com", "1111111111", "Michigen Ave.", "60616");
        assertEquals("fid is correct", "101", cid);

        cid = cs.addCustomer("name2", "email2@aa.com", "2222222222", "Michigen road.", "60616");
        assertEquals("fid is correct", "102", cid);

        Customer c = cs.customerOfCid(cid);
        assertNotNull("customer is not null", c);
        assertEquals("find wanted customer", "name2", c.getName());
    }

    @Test
    public void changeCustomerInfoTest() {
        /* set up */
        cs.clearStoredData();
        cs.addCustomer("name1", "email1@aa.com", "1111111111", "Michigen Ave.", "60616");
        cs.addCustomer("name2", "email2@aa.com", "2222222222", "Michigen road.", "60616");
        cs.addCustomer("name3", "email3@aa.com", "3333333333", "Sang road.", "315000");

        assertEquals("cid not exist", false, cs.setCustomer("105", "newName3", "newEmail3@aa.com", "3303303330", "3 New Road", "315003"));
        assertEquals("cid not exist", true, cs.setCustomer("103", "newName3", "newEmail3@aa.com", "3303303330", "3 New Road", "315003"));

        Customer c = cs.customerOfCid("103");
        assertNotNull("customer not null", c);
        assertEquals("email is changed corrected", "newEmail3@aa.com", c.getEmail());
    }

    @Test
    public void searchTest() {
        /* set up */
        cs.clearStoredData();
        cs.addCustomer("Will", "will@aa.com", "1111111111", "Michigen Ave.", "60616");
        cs.addCustomer("Sam", "sam@bb.com", "2222222222", "Michigen road.", "60616");
        cs.addCustomer("Dion", "dion@cc.com", "3333333333", "Sang road.", "315000");

        assertEquals("search test 1", 0, cs.customersByKeyword("123").length);
        assertEquals("search test 2", 3, cs.customersByKeyword("").length);
        assertEquals("search test 2", 3, cs.customersByKeyword(null).length);
        assertEquals("search test 3", 3, cs.customersByKeyword(".com").length);
        assertEquals("search test 4", 2, cs.customersByKeyword("ichi").length);
        assertEquals("search test 5", 2, cs.customersByKeyword("road").length);
        assertEquals("search test 6", 2, cs.customersByKeyword("6061").length);
    }

    @Test
    public void persistenceTest() {
        Customer cus = cs.customerOfCid("103");
        assertNotNull("customer not null", cus);
        assertEquals("customer stored in local is found", "email3@aa.com", cus.getEmail());

        last_test = true;
    }

    @After
    public void leave_something() {
        cs.clearStoredData();
        if (!last_test) {
            cs.addCustomer("name1", "email1@aa.com", "1111111111", "Michigen Ave.", "60616");
            cs.addCustomer("name2", "email2@aa.com", "2222222222", "Michigen road.", "60616");
            cs.addCustomer("name3", "email3@aa.com", "3333333333", "Sang road.", "315000");
        }
    }


}