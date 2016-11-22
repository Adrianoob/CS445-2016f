package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Order;

/**
 * Created by adrianoob on 11/21/16.
 */
public interface OrderIOInterface {
    // read stored order data
    Order[] readStoredData();

    // add order
    void addOrder(Order order);

    // change order
    void changeOrder(Order order);

    // clear local data
    void clearStoredDate();
}
