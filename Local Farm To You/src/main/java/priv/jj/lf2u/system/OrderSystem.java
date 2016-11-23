package priv.jj.lf2u.system;

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

    OrderSystem() {
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
    public String addOrder(String cid, String fid, String delivery_note, String[] fspidList, double[] amountList) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return "-1";

        Customer customer = cs.customerOfCid(cid);
        if (customer == null) return "-1";

        FarmStoreProduct [] proList = new FarmStoreProduct[fspidList.length];
        for (int i = 0; i < fspidList.length; i++) {
            FarmStoreProduct product = ps.productOfId_newestVersion(fspidList[i], fid);
            if (product == null) return "-1";
            proList[i] = product;
        }

        String z = customer.getZip();
        String [] zs = farmer.getDeliversTo();
        boolean is_local = false;
        for (String s : zs) { if (s.equals(z)) is_local = true; break; }
        if (!is_local) return "0";

        Order order = new Order(customer, farmer, delivery_note, proList, amountList);
        String oid = "" + ++id_counter;
        order.setOid(oid);
        order.setDeliveryCharge(farmer.getDeliveryCharge());
        orders.add(order);
        orderIO.addOrder(order);
        return oid;
    }

    public Order [] ordersToDeliverTodayByFid(String fid) {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(Calendar.getInstance().getTime());
        for (Order o : orders) {
            if (o.getFid().equals(fid) && o.getPlannedDeliveryDate().equals(today)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] ordersToDeliverTomorrowByFid(String fid) {
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

    public Order [] ordersByKeyword(String keyword) {
        ArrayList<Order> list = new ArrayList<>();
        if (keyword == null || keyword.equals("")) { list = orders; }
        else {
            for (Order o : orders) {
                if (o.hasKeyword(keyword)) list.add(o);
            }
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] ordersByDates(String fid, String start, String end) {
        ArrayList<Order> list = new ArrayList<>();
        for (Order o : orders) {
            if (o.getFid().equals(fid)) {
                int d = Integer.parseInt(o.getOrderDate());
                int st = Integer.parseInt(start);
                int en = Integer.parseInt(end);
                if (d <= en && d >= st) list.add(o);
            }
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

    public boolean confirmDelivery(String oid) {
        Order o = null;
        for (Order or : orders) { if (or.getOid().equals(oid)) {o = or; break;}}
        if (o == null) return false;

        boolean isSuccessful = o.confirmDelivery();
        if (isSuccessful) orderIO.changeOrder(o);
        return isSuccessful;
    }

    public Order [] allOrdersPlacedToday() {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(Calendar.getInstance().getTime());
        for (Order o : orders) {
            if (o.getOrderDate().equals(today)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] allOrdersPlacedYesterday() {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DATE, -1);
        String yesterday = format.format(calendar.getTime());
        for (Order o : orders) {
            if (o.getPlannedDeliveryDate().equals(yesterday)) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    public Order [] allOrdersLastMonth() {
        ArrayList<Order> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1); calendar.add(Calendar.DATE, -1);
        int end = Integer.parseInt((format.format(calendar.getTime())));
        calendar.set(Calendar.DATE, 1);
        int start = Integer.parseInt((format.format(calendar.getTime())));
        for (Order o : orders) {
            int date = Integer.parseInt(o.getOrderDate());
            if (date >= start && date <= end) list.add(o);
        }
        return list.toArray(new Order[list.size()]);
    }

    /* IO Method */
    public void setOrderIO(OrderIOInterface io) { orderIO = io; }

    public void clearStoredData() {
        orders.clear();
        id_counter = 100;
        orderIO.clearStoredDate();
    }

}
