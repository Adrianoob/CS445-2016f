package priv.jj.lf2u.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by adrianoob on 10/27/16.
 */

//        "mid": "0",
//        "name": "Super User",
//        "created_by": "System"
//        "create_date": "20161001",
//        "phone": "123-0987-654",
//        "email": "superuser@example.com"

public class Manager {
    private String mid; // manager id
    private String name;
    private String createdBy;
    private Calendar createdDate;
    private String phone;
    private String email;

    public Manager (String name, String createdBy, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy;
        this.createdDate = Calendar.getInstance();
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(createdDate.getTime());
    }
}
