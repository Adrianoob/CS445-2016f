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
        assertEquals("email is changed corrected", "newEmail3@aa.com", c.getEmail());
    }

    @Test
    public void persistenceTest() {
        Customer farmer = cs.customerOfCid("103");
        assertEquals("customer stored in local is found", "email3@aa.com", farmer.getEmail());

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