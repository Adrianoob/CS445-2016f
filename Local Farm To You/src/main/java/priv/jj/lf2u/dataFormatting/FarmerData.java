package priv.jj.lf2u.dataFormatting;

import priv.jj.lf2u.entity.Farmer;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by adrianoob on 10/28/16.
 */

@XmlRootElement
public class FarmerData {
    private String fid;
    private FarmInfo farm_info;
    private PersonalInfo personal_info;
    private String [] delivers_to;
    private FarmerData() {}

    public FarmerData(Farmer f) {
        fid = f.getFid();
        farm_info = new FarmInfo(f.getName(), f.getAddress(), f.getPhone(), f.getWeb());
        personal_info = new PersonalInfo(f.getPersonName(), f.getPersonEmail(), f.getPersonPhone());
        delivers_to = f.getDeliversTo();
    }



    public String getPersonName() {
        return personal_info.getName();
    }

    public String getPersonEmail() {
        return personal_info.getEmail();
    }

    public String getPersonPhone() {
        return personal_info.getPhone();
    }

    public String getFarmName() {
        return farm_info.getName();
    }

    public String getFarmPhone() {
        return farm_info.getPhone();
    }

    public String getFarmAddress() {
        return farm_info.getAddress();
    }

    public String getFarmWeb() {
        return farm_info.getWeb();
    }

    /* getters and setters */

    public FarmInfo getFarm_info() {
        return farm_info;
    }

    public void setFarm_info(FarmInfo farm_info) {
        this.farm_info = farm_info;
    }

    public PersonalInfo getPersonal_info() {
        return personal_info;
    }

    public void setPersonal_info(PersonalInfo personal_info) {
        this.personal_info = personal_info;
    }

    public String[] getDelivers_to() {
        return delivers_to;
    }

    public void setDelivers_to(String[] delivers_to) {
        this.delivers_to = delivers_to;
    }
}
