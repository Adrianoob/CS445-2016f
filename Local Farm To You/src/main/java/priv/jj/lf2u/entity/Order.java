package priv.jj.lf2u.entity;

import java.util.Calendar;

/**
 * Created by adrianoob on 11/20/16.
 */
public class Order {
    private String oid;
    private Customer customer;
    private Farmer farmer;
    private String delivery_note;
    private FarmStoreProduct [] fspidList;
    private double [] amountList;
    private int [] versionList;
    private int status;
    private Calendar orderDate;
    private Calendar plannedDeliveryDate;
    private Calendar actuallyDeliveryDate;
    private double deliveryCharge;

    public Order(Customer customer, Farmer farmer, String delivery_note, FarmStoreProduct[] fspidList, double[] amountList) {
        this.customer = customer;
        this.farmer = farmer;
        this.delivery_note = delivery_note;
        this.fspidList = fspidList;
        this.amountList = amountList;
        this.status = Order.OPEN;
        orderDate = Calendar.getInstance();
        plannedDeliveryDate = Calendar.getInstance();
        plannedDeliveryDate.roll(Calendar.DATE, 1);
        actuallyDeliveryDate = null;
    }

    public boolean confirmDelivery() {
        if (status == OPEN) {
            actuallyDeliveryDate = Calendar.getInstance();
            status = DELIVERED;
            return true;
        }
        return false;
    }

    public boolean cancelOrder() {
        if (status == OPEN) {
            status = CANCELLED;
            return true;
        }
        return false;
    }

    /* status code */
    public static int OPEN = 1;
    public static int DELIVERED = 2;
    public static int CANCELLED = 3;

    /* getters and setters */

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getFid() {
        return farmer.getFid();
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public void setDelivery_note(String delivery_note) {
        this.delivery_note = delivery_note;
    }

    public double[] getAmountList() {
        return amountList;
    }

    public void setAmountList(double[] amountList) {
        this.amountList = amountList;
    }

    public int[] getVersionList() {
        return versionList;
    }

    public void setVersionList(int[] versionList) {
        this.versionList = versionList;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getCid() {
        return customer.getCid();
    }

    public int getStatus() {
        return status;
    }

    public String getOrderDate() {
        int yyyy = orderDate.get(Calendar.YEAR);
        int mm = orderDate.get(Calendar.MONTH) + 1;
        int dd = orderDate.get(Calendar.DATE);
        return yyyy + "" + mm + "" + dd;
    }

    public String getPlannedDeliveryDate() {
        int yyyy = plannedDeliveryDate.get(Calendar.YEAR);
        int mm = plannedDeliveryDate.get(Calendar.MONTH) + 1;
        int dd = plannedDeliveryDate.get(Calendar.DATE);
        return yyyy + "" + mm + "" + dd;
    }

    public String getActuallyDeliveryDate() {
        if (actuallyDeliveryDate == null) return null;

        int yyyy = actuallyDeliveryDate.get(Calendar.YEAR);
        int mm = actuallyDeliveryDate.get(Calendar.MONTH) + 1;
        int dd = actuallyDeliveryDate.get(Calendar.DATE);
        return yyyy + "" + mm + "" + dd;
    }
}