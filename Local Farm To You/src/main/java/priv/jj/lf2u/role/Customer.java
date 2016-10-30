package priv.jj.lf2u.role;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Customer implements PersonInterface {
    private String cid; // customer id
    private String zip;
    private String street;
    private Person person;

    public Customer(String n, String e, String p, String stree, String zi) {
        person = new Person(n, e, p);
        street = stree;
        zip = zi;
        // FIXME: 10/27/16 here needs a way to generate id
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
}
