package priv.jj.lf2u.RESTservice.dataFormatting;

/**
 * Created by adrianoob on 11/21/16.
 */
public class ProductPOSTdata {
    private String gcpid;
    private String note;
    private String start_date;
    private String end_date;
    private double price;
    private String product_unit;
    private String image;

    public ProductPOSTdata(String gcpid, String note, String start_date, String end_date, double price, String product_unit, String image) {
        this.gcpid = gcpid;
        this.note = note;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.product_unit = product_unit;
        this.image = image;
    }

    public String getGcpid() {
        return gcpid;
    }

    public void setGcpid(String gcpid) {
        this.gcpid = gcpid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
