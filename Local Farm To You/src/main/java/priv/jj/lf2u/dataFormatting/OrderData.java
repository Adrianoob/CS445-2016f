package priv.jj.lf2u.dataFormatting;


/**
 * Created by adrianoob on 11/22/16.
 */
public class OrderData {
    private String fid;
    private OrderDetail [] order_detail;
    private String delivery_note;

    public OrderData(String fid, OrderDetail[] order_detail, String delivery_note) {
        this.fid = fid;
        this.order_detail = order_detail;
        this.delivery_note = delivery_note;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public OrderDetail[] getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(OrderDetail[] order_detail) {
        this.order_detail = order_detail;
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public void setDelivery_note(String delivery_note) {
        this.delivery_note = delivery_note;
    }

    public String [] getFspidList() {
        String [] list = new String[order_detail.length];
        for (int i = 0; i < order_detail.length; i++) {
            list[i] = order_detail[i].getFspid();
        }
        return list;
    }

    public double [] getAmountList() {
        double [] list = new double[order_detail.length];
        for (int i = 0; i < order_detail.length; i++) {
            list[i] = order_detail[i].getAmount();
        }
        return list;
    }
}
