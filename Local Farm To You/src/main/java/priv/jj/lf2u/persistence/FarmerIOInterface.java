package priv.jj.lf2u.persistence;

import priv.jj.lf2u.system.FarmerSystem;

import java.util.Hashtable;

/**
 * Created by adrianoob on 10/30/16.
 */
public interface FarmerIOInterface {
    FarmerSystem fs = FarmerSystem.INSTANCE;

    // loading existing farmers
    void loadFarmers();

    // add new farmer to storage
    void addFarmer(Hashtable<String, String> farmer);

}
