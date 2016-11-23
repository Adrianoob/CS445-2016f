package priv.jj.lf2u.RESTservice.dataFormatting;

import priv.jj.lf2u.entity.Order;

/**
 * Created by adrianoob on 11/22/16.
 */
public class FarmerReportOrderFormat {
    private String frid;
    private String name;
    private FarmerReportOrders[] orders;

    public FarmerReportOrderFormat(String frid, String name, Order [] orders) {
        this.frid = frid;
        this.name = name;
        this.orders = new FarmerReportOrders[orders.length];
        for (int i = 0; i < orders.length; i++) {
            this.orders[i] = new FarmerReportOrders(orders[i]);
        }
    }

    public FarmerReportOrderFormat(String frid, String name, FarmerReportOrders[] orders) {
        this.frid = frid;
        this.name = name;
        this.orders = orders;
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

    public FarmerReportOrders[] getOrders() {
        return orders;
    }

    public void setOrders(FarmerReportOrders[] orders) {
        this.orders = orders;
    }
}
