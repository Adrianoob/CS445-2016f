package priv.jj.lf2u.system;

import priv.jj.lf2u.entity.Manager;
import priv.jj.lf2u.persistence.ManagerIOInterface;
import priv.jj.lf2u.persistence.ManagerSerialization;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by adrianoob on 11/21/16.
 */
public enum ManagerSystem {
    INSTANCE;
    private ArrayList<Manager> managers;
    private ManagerIOInterface managerIO;

    ManagerSystem() {
        managers = new ArrayList<>();
        managerIO = new ManagerSerialization();
        loadStoredData();
    }

    private void loadStoredData() {
        managerIO.clearStoredData();
        Manager [] mngrs = managerIO.readStoredData();
        Collections.addAll(managers, mngrs);
    }

    public Manager [] getManagers() {
        return managers.toArray(new Manager[managers.size()]);
    }

    public Manager managerOfMid(String mid) {
        for (Manager m : managers) {
            if (m.getMid().equals(mid)) return m;
        }
        return null;
    }

    /* IO Method */
    public void setManagerIO(ManagerIOInterface io) { managerIO = io; }
}
