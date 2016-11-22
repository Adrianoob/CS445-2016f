package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Manager;

/**
 * Created by adrianoob on 11/21/16.
 */
public class ManagerSerialization implements ManagerIOInterface {
    @Override
    public Manager[] readStoredData() {
        Manager admin = new Manager("Admin", "System", "123-123-1234", "admin@gg.com");

        admin.setMid("0");
        return new Manager[]{admin};
    }

    @Override
    public void addManager() {}

    @Override
    public void changeManager() {}

    @Override
    public void clearStoredData() {}
}
