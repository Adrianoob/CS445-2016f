package priv.jj.lf2u.RESTservice.dataFormatting;

import priv.jj.lf2u.entity.Customer;
import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.system.CustomerSystem;

import java.text.DecimalFormat;

/**
 * Created by adrianoob on 11/22/16.
 */
public class FarmerReportOrders {
    private String oid;
    private double product_total;
    private double delivery_charge;
    private double order_total;
    private String status;
    private String order_date;
    private String planned_delivery_date;
    private String actual_delivery_date;
    private OrderedBy ordered_by;
    private String delivery_address;
    private String note;
    private FarmerReportOrderDetail [] order_detail;

    public FarmerReportOrders(Order o) {
        oid = o.getOid();
        product_total = o.productTotal();
        delivery_charge = o.getDeliveryCharge();
        order_total = product_total + delivery_charge;
        status = o.getStatusInfo();
        order_date = o.getOrderDate();
        planned_delivery_date = o.getPlannedDeliveryDate();
        actual_delivery_date = o.getActuallyDeliveryDate();
        Customer cus = CustomerSystem.INSTANCE.customerOfCid(o.getCid());
        ordered_by = new OrderedBy(cus.getName(), cus.getEmail(), cus.getPhone());
        delivery_address = cus.getStreet();
        note = o.getDelivery_note();
        order_detail = new FarmerReportOrderDetail[o.getProductList().length];
        for (int i = 0; i < o.getProductList().length; i++) {
            FarmStoreProduct p = o.getProductList()[i];
            double n = o.getAmountList()[i];
            order_detail[i] = new FarmerReportOrderDetail(p.getFspid(), p.getName(), n + " " + p.getProduct_unit(),
                    p.getPrice() + " per " + p.getProduct_unit(),
                    Double.parseDouble(new DecimalFormat("##.##").format(n * p.getPrice())));
        }
    }

    public FarmerReportOrders(String oid, double product_total, double delivery_charge, double order_total, String status, String order_date, String planned_delivery_date, String actual_delivery_date, OrderedBy ordered_by, String delivery_address, String note, FarmerReportOrderDetail[] order_detail) {
        this.oid = oid;
        this.product_total = product_total;
        this.delivery_charge = delivery_charge;
        this.order_total = order_total;
        this.status = status;
        this.order_date = order_date;
        this.planned_delivery_date = planned_delivery_date;
        this.actual_delivery_date = actual_delivery_date;
        this.ordered_by = ordered_by;
        this.delivery_address = delivery_address;
        this.note = note;
        this.order_detail = order_detail;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public double getProduct_total() {
        return product_total;
    }

    public void setProduct_total(double product_total) {
        this.product_total = product_total;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public OrderedBy getOrdered_by() {
        return ordered_by;
    }

    public void setOrdered_by(OrderedBy ordered_by) {
        this.ordered_by = ordered_by;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public FarmerReportOrderDetail[] getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(FarmerReportOrderDetail[] order_detail) {
        this.order_detail = order_detail;
    }
}
