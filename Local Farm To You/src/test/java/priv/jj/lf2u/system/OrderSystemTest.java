package priv.jj.lf2u.system;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import priv.jj.lf2u.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

/**
 * Created by adrianoob on 11/20/16.
 */
public class OrderSystemTest {
    private FarmerSystem fs = FarmerSystem.INSTANCE;
    private ProductSystem ps = ProductSystem.INSTANCE;
    private CatalogSystem ca = CatalogSystem.INSTANCE;
    private CustomerSystem cs = CustomerSystem.INSTANCE;
    private OrderSystem os = OrderSystem.INSTANCE;
    private String today;
    private String tmr;

    boolean last_test = false;

    @Before
    public void setup() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        today = format.format(calendar.getTime());
        calendar.roll(Calendar.DATE, 1);
        tmr = format.format(calendar.getTime());
    }

    private void loadingData() {
        fs.clearStoredData();
        fs.addFarmer("farmer1", "email1@aa.com", "1111111111",
                "farm1", "address1", "1111111112", "www.farm1.com", new String[]{"60616", "60647"}); // fid = 101
        fs.addFarmer("farmer2", "email2@aa.com", "2222222222",
                "farm2", "address2", "2222222223", "www.farm2.com", new String[]{"60010", "60606"}); // fid = 102

        ca.clearStoredDate();
        ca.addCatalog("Potatoes"); // gcpid = 101
        ca.addCatalog("Eggplant"); // gcpid = 102;

        ps.clearStoredData();
        ps.addProduct("101", "102", "note", "", "", 12, "unit", "image"); // fspid = 101
        ps.addProduct("101", "101", "note", "", "", 25, "unit", "image"); // fspid = 102

        cs.clearStoredData();
        cs.addCustomer("cus1", "cus1e@aa.com", "1111111111", "Michigen Ave.", "60616"); // cid = 101
        cs.addCustomer("cus2", "cus2e@aa.com", "2222222222", "Michigen road.", "60616"); // cid = 102
    }

    @Test
    public void testCreateOrder() {
        // set up
        loadingData();
        os.clearStoredData();
        String id;
        id = os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        assertEquals("order 101 created successfully", "101", id);
        id = os.addOrder("101", "101", "N/A", new String[] {"101"}, new double[] {0.5}); // oid = 102
        assertEquals("order 102 created successfully", "102", id);
        id = os.addOrder("102", "101", "N/A", new String[] {"102"}, new double[] {10}); // oid = 103
        assertEquals("order 103 created successfully", "103", id);

        Order order = os.getOrder("102", "101");
        assertEquals("order 2 correct id", "102", order.getOid());
        assertEquals("order 2 correct planned delivery date", tmr, order.getPlannedDeliveryDate());
        assertEquals("order 2 correct order date", today, order.getOrderDate());
        assertEquals((new double[] {0.5})[0], order.getAmountList()[0], 0);
        assertEquals("order 2 correct status", Order.OPEN, order.getStatus());

        // fail tests
        assertEquals("fail to add order for non-existing cid", "-1", os.addOrder("103", "101", "", new String[]{}, new double[]{}));
        assertEquals("fail to add order for non-existing fid", "-1", os.addOrder("102", "103", "", new String[]{}, new double[]{}));
        assertEquals("fail to add order for unmatched zip code", "0", os.addOrder("102", "102", "", new String[]{}, new double[]{}));
        assertEquals("fail to add order for unmatched farm and product", "-1", os.addOrder("102", "102", "", new String[]{"101"}, new double[]{1.2}));

    }

    @Test
    public void testGetOrders() {
        // set up
        loadingData();
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
        // set up
        loadingData();
        os.clearStoredData();
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "N/A", new String[] {"101"}, new double[] {0.5}); // oid = 102

        // test
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

    @Test
    public void testCalculatingProductTotal() {
        // set up
        loadingData();
        os.clearStoredData();
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        Order order = os.getOrder("101", "101");

        // test
        assertEquals("total price is correct", 62, order.productTotal(), 0);

        Hashtable<String, Object> data = new Hashtable<>();
        data.put("price", new Double(13));
        ps.changeProductOfId("101", "101", data);
        assertEquals("total price won't be affected by changing price", 62, order.productTotal(), 0);

        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        order = os.getOrder("102", "101");
        assertEquals("new price will affect new order's total", 63, order.productTotal(), 0);
    }

    @Test
    public void searchTest() {
        os.clearStoredData();
        loadingData();
        os.addOrder("101", "101", "thank", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "Thank You", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 102
        os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 103

        assertEquals("search test 1", 3, os.ordersByKeyword("").length);
        assertEquals("search test 2", 1, os.ordersByKeyword("102").length);
        assertEquals("search test 3", 1, os.ordersByKeyword("thank ").length);
        assertEquals("search test 4", 0, os.ordersByKeyword("100").length);
        assertEquals("search test 5", 3, os.ordersByKeyword("a").length);


    }

    @Test
    public void orderStatusTest() {
        os.clearStoredData();
        loadingData();
        os.addOrder("101", "101", "thank", new String[] {"101"}, new double[] {1}); // oid = 101

        boolean worked;
        Order or = os.getOrder("101", "101");
        assertEquals("Status Open initially", Order.OPEN, or.getStatus());
        worked = os.cancelOrder("101", "101");
        assertEquals("Cancellation takes effect", true, worked);
        assertEquals("Status is cancelled", Order.CANCELLED, or.getStatus());
        worked = os.cancelOrder("101", "101");
        assertEquals("can't cancel cancelled order", false, worked);
        assertEquals("Status is cancelled", Order.CANCELLED, or.getStatus());
        worked = os.confirmDelivery("101");
        assertEquals("can't confirm cancelled order", false, worked);
        assertEquals("Status is cancelled", Order.CANCELLED, or.getStatus());

        String a = os.addOrder("102", "101", "thank", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 102
        or = os.getOrder("102", "102");
        assertEquals("Status Open initially", Order.OPEN, or.getStatus());
        worked = os.confirmDelivery("102");
        assertEquals("Cancellation takes effect", true, worked);
        assertEquals("Status is cancelled", Order.DELIVERED, or.getStatus());
        worked = os.cancelOrder("102", "102");
        assertEquals("can't cancel delivered order", false, worked);
        assertEquals("Status is delivered", Order.DELIVERED, or.getStatus());
        worked = os.confirmDelivery("102");
        assertEquals("can't confirm delivered order", false, worked);
        assertEquals("Status is delivered", Order.DELIVERED, or.getStatus());

    }

    @Test
    public void ordersByDateTest() {
        // set up
        os.clearStoredData();
        loadingData();
        os.addOrder("101", "101", "thank", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        os.addOrder("101", "101", "Thank You", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 102
        os.addOrder("102", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 103

        Order[] ors = os.ordersToDeliverTodayByFid("101");
        assertEquals("get correct orders", 0, ors.length);
        ors = os.ordersToDeliverTomorrowByFid("101");
        assertEquals("get correct orders", 3, ors.length);

        ors = os.ordersByCustomerOfCid("101");
        assertEquals("get correct orders", 2, ors.length);
        ors = os.ordersByCustomerOfCid("102");
        assertEquals("get correct orders", 1, ors.length);

        ors = os.allOrdersPlacedToday();
        assertEquals("get correct orders", 3, ors.length);
        ors = os.allOrdersLastMonth();
        assertEquals("get correct orders", 0, ors.length);
        ors = os.allOrdersPlacedYesterday();
        assertEquals("get correct orders", 0, ors.length);
    }

    @Test
    public void persistenceTest() {
        Order order = os.getOrder("101", "101");
        assertEquals("order from last time exists", order.getFid(), "101");
        last_test = true;
    }

    @After
    public void leave_something() {
        if (last_test) {
            fs.clearStoredData();
            ps.clearStoredData();
            ca.clearStoredDate();
            cs.clearStoredData();
            os.clearStoredData();
        } else {
            os.clearStoredData();
            os.addOrder("101", "101", "N/A", new String[] {"101", "102"}, new double[] {1, 2}); // oid = 101
        }
    }
}