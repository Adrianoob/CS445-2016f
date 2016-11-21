package priv.jj.lf2u.entity;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Person {
    // private String id; // ?
    private String name;
    private String email;
    private String phone;

    public Person(String n, String e, String p) {
        name = n;
        email = e;
        phone = p;
    }

    public  String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

}
