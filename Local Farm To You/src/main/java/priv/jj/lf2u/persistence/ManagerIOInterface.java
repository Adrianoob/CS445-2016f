package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Manager;

/**
 * Created by adrianoob on 11/21/16.
 */
public interface ManagerIOInterface {
    Manager[] readStoredData();

    void addManager();

    void changeManager();

    void clearStoredData();
}
