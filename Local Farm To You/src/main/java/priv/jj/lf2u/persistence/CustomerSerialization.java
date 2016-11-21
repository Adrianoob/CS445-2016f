package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Customer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by adrianoob on 11/20/16.
 */
public class CustomerSerialization implements CustomerIOInterface {
    private static final String fileName = "customerObjects.ser";
    private ArrayList<Customer> customers;

    public CustomerSerialization() { customers = new ArrayList<>(); }

    @Override
    public Customer[] readCustomersData() {
        customers.clear();
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                int size = in.readInt();
                for (int i = 0; i < size; i++) {
                    customers.add((Customer) in.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return customers.toArray(new Customer[customers.size()]);
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
        write();
    }

    @Override
    public void setCustomer(Customer customer) {
        Customer c_to_change = null;
        for (Customer c : customers) {
            if (customer.getCid().equals(c.getCid())) {
                c_to_change =c;
                break;
            }
        }
        customers.remove(c_to_change);
        customers.add(customer);
        write();
    }

    @Override
    public void clearStoredData() {
        customers.clear();
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void write() {
        File file = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeInt(customers.size());
            for (Customer f : customers) {
                out.writeObject(f);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
