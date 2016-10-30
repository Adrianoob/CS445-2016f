package priv.jj.lf2u.dataFormatting;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by adrianoob on 10/28/16.
 */

@XmlRootElement
public class FarmInfo {
    private String name;
    private String address;
    private String phone;
    private String web;
    public FarmInfo() {}
    public FarmInfo(String n, String a, String p, String w) {
        name = n; address = a; phone = p; web = w;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
