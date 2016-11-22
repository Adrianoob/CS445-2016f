package priv.jj.lf2u.entity;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Farmer
 */
public class Farmer implements Serializable {
    private String fid; // farmer id
    private String name; // farm name
    private String address; // farm address
    private String phone; // farm phone
    private String web;
    private String [] deliversTo;
    private String personName;
    private String personEmail;
    private String personPhone;
    private double deliveryCharge;

    public Farmer(String n, String e, String p,
                  String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        setFarmerInfo(n, e, p, farmer_name, addres, farm_phone, webb, zips);
        deliveryCharge = 0;
    }

    public void setFarmerInfo(String n, String e, String p,
                              String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        personEmail = e;
        personName = n;
        personPhone = p;
        name = farmer_name;
        address = addres;
        phone = farm_phone;
        web = webb;
        deliversTo = new String[zips.length];
        System.arraycopy(zips, 0, deliversTo, 0, zips.length);
    }

    public Hashtable<String, String> createHashTable() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("fid",     fid);
        table.put("name",    name);
        table.put("address", address);
        table.put("phone",   phone);
        table.put("web",     web);
        table.put("personName", personName);
        table.put("personEmail",personEmail);
        table.put("personPhone",personPhone);

        String zip = "";
        if (deliversTo.length > 0) {
            zip = deliversTo[0];
            if (deliversTo.length > 1) {
                for (int i = 1; i < deliversTo.length; i++) {
                    zip = zip + "," + deliversTo[i];
                }
            }
        }
        table.put("deliversTo", zip);

        return table;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeb() {
        return web;
    }

    public String [] getDeliversTo() {
        return deliversTo;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String id) {
        fid = id;
    }

    public boolean deliversTo(String zip) {
        for (String z : deliversTo) {
            if (z.equals(zip))
                return true;
        }
        return false;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getDeliveryCharge() {
        return this.deliveryCharge;
    }


}
