package priv.jj.lf2u.entity;

/**
 * Created by adrianoob on 10/27/16.
 */
public class Manager implements PersonInterface {
    private String mid; // manager id
    private String created_by;
    private String created_date;
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
