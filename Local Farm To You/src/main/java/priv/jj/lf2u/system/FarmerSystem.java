package priv.jj.lf2u.system;

import priv.jj.lf2u.persistence.FarmerIOInterface;
import priv.jj.lf2u.persistence.FarmerSerialization;
import priv.jj.lf2u.role.Farmer;

import java.util.ArrayList;

/**
 * Created by adrianoob on 10/28/16.
 */
public enum FarmerSystem {
    INSTANCE;

    private ArrayList<Farmer> farmers;
    private int id_counter;
    private FarmerIOInterface farmerIO;

    /* Constructor and Helpers */
    private FarmerSystem() {
        // things to do the first time initialization
        farmers = new ArrayList();
        farmerIO = new FarmerSerialization();
        readFarmersDataIfExists();
        initializeIdGenerator();
    }

    private void readFarmersDataIfExists() {
        // read farmer data
        // read max int id generated
    }

    private void initializeIdGenerator() {
        id_counter = 100; // for now
        for (Farmer f : farmers) {
            int id = Integer.parseInt(f.getFid());
            if (id > id_counter)
                id_counter = id;
        }
    }

    /* Public Methods */
    /** add a new farmer and return its newly generated id
     * @param n - person name
     * @param e - person email
     * @param p - person phone
     * @param farmer_name - farm name
     * @param addres      - farm address
     * @param farm_phone  - farm phone
     * @param webb        - farm web
     * @param zips        - farm delivery zip codes
     * @return - newly generated fid
     */
    public String addFarmer(String n, String e, String p,
                            String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        Farmer farmer = new Farmer(n,e,p,farmer_name,addres,farm_phone,webb, zips);
        String fid = "" + ++id_counter;
        farmer.setFid(fid);
        farmers.add(farmer);
        return fid;
    }

    /** set the farmer of fid with given information
     * @param fid - the farmer to set
     * @param n   - person name
     * @param e   - person email
     * @param p   - person phone
     * @param farmer_name - farm name
     * @param addres      - farm address
     * @param farm_phone  - farm phone
     * @param webb        - farm web
     * @param zips        - farm delivery zip codes
     * @return - whether operation goes through, false when farmer of fid doesn't exist
     */
    public boolean setFarmer(String fid,
                             String n, String e, String p,
                             String farmer_name, String addres, String farm_phone, String webb, String [] zips) {
        Farmer farmer = farmOfFid(fid);
        if (farmer == null)
            return false;
        else {
            farmer.setFarmerInfo(n,e,p,farmer_name,addres,farm_phone,webb,zips);
            return true;
        }
    }

    public Farmer farmOfFid(String fid) {
        for (Farmer farmer : farmers) {
            if (farmer.getFid().equals(fid))
                return farmer;
        }
        return null;
    }

    public Farmer[] farmersOfZip(String zip) {
        ArrayList<Farmer> choice = new ArrayList<Farmer>();
        for (Farmer farmer : farmers) {
            if (farmer.deliversTo(zip))
                choice.add(farmer);
        }

        return choice.toArray(new Farmer[choice.size()]);
    }
}
