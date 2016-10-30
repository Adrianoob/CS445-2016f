package priv.jj.lf2u.role;

import java.io.Serializable;

/**
 * Farmer
 */
public class Farmer implements PersonInterface, Serializable {
    private String fid; // farmer id
    private String name; // farm name
    private String address; // farm address
    private String phone; // farm phone
    private String web;
    private String [] delivers_to;
    private Person person;

    public Farmer() {}

    public Farmer(String n, String e, String p,
                  String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        person = new Person(n, e, p);
        name = farmer_name;
        address = addres;
        phone = farm_phone;
        web = webb;
        delivers_to = new String[zips.length];
        System.arraycopy(zips, 0, delivers_to, 0, zips.length);
    }

    public void setFarmerInfo(String n, String e, String p,
                              String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        person = new Person(n, e, p);
        name = farmer_name;
        address = addres;
        phone = farm_phone;
        web = webb;
        delivers_to = new String[zips.length];
        System.arraycopy(zips, 0, delivers_to, 0, zips.length);
    }

    public String getPersonName() {
        return person.getName();
    }

    public String getPersonEmail() {
        return person.getEmail();
    }

    public String getPersonPhone() {
        return person.getPhone();
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
