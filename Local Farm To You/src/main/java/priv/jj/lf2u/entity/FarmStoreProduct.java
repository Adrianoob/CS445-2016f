package priv.jj.lf2u.entity;

import java.io.Serializable;

/**
 * Created by adrianoob on 10/27/16.
 */
public class FarmStoreProduct implements Serializable {
    private String fspid; // farm store product id
    private String fid; // farmer id
    private String name; // product name
    private String note; // description
    private String start_date;
    private String end_date;
    private String price;
    private String product_unit;
    private String image;
    private int version;

    public FarmStoreProduct(String fid, String name, String note,
                            String start_date, String end_date,
                            String price, String product_unit, String image) {
        this.fid = fid;
        this.name = name;
        this.note = note;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.product_unit = product_unit;
        this.image = image;
        this.version = 0;
    }


    public String getFspid() {
        return fspid;
    }

    public void setFspid(String fspid) {
        this.fspid = fspid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public int getVersion() { return this.version; }

    public FarmStoreProduct copy() {
        FarmStoreProduct copy = new FarmStoreProduct(this.fid, this.name, this.note, this.start_date, this.end_date,
                this.price, this.product_unit, this.image);
        copy.version = this.version + 1;
        copy.fspid = this.fspid;
        return copy;
    }
}
