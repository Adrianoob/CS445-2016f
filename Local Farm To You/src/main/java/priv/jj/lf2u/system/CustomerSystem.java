package priv.jj.lf2u.system;

import priv.jj.lf2u.persistence.CustomerIOInterface;
import priv.jj.lf2u.persistence.CustomerSerialization;
import priv.jj.lf2u.entity.Customer;

import java.util.ArrayList;

/**
 * Created by adrianoob on 11/20/16.
 */
public enum CustomerSystem {
    INSTANCE;

    private ArrayList<Customer> customers;
    private int id_counter;
    private CustomerIOInterface customerIO;

    CustomerSystem() {
        customers = new ArrayList<>();
        id_counter = 100;
        customerIO = new CustomerSerialization();

        loadStoredData();
    }

    private void loadStoredData() {
        Customer [] cs = customerIO.readCustomersData();
        for (Customer c : cs) {
            customers.add(c);
            int id = Integer.parseInt(c.getCid());
            if (id > id_counter) id_counter = id;
        }

    }

    /* public methods */
    public String addCustomer(String n, String e, String p, String s, String z) {
        Customer c = new Customer(n, e, p, s, z);
        String id = "" + ++id_counter;
        c.setCid(id);

        customers.add(c);
        customerIO.addCustomer(c);
        return id;
    }

    public boolean setCustomer(String cid, String n, String e, String p, String s, String z) {
        Customer c = customerOfCid(cid);
        if (c == null)
            return false;

        c.setName(n);
        c.setEmail(e);
        c.setPhone(p);
        c.setStreet(s);
        c.setZip(z);
        customerIO.setCustomer(c);
        return true;
    }


    public Customer customerOfCid(String cid) {
        for (Customer c : customers) {
            if (c.getCid().equals(cid))
                return c;
        }
        return null;
    }

    public Customer [] customersByKeyword(String keyword) {
        ArrayList<Customer> cus = new ArrayList<>();
        if (keyword == null || keyword.equals("")) { cus = customers; }
        else {
            for (Customer c : customers) {
                if (c.hasKeyword(keyword)) cus.add(c);
            }
        }
        return cus.toArray(new Customer[cus.size()]);
    }

    /* IO Method */
    public void setCustomerIO(CustomerIOInterface io) { customerIO = io; }

    public void clearStoredData() {
        id_counter = 100;
        customers.clear();
        customerIO.clearStoredData();
    }
}
