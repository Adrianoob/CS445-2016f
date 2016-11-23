package priv.jj.lf2u.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Customer implements Serializable {
    private LinkedHashMap<String, String> info; // searchable attribtes hide here
    // String cid; // customer id
    // String zip;
    // String street;
    // String name;
    // String email;
    // String phone;

    public Customer(String n, String e, String p, String stree, String zi) {
        info = new LinkedHashMap<>();
        setCustomerInfo(n, e, p, stree, zi);
    }

    public void setCustomerInfo(String n, String e, String p, String s, String z) {
        info.put("cid", "not-set");
        info.put("name", n);
        info.put("street", s);
        info.put("zip", z);
        info.put("phone", p);
        info.put("email", e);
    }

    public boolean hasKeyword(String keyword) {
        Set<String> keys = info.keySet();
        for (String key : keys) {
            String value = info.get(key);
            if (value.toLowerCase().contains(keyword.toLowerCase()))
                return true;
        }
        return false;
    }

    /* getters and setters */

    public String getCid() {
        return info.get("cid");
    }

    public void setCid(String cid) {
        info.put("cid", cid);
    }

    public String getZip() {
        return info.get("zip");
    }

    public void setZip(String zip) {
        info.put("zip", zip);
    }

    public String getStreet() {
        return info.get("street");
    }

    public void setStreet(String street) {
        info.put("street", street);
    }

    public String getName() {
        return info.get("name");
    }

    public void setName(String name) {
        info.put("name", name);
    }

    public String getEmail() {
        return info.get("email");
    }

    public void setEmail(String email) {
        info.put("email", email);
    }

    public String getPhone() {
        return info.get("phone");
    }

    public void setPhone(String phone) {
        info.put("phone", phone);
    }

    public LinkedHashMap<String, String> getFormattedInfo() {
        return info;
    }
}
