
package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class USER implements Serializable ,Cloneable{


    @SerializedName("ID")
    @Expose
    private String ID;

    @Override
    public String toString() {
        if(getNAME().contains("Select")){
            return ""+getNAME() .toString();
        }
        if(getDEGINATION() ==null || getDEGINATION().equalsIgnoreCase("user")){
            return ""+getNAME().toString();
        }else{
            return ""+getNAME()+" ( "+getDEGINATION()+" ) " .toString();
        }
    }

    public String getENABLE() {
        return ENABLE;
    }

    public void setENABLE(String ENABLE) {
        this.ENABLE = ENABLE;
    }

    @SerializedName("ENABLE")
    @Expose
    private String ENABLE;

    @SerializedName("MOBILE")
    @Expose
    private String MOBILE;

    @SerializedName("TIME_STAMP")
    @Expose
    private String TIME_STAMP;

    @SerializedName("NAME")
    @Expose
    private String NAME;


    @SerializedName("EMAIL")
    @Expose
    private String EMAIL;
    @SerializedName("RP")
    @Expose
    private String RP;

    public String getRP() {
        return RP;
    }

    public void setRP(String RP) {
        this.RP = RP;
    }

    @SerializedName("GP")
    @Expose
    private String GP;

    public String getGP() {
        return GP;
    }

    public void setGP(String GP) {
        this.GP = GP;
    }

    @SerializedName("TP")
    @Expose
    private String TP;

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }

    @SerializedName("LOCATION")
    @Expose
    private String LOCATION;

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @SerializedName("ADDRESS")
    @Expose
    private String ADDRESS;


    @SerializedName("DEGINATION")
    @Expose
    private String DEGINATION;

    @SerializedName("MONTHALY_SALE")
    @Expose
    private String MONTHALY_SALE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getTIME_STAMP() {
        return TIME_STAMP;
    }

    public void setTIME_STAMP(String TIME_STAMP) {
        this.TIME_STAMP = TIME_STAMP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getDEGINATION() {
        return DEGINATION;
    }

    public void setDEGINATION(String DEGINATION) {
        this.DEGINATION = DEGINATION;
    }

    public String getMONTHALY_SALE() {
        return MONTHALY_SALE;
    }

    public void setMONTHALY_SALE(String MONTHALY_SALE) {
        this.MONTHALY_SALE = MONTHALY_SALE;
    }

    public String getMONTHALY_COLLECTION() {
        return MONTHALY_COLLECTION;
    }

    public void setMONTHALY_COLLECTION(String MONTHALY_COLLECTION) {
        this.MONTHALY_COLLECTION = MONTHALY_COLLECTION;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    @SerializedName("MONTHALY_COLLECTION")
    @Expose
    private String MONTHALY_COLLECTION;

    public String getSSTG() {
        return SSTG;
    }

    public void setSSTG(String SSTG) {
        this.SSTG = SSTG;
    }

    @SerializedName("SSTG")
    @Expose
    private String SSTG;


    @SerializedName("PASSWORD")
    @Expose
    private String PASSWORD;

    public String getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(String POINTS) {
        this.POINTS = POINTS;
    }

    @SerializedName("POINTS")
    @Expose
    private String POINTS;





    @SerializedName("CURRENT_SSTG")
    @Expose
    private String CURRENT_SSTG;
    @SerializedName("CURRENT_MST")
    @Expose
    private String CURRENT_MST;
    @SerializedName("CURRENT_MCT")
    @Expose
    private String CURRENT_MCT;


    @SerializedName("CITY")
    @Expose
    private String CITY;
    @SerializedName("STATE")
    @Expose
    private String STATE;

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    @SerializedName("PINCODE")
    @Expose
    private String PINCODE;



    @SerializedName("TODAY_SSTG")
    @Expose
    private String TODAY_SSTG;
    @SerializedName("TODAY_MST")
    @Expose
    private String TODAY_MST;
    @SerializedName("TODAY_MCT")
    @Expose
    private String TODAY_MCT;

    public String getDATE() {

        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    @SerializedName("DATE")
    @Expose
    private String DATE;




    public String getCURRENT_MST() {
        return CURRENT_MST;
    }

    public void setCURRENT_MST(String CURRENT_MST) {
        this.CURRENT_MST = CURRENT_MST;
    }

    public String getCURRENT_MCT() {
        return CURRENT_MCT;
    }

    public void setCURRENT_MCT(String CURRENT_MCT) {
        this.CURRENT_MCT = CURRENT_MCT;
    }

    public String getTODAY_SSTG() {
        return TODAY_SSTG;
    }

    public void setTODAY_SSTG(String TODAY_SSTG) {
        this.TODAY_SSTG = TODAY_SSTG;
    }

    public String getTODAY_MST() {
        return TODAY_MST;
    }

    public void setTODAY_MST(String TODAY_MST) {
        this.TODAY_MST = TODAY_MST;
    }

    public String getTODAY_MCT() {
        return TODAY_MCT;
    }

    public void setTODAY_MCT(String TODAY_MCT) {
        this.TODAY_MCT = TODAY_MCT;
    }

    public String getCURRENT_SSTG() {

        return CURRENT_SSTG;
    }

    public void setCURRENT_SSTG(String CURRENT_SSTG) {
        this.CURRENT_SSTG = CURRENT_SSTG;
    }
}
