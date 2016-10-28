package priv.jj.lf2u.role;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Customer implements PersonInterface {
    private String cid; // customer id
    private String zip;
    private String street;
    private Person person;

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
