package priv.jj.lf2u.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by adrianoob on 10/27/16.
 */
public class FarmStoreProduct implements Serializable {
    private String fspid; // farm store product id
    private String fid; // farmer id
    private String name; // product name
    private String note; // description
    private Calendar start_date;
    private Calendar end_date;
    private double price;
    private String product_unit;
    private String image;
    private int version; // 0 -> new, other -> old

    public FarmStoreProduct(String fid, String name, String note,
                            String start_date, String end_date,
                            double price, String product_unit, String image) {
        this.fid = fid;
        this.name = name;
        this.note = note;
        if (start_date.equals("")) {
            this.start_date = Calendar.getInstance();
            this.start_date.set(Calendar.DAY_OF_YEAR, 0);
        } else { this.start_date = convertDate(start_date); }
        if (end_date.equals("")) {
            this.end_date = Calendar.getInstance();
            this.end_date.set(Calendar.DATE, 31);
            this.end_date.set(Calendar.MONTH, 11);
        } else { this.end_date = convertDate(end_date); }
        this.price = price;
        this.product_unit = product_unit;
        this.image = image;
        this.version = 0;
    }

    private Calendar convertDate(String str) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(str.substring(0, 2)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(str.substring(3, 5)));
        return cal;
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
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(this.start_date.getTime());
    }

    public void setStart_date(String start_date) {
        if (start_date.equals("")) {
            this.start_date = Calendar.getInstance();
            this.start_date.set(Calendar.DAY_OF_YEAR, 0);
        } else { this.start_date = convertDate(start_date); }
    }

    public String getEnd_date() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(this.end_date.getTime());
    }

    public void setEnd_date(String end_date) {
        if (end_date.equals("")) {
            this.end_date = Calendar.getInstance();
            this.end_date.set(Calendar.DATE, 31);
            this.end_date.set(Calendar.MONTH, 11);
        } else { this.end_date = convertDate(end_date); }
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

    public int getVersion() { return this.version; }

    public void setVersion(int version) {
        this.version = version;
    }

    public FarmStoreProduct copy() {
        FarmStoreProduct copy = new FarmStoreProduct(this.fid, this.name, this.note, this.getStart_date(), this.getEnd_date(),
                this.price, this.product_unit, this.image);
        copy.version = this.version + 1;
        copy.fspid = this.fspid;
        return copy;
    }
}
