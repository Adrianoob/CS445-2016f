package priv.jj.lf2u.system;

import priv.jj.lf2u.persistence.FarmerIOInterface;
import priv.jj.lf2u.persistence.FarmerSerialization;
import priv.jj.lf2u.entity.Farmer;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by adrianoob on 10/28/16.
 */
public enum FarmerSystem {
    INSTANCE;

    private ArrayList<Farmer> farmers;
    private int id_counter;
    private FarmerIOInterface farmerIO;

    /* Constructor and Helpers */
    FarmerSystem() {
        // things to do the first time initialization
        farmers = new ArrayList();
        farmerIO = new FarmerSerialization();
        id_counter = 100;

        loadStoredData();
    }

    private void loadStoredData() {
        Farmer [] farmers = farmerIO.readFarmersData();
        for (Farmer f : farmers) {
            this.farmers.add(f);
            int id = Integer.parseInt(f.getFid());
            if (id > id_counter) id_counter = id;
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
        farmerIO.addFarmer(farmer);
        return fid;
    }

    public String addFarmer(Hashtable<String, Object> data) {
        String fid = "" + ++id_counter;
        String n = (String) data.get("personName");
        String e = (String) data.get("personEmail");
        String p = (String) data.get("personPhone");
        String fn = (String) data.get("farmName");
        String ad = (String) data.get("farmAddress");
        String fp = (String) data.get("farmPhone");
        String fw = (String) data.get("farmWeb");
        String [] dt = (String []) data.get("deliversTo");
        Farmer farmer = new Farmer(n,e,p,fn,ad,fp,fw,dt);
        farmer.setFid(fid);
        farmers.add(farmer);
        farmerIO.addFarmer(farmer);
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
        Farmer farmer = farmerOfFid(fid);
        if (farmer == null)
            return false;

        farmer.setFarmerInfo(n,e,p,farmer_name,addres,farm_phone,webb,zips);
        farmerIO.setFarmer(farmer);
        return true;
    }

    public Farmer farmerOfFid(String fid) {
        for (Farmer farmer : farmers) {
            if (farmer.getFid().equals(fid))
                return farmer;
        }
        return null;
    }

    public Farmer[] farmersOfZip(String zip) {
        // if zip is null, return all farmers
        if (zip == null)
            return farmers.toArray(new Farmer[farmers.size()]);

        ArrayList<Farmer> choice = new ArrayList<>();
        for (Farmer farmer : farmers) {
            if (farmer.deliversTo(zip))
                choice.add(farmer);
        }

        return choice.toArray(new Farmer[choice.size()]);
    }

    public Farmer[] farmersByKeyword(String keyword) {
        ArrayList<Farmer> list = new ArrayList<>();
        if (keyword == null || keyword.equals("")) {
            list = this.farmers;
        } else {
            for (Farmer f :farmers) {
                if (f.hasKeyword(keyword)) list.add(f);
            }
        }
        return list.toArray(new Farmer[list.size()]);
    }

    public boolean updateDeliveryCharge(String fid, double deliv) {
        Farmer far = farmerOfFid(fid);
        if (far == null) return false;
        far.setDeliveryCharge(deliv);
        farmerIO.setFarmer(far);
        return true;
    }

    /* IO Method */
    public void setFarmerIO(FarmerIOInterface fio) {
        farmerIO = fio;
    }

    public void clearStoredData() {
        farmers.clear();
        farmerIO.clearStoredData();
        id_counter = 100;
    }

}
