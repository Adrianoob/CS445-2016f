package priv.jj.lf2u.system;

import priv.jj.lf2u.entity.Customer;
import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.entity.Order;

import java.util.ArrayList;

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

    private OrderSystem() {
        ps = ProductSystem.INSTANCE;
        fs = FarmerSystem.INSTANCE;
        cs = CustomerSystem.INSTANCE;
        orders = new ArrayList<>();
        id_counter = 100;
    }


    /* public methods */
    public boolean addOrder(String cid, String fid, String delivery_note, String[] fspidList, double[] amountList) {
        Farmer farmer = fs.farmOfFid(fid);
        if (farmer == null) return false;

        Customer customer = cs.customerOfCid(cid);
        if (customer == null) return false;

        FarmStoreProduct [] proList = new FarmStoreProduct[fspidList.length];
        int [] versions = new int[fspidList.length];
        for (int i = 0; i < versions.length; i++) {
            FarmStoreProduct product = ps.productOfId_newestVersion(fspidList[i]);
            if (product == null) return false;
            versions[i] = product.getVersion();
            proList[i] = product;
        }
        Order order = new Order(customer, farmer, delivery_note, proList, amountList);
        order.setOid("" + ++id_counter);
        order.setDeliveryCharge(farmer.getDeliveryCharge());
        order.setVersionList(versions);
        orders.add(order);
        return true;
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

        return o.cancelOrder();
    }

    public boolean confirmDelivery(String oid, String cid) {
        Order o = getOrder(oid, cid);
        if (o == null) return false;

        return o.confirmDelivery();
    }

    public void clearStoredData() {
        orders.clear();
        id_counter = 100;
    }

}
