package priv.jj.lf2u.dataFormatting;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by adrianoob on 10/28/16.
 */

@XmlRootElement
public class PersonalInfo {
    private String name;
    private String email;
    private String phone;
    public PersonalInfo(){}
    public PersonalInfo(String n, String e, String p) {
        name = n; email = e; phone = p;
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
