package priv.jj.lf2u.RESTservice.dataFormatting;

/**
 * Created by adrianoob on 11/22/16.
 */
public class FarmerReportOrderDetail {
    private String fspid;
    private String name;
    private String order_size;
    private String price;
    private double line_item_total;

    public FarmerReportOrderDetail(String fspid, String name, String order_size, String price, double line_item_total) {
        this.fspid = fspid;
        this.name = name;
        this.order_size = order_size;
        this.price = price;
        this.line_item_total = line_item_total;
    }

    public String getFspid() {
        return fspid;
    }

    public void setFspid(String fspid) {
        this.fspid = fspid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_size() {
        return order_size;
    }

    public void setOrder_size(String order_size) {
        this.order_size = order_size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getLine_item_total() {
        return line_item_total;
    }

    public void setLine_item_total(double line_item_total) {
        this.line_item_total = line_item_total;
    }
}
