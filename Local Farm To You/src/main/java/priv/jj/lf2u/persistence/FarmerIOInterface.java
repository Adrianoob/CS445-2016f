package priv.jj.lf2u.persistence;

import priv.jj.lf2u.entity.Farmer;

/**
 * Created by adrianoob on 10/30/16.
 */
public interface FarmerIOInterface {
    // reading existing farmers from disk
    Farmer [] readFarmersData();

    // add new farmer to storage
    void addFarmer(Farmer farmer);

    // give an existing farm new values
    void setFarmer(Farmer farmer);

    // clear out stored data
    void clearStoredData();
}
