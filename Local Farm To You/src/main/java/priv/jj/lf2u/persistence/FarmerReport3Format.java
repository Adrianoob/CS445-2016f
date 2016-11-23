package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Order;

/**
 * Created by adrianoob on 11/22/16.
 */
public class FarmerReport3Format {
    private String frid;
    private String name;
    private String start_date;
    private String end_date;
    private int orders_placed;
    private int orders_cancelled;
    private int orders_delivered;
    private double products_revenue;
    private double delivery_revenue;

    public FarmerReport3Format(Order[] orders, String start, String end) {
        frid = "703";
        name = "Revenue report";
        start_date = start;
        end_date = end;
        orders_placed = orders.length;
        orders_cancelled = 0; orders_delivered = 0;
        products_revenue = 0; delivery_revenue = 0;
        for (Order order : orders) {
            if (order.getStatus() == Order.CANCELLED)
                orders_cancelled++;
            else if (order.getStatus() == Order.DELIVERED) {
                orders_delivered ++;
                products_revenue += order.productTotal();
                delivery_revenue += order.getDeliveryCharge();
            }
        }

    }

    public FarmerReport3Format(String frid, String name, String start_date, String end_date, int orders_place, int orders_cancelled, int orders_delivered, double products_revenue, double delivery_revenue) {
        this.frid = frid;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.orders_placed = orders_place;
        this.orders_cancelled = orders_cancelled;
        this.orders_delivered = orders_delivered;
        this.products_revenue = products_revenue;
        this.delivery_revenue = delivery_revenue;
    }

    public String getFrid() {
        return frid;
    }

    public void setFrid(String frid) {
        this.frid = frid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getOrders_placed() {
        return orders_placed;
    }

    public void setOrders_placed(int orders_place) {
        this.orders_placed = orders_place;
    }

    public int getOrders_cancelled() {
        return orders_cancelled;
    }

    public void setOrders_cancelled(int orders_cancelled) {
        this.orders_cancelled = orders_cancelled;
    }

    public int getOrders_delivered() {
        return orders_delivered;
    }

    public void setOrders_delivered(int orders_delivered) {
        this.orders_delivered = orders_delivered;
    }

    public double getProducts_revenue() {
        return products_revenue;
    }

    public void setProducts_revenue(double products_revenue) {
        this.products_revenue = products_revenue;
    }

    public double getDelivery_revenue() {
        return delivery_revenue;
    }

    public void setDelivery_revenue(double delivery_revenue) {
        this.delivery_revenue = delivery_revenue;
    }
}
