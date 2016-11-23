package priv.jj.lf2u.RESTservice.dataFormatting;

/**
 * Created by adrianoob on 11/22/16.
 */
public class OrderDetail {
    private String fspid;
    private double amount;

    public OrderDetail(String fspid, double amount) {
        this.fspid = fspid;
        this.amount = amount;
    }

    public String getFspid() {
        return fspid;
    }

    public void setFspid(String fspid) {
        this.fspid = fspid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
