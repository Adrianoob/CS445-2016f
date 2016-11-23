package priv.jj.lf2u.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Farmer
 */
public class Farmer implements Serializable {
    private Hashtable<String, String> info; // this is searchable;
// hidden attributes in info Hashtable
// String fid; // farmer id
// String name; // farm name
// String address; // farm address
// String phone; // farm phone
// String web;
// String [] deliversTo;
// String personName;
// String personEmail;
// String personPhone;
// double deliveryCharge;

    public Farmer(String n, String e, String p,
                  String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        info = new Hashtable<>();
        setFarmerInfo(n, e, p, farmer_name, addres, farm_phone, webb, zips);
        info.put("deliveryCharge", "0");
        // FIXME: 11/22/16 deliverycharge is not stored well
    }

    public void setFarmerInfo(String n, String e, String p,
                              String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        info.put("personEmail", e);
        info.put("personName", n);
        info.put("personPhone", p);
        info.put("name", farmer_name);
        info.put("address", addres);
        info.put("phone", farm_phone);
        info.put("web", webb);
        String temp = "";
        for (String z : zips) {
            temp = temp + "," + z;
        }
        if (temp.equals("")) temp = "";
        else temp = temp.substring(1, temp.length());
        info.put("deliversTo", temp);
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

    public String getPersonName() {
        return info.get("personName");
    }

    public String getPersonEmail() {
        return info.get("personEmail");
    }

    public String getPersonPhone() {
        return info.get("personPhone");
    }

    public String getName() {
        return info.get("name");
    }

    public String getAddress() {
        return info.get("address");
    }

    public String getPhone() {
        return info.get("phone");
    }

    public String getWeb() {
        return info.get("web");
    }

    public String [] getDeliversTo() {
        String deliversTo = info.get("deliversTo");
        if (deliversTo.equals("")) return new String[]{};

        StringTokenizer t = new StringTokenizer(deliversTo, ",");
        ArrayList<String> zlist = new ArrayList<>();
        while (t.hasMoreTokens()) {
            zlist.add(t.nextToken());
        }
        return zlist.toArray(new String[zlist.size()]);
    }


    public String getFid() {
        return info.get("fid");
    }

    public void setFid(String id) {
        info.put("fid", id);
    }

    public boolean deliversTo(String zip) {
        for (String z : getDeliversTo()) {
            if (z.equals(zip))
                return true;
        }
        return false;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        info.put("deliveryCharge", ""+deliveryCharge);
    }

    public double getDeliveryCharge() {
        return Double.parseDouble(info.get("deliveryCharge"));
    }


}
