package priv.jj.lf2u.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by adrianoob on 11/20/16.
 */
public class Order implements Serializable {
    private String oid;
    private String delivery_note;

    private Customer customer;
    private Farmer farmer;
    private FarmStoreProduct [] productList;
    private double [] amountList;
//    private int [] versionList;
    private int status;
    private Calendar orderDate;
    private Calendar plannedDeliveryDate;
    private Calendar actuallyDeliveryDate;
    private double deliveryCharge;

    public Order(Customer customer, Farmer farmer, String delivery_note, FarmStoreProduct[] productList, double[] amountList) {
        this.customer = customer;
        this.farmer = farmer;
        this.delivery_note = delivery_note;
        this.productList = productList;
        this.amountList = amountList;
        this.status = Order.OPEN;
        orderDate = Calendar.getInstance();
        plannedDeliveryDate = Calendar.getInstance();
        plannedDeliveryDate.roll(Calendar.DATE, 1);
        actuallyDeliveryDate = null;
    }

    public boolean hasKeyword(String keyword) {
        return oid.toLowerCase().equals(keyword.toLowerCase()) || delivery_note.toLowerCase().contains(keyword.toLowerCase());
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
    public static final int OPEN = 1;
    public static final int DELIVERED = 2;
    public static final int CANCELLED = 3;

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

//    public int[] getVersionList() {
//        return versionList;
//    }
//
//    public void setVersionList(int[] versionList) {
//        this.versionList = versionList;
//    }


    public FarmStoreProduct[] getProductList() {
        return productList;
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

    public String getStatusInfo() {
        String i = "";
        switch (status) {
            case OPEN: i = "open"; break;
            case DELIVERED: i = "delivered"; break;
            case CANCELLED: i = "cancelled"; break;
        }
        return i;
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
        if (actuallyDeliveryDate == null) return "";

        int yyyy = actuallyDeliveryDate.get(Calendar.YEAR);
        int mm = actuallyDeliveryDate.get(Calendar.MONTH) + 1;
        int dd = actuallyDeliveryDate.get(Calendar.DATE);
        return yyyy + "" + mm + "" + dd;
    }

    public double productTotal() {
        double sum = 0;
        for (int i = 0; i < productList.length; i++) {
            sum += productList[i].getPrice() * amountList[i];
        }
        return sum;
    }

    public double ordertotal() {
        return productTotal() + deliveryCharge;
    }

    public LinkedHashMap<String, String> getFormattedInfo() {
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("oid", oid);
        data.put("order_data", getOrderDate());
        data.put("planned_delivery_date", getPlannedDeliveryDate());
        data.put("actual_delivery_date", getActuallyDeliveryDate());
        data.put("status", getStatusInfo());
        data.put("fid", getFid());
        return data;
    }
}
