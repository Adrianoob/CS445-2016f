package priv.jj.lf2u.dataFormatting;

import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.system.FarmerSystem;

import java.text.DecimalFormat;

/**
 * Created by adrianoob on 11/22/16.
 */
public class OrderInfo {
    private String oid;
    private String order_date;
    private String planned_delivery_date;
    private String actual_delivery_date;
    private String status;
    private Farm_info farm_info;
    private Order_detail [] order_detail;
    private String delivery_note;
    private double products_total;
    private double delivery_charge;
    private double order_total;

    public OrderInfo(Order o) {
        oid = o.getOid();
        order_date = o.getOrderDate();
        planned_delivery_date = o.getPlannedDeliveryDate();
        actual_delivery_date = o.getActuallyDeliveryDate();
        status = o.getStatusInfo();
        Farmer f = FarmerSystem.INSTANCE.farmerOfFid(o.getFid());
        farm_info = new Farm_info(f.getFid(), f.getName(), f.getAddress(), f.getPhone(), f.getWeb());
        double [] amounts = o.getAmountList();
        FarmStoreProduct [] prods = o.getProductList();
        order_detail = new Order_detail[amounts.length];
        for (int i = 0; i < amounts.length; i++) {
            order_detail[i] = new Order_detail(prods[i].getFspid(), prods[i].getName(),
                    amounts[i] + prods[i].getProduct_unit(), prods[i].getPrice() + " per " + prods[i].getProduct_unit(),
                    Double.parseDouble(new DecimalFormat("##.##").format(prods[i].getPrice() * amounts[i])));
        }
        delivery_note = o.getDelivery_note();
        products_total = Double.parseDouble(new DecimalFormat("##.##").format(o.productTotal()));
        delivery_charge = o.getDeliveryCharge();
        order_total = Double.parseDouble(new DecimalFormat("##.##").format((products_total + delivery_charge)));

    }

    public OrderInfo(String oid, String order_date, String planned_delivery_date, String actual_delivery_date, String status, Farm_info farm_info, Order_detail[] order_detail, String delivery_note, double products_total, double delivery_charge, double order_total) {
        this.oid = oid;
        this.order_date = order_date;
        this.planned_delivery_date = planned_delivery_date;
        this.actual_delivery_date = actual_delivery_date;
        this.status = status;
        this.farm_info = farm_info;
        this.order_detail = order_detail;
        this.delivery_note = delivery_note;
        this.products_total = products_total;
        this.delivery_charge = delivery_charge;
        this.order_total = order_total;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPlanned_delivery_date() {
        return planned_delivery_date;
    }

    public void setPlanned_delivery_date(String planned_delivery_date) {
        this.planned_delivery_date = planned_delivery_date;
    }

    public String getActual_delivery_date() {
        return actual_delivery_date;
    }

    public void setActual_delivery_date(String actual_delivery_date) {
        this.actual_delivery_date = actual_delivery_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Farm_info getFarm_info() {
        return farm_info;
    }

    public void setFarm_info(Farm_info farm_info) {
        this.farm_info = farm_info;
    }

    public Order_detail[] getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(Order_detail[] order_detail) {
        this.order_detail = order_detail;
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public void setDelivery_note(String delivery_note) {
        this.delivery_note = delivery_note;
    }

    public double getProducts_total() {
        return products_total;
    }

    public void setProducts_total(double products_total) {
        this.products_total = products_total;
    }

    public double getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(double delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public double getOrder_total() {
        return order_total;
    }

    public void setOrder_total(double order_total) {
        this.order_total = order_total;
    }
}
