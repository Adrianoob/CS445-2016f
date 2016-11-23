package priv.jj.lf2u.dataFormatting;

/**
 * Created by adrianoob on 11/22/16.
 */
public class Order_detail {
    private String fspid;
    private String name;
    private String amount;
    private String price;
    private double line_item_total;

    public Order_detail(String fspid, String name, String amount, String price, double line_item_total) {
        this.fspid = fspid;
        this.name = name;
        this.amount = amount;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
