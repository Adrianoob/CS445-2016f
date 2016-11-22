package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Order;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by adrianoob on 11/21/16.
 */
public class OrderSerialization implements OrderIOInterface {
    private final static String fileName = "OrderObjects.ser";
    private ArrayList<Order> orders;


    public OrderSerialization() {
        orders = new ArrayList<>();
    }

    @Override
    public Order[] readStoredData() {
        orders.clear();
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                int size = in.readInt();
                for (int i = 0; i < size; i++) {
                    orders.add((Order) in.readObject());
                }
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return orders.toArray(new Order[orders.size()]);
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
        write();
    }

    @Override
    public void changeOrder(Order order) {
//        Order o_to_change = null;
//        for (Order o : orders) {
//            if (order.getOid().equals(o.getOid())) {
//                o_to_change = o;
//                break;
//            }
//        }
//        orders.remove(o_to_change);
//        orders.add(order);
        write();
    }

    @Override
    public void clearStoredDate() {
        orders.clear();
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void write() {
        File file = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeInt(orders.size());
            for (Order o : orders) {
                out.writeObject(o);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
