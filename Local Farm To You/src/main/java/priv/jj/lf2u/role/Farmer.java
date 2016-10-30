package priv.jj.lf2u.role;

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
    private String [] delivers_to;
    private String personName;
    private String personEmail;
    private String personPhone;
//    private Person person;

    public Farmer() {}

    public Farmer(String n, String e, String p,
                  String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        personName = n;
        personEmail = e;
        personPhone = p;
        name = farmer_name;
        address = addres;
        phone = farm_phone;
        web = webb;
        delivers_to = new String[zips.length];
        System.arraycopy(zips, 0, delivers_to, 0, zips.length);
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
        delivers_to = new String[zips.length];
        System.arraycopy(zips, 0, delivers_to, 0, zips.length);
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
        if (delivers_to.length > 0) {
            zip = delivers_to[0];
            if (delivers_to.length > 1) {
                for (int i = 1; i < delivers_to.length; i++) {
                    zip = zip + "," + delivers_to[i];
                }
            }
        }
        table.put("delivers_to", zip);

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

    public String [] getDelivers_to() {
        return delivers_to;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String id) {
        fid = id;
    }

    public boolean deliversTo(String zip) {
        for (String z : delivers_to) {
            if (z.equals(zip))
                return true;
        }
        return false;
    }


}
