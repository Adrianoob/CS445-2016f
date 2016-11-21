package priv.jj.lf2u.system;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import priv.jj.lf2u.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by adrianoob on 11/20/16.
 */
public class OrderSystemTest {
    FarmerSystem fs = FarmerSystem.INSTANCE;
    ProductSystem ps = ProductSystem.INSTANCE;
    CatalogSystem ca = CatalogSystem.INSTANCE;
    CustomerSystem cs = CustomerSystem.INSTANCE;
    OrderSystem os = OrderSystem.INSTANCE;
    String today;
    String tmr;

    @Before
    public void setup() {
        fs.clearStoredData();
        fs.addFarmer("farmer1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"}); // fid = 101
        fs.addFarmer("farmer2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60616", "60606"}); // fid = 102

        ca.clearStoredDate();
        ca.addCatalog("Potatoes"); // gcpid = 101
        ca.addCatalog("Eggplant"); // gcpid = 102;

        ps.clearStoredData();
        ps.addProduct("101", "102", "note", "start", "end", "price", "unit", "image"); // fspid = 101
        ps.addProduct("101", "101", "note", "start", "end", "price", "unit", "image"); // fspid = 102

        cs.clearStoredData();
        cs.addCustomer("cus1", "cus1e@aa.com", "1111111111", "Michigen Ave.", "60616"); // cid = 101
        cs.addCustomer("cus2", "cus2e@aa.com", "2222222222", "Michigen road.", "60616"); // cid = 102

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        today = format.format(calendar.getTime());
        calendar.roll(Calendar.DATE, 1);
        tmr = format.format(calendar.getTime());
    }

    @Test
    public void testCreateOrder() {
        // set up
        os.clearStoredData();
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "N/A", new String[] {"101"}, new double[] {0.5}); // oid = 102
        os.addOrder("102", "101", "N/A", new String[] {"102"}, new double[] {10}); // oid = 103

        assertEquals("fail to add order for non-existing cid", false, os.addOrder("103", "101", "", new String[]{}, new double[]{}));

        Order order = os.getOrder("102", "101");
        assertEquals("order 2 correct id", "102", order.getOid());
        assertEquals("order 2 correct planned delivery date", tmr, order.getPlannedDeliveryDate());
        assertEquals("order 2 correct order date", today, order.getOrderDate());
        assertEquals((new double[] {0.5})[0], order.getAmountList()[0], 0);
        assertEquals("order 2 correct status", Order.OPEN, order.getStatus());
    }

    @Test
    public void testGetOrders() {
        // set up
        os.clearStoredData();
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "N/A", new String[] {"101"}, new double[] {0.5}); // oid = 102
        os.addOrder("102", "101", "N/A", new String[] {"102"}, new double[] {10}); // oid = 103

        // test get orderS
        Order [] oo = os.ordersByCustomerOfCid("101");
        assertEquals("got 2 orders for cid = 101", 2, oo.length);
        oo = os.ordersByCustomerOfCid("102");
        assertEquals("got 1 orders for cid = 102", 1, oo.length);
        oo = os.ordersByCustomerOfCid("103");
        assertEquals("got 0 orders for cid = 103", 0, oo.length);

        // test get order
        Order order = os.getOrder("103", "102");
        assertEquals("order has correct fid", "101", order.getFid());
        order = os.getOrder("103", "101");
        assertEquals("oid and cid not match", null, order);
    }

    @Test
    public void testCancel_Confirm() {
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "N/A", new String[] {"101"}, new double[] {0.5}); // oid = 102

        Order or1 = os.getOrder("101", "101");
        assertEquals("succeed to cancel", true, or1.cancelOrder());
        assertEquals("cancelled item has correct status", Order.CANCELLED, or1.getStatus());
        Order or2 = os.getOrder("102", "101");
        assertEquals("succeed to confirm delivery", true, or2.confirmDelivery());
        assertEquals("confirmmed item has correct status", Order.DELIVERED, or2.getStatus());

        assertEquals("cancelled item can't be cancelled", false, or1.cancelOrder());
        assertEquals("confirmmed item can't be cancelled", false, or2.cancelOrder());

        assertEquals("cancelled item can't be confirmed", false, or1.confirmDelivery());
        assertEquals("confirmmed item can't be confirmed", false, or2.confirmDelivery());

        assertEquals("cancelled item won't change its status", Order.CANCELLED, or1.getStatus());
        assertEquals("confirmed item won't change its status", Order.DELIVERED, or2.getStatus());

    }
}