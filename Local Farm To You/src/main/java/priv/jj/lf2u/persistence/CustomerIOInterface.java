package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Customer;

/**
 * Created by adrianoob on 11/20/16.
 */
public interface CustomerIOInterface {
    // reading existing farmers from stored data
    Customer[] readCustomersData();

    // add new farmer to storage
    void addCustomer(Customer customer);

    // give an existing farm new values
    void setCustomer(Customer customer);

    // clear out stored data
    void clearStoredData();
}
