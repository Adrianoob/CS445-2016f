package priv.jj.lf2u.dataFormatting;

/**
 * Created by adrianoob on 11/21/16.
 */
public class ProductGETdata {
    private String fspid;
    private String name;
    private String notes;
    private String start_date;
    private String end_date;
    private double price;
    private String product_unit;
    private String image;

    public ProductGETdata(String fspid, String name, String notes, String start_date, String end_date, double price, String product_unit, String image) {
        this.fspid = fspid;
        this.name = name;
        this.notes = notes;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.product_unit = product_unit;
        this.image = image;
    }

    public String getFspid() {
        return fspid;
    }

    public void setFspid(String fspid) {
        this.fspid = fspid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
