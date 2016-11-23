package priv.jj.lf2u.dataFormatting;


/**
 * Created by adrianoob on 11/22/16.
 */
public class OrderedBy {
    private String name;
    private String email;
    private String phone;

    public OrderedBy(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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
