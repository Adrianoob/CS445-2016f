package priv.jj.lf2u.system;

import com.sun.org.apache.xpath.internal.operations.Or;
import priv.jj.lf2u.entity.Customer;
import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.persistence.OrderIOInterface;
import priv.jj.lf2u.persistence.OrderSerialization;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by adrianoob on 11/20/16.
 */
public enum OrderSystem {
    INSTANCE;
    private ProductSystem ps;
    private FarmerSystem fs;
    private CustomerSystem cs;


    private ArrayList<Order> orders;
    private int id_counter;
    private OrderIOInterface orderIO;

    private OrderSystem() {
        ps = ProductSystem.INSTANCE;
        fs = FarmerSystem.INSTANCE;
        cs = CustomerSystem.INSTANCE;
        orders = new ArrayList<>();
        id_counter = 100;
        orderIO = new OrderSerialization();

        loadStoredData();
    }

    private void loadStoredData() {
        Order [] orders = orderIO.readStoredData();
        for (Order order : orders) {
            this.orders.add(order);
            int id = Integer.parseInt(order.getOid());
            if (id > id_counter) id_counter = id;
        }
    }


    /* public methods */
    public boolean addOrder(String cid, String fid, String delivery_note, String[] fspidList, double[] amountList) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return false;

        Customer customer = cs.customerOfCid(cid);
        if (customer == null) return false;

        FarmStoreProduct [] proList = new FarmStoreProduct[fspidList.length];
//        int [] versions = new int[fspidList.length];
        for (int i = 0; i < fspidList.length; i++) {
            FarmStoreProduct product = ps.productOfId_newestVersion(fspidList[i], fid);
            if (product == null) return false;
//            versions[i] = product.getVersion();
            proList[i] = product;
        }
        Order order = new Order(customer, farmer, delivery_note, proList, amountList);
        order.setOid("" + ++id_counter);
        order.setDeliveryCharge(farmer.getDeliveryCharge());
//        order.setVersionList(versions);
        orders.add(order);
        orderIO.addOrder(order);
        return true;
    }

    public Order [] ordersTodayByFid(String fid) {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(Calendar.getInstance().getTime());
        for (Order o : orders) {
            if (o.getFid().equals(fid) && o.getPlannedDeliveryDate().equals(today)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] ordersTomorrowByFid(String fid) {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DATE, 1);
        String tomorrow = format.format(calendar.getTime());
        for (Order o : orders) {
            if (o.getFid().equals(fid) && o.getPlannedDeliveryDate().equals(tomorrow)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] ordersByCustomerOfCid(String cid) {
        ArrayList<Order> list = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCid().equals(cid)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order getOrder(String oid, String cid) {
        for (Order o : orders) {
            if (o.getOid().equals(oid) && o.getCid().equals(cid))
                return o;
        }
        return null;
    }

    public boolean cancelOrder(String oid, String cid) {
        Order o = getOrder(oid, cid);
        if (o == null) return false;

        boolean isSuccessful = o.cancelOrder();
        if (isSuccessful) orderIO.changeOrder(o);
        return isSuccessful;
    }

    public boolean confirmDelivery(String oid, String cid) {
        Order o = getOrder(oid, cid);
        if (o == null) return false;

        boolean isSuccessful = o.confirmDelivery();
        if (isSuccessful) orderIO.changeOrder(o);
        return isSuccessful;
    }

    public void clearStoredData() {
        orders.clear();
        id_counter = 100;
        orderIO.clearStoredDate();
    }

}
