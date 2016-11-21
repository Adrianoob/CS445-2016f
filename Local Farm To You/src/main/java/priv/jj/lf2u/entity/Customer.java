package priv.jj.lf2u.entity;

import java.io.Serializable;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Customer implements Serializable {
    private String cid; // customer id
    private String zip;
    private String street;
    private String name;
    private String email;
    private String phone;

    public Customer(String n, String e, String p, String stree, String zi) {
        setCustomerInfo(n, e, p, stree, zi);
    }

    public void setCustomerInfo(String n, String e, String p, String s, String z) {
        name = n;
        email = e;
        phone = p;
        street = s;
        zip = z;
    }

    /* getters and setters */

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
