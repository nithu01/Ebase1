package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rajeev on 1/2/17.
 */

public class CouponRedeem implements Serializable {

    @SerializedName("POINT")
    @Expose
    private String POINT;
    @SerializedName("srn")
    @Expose
    private String srn;

    public String getSrn() {
        return srn;
    }

    public void setSrn(String srn) {
        this.srn = srn;
    }

    @SerializedName("Bpr")
    @Expose
    private String Bpr;

    @SerializedName("Apr")
    @Expose
    private String Apr;


    @SerializedName("TP")
    @Expose
    private String TP;

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }

    @SerializedName("DOJ")
    @Expose
    private String doj;

    public String getBpr() {
        return Bpr;
    }

    public void setBpr(String bpr) {
        Bpr = bpr;
    }

    public String getApr() {
        return Apr;
    }

    public void setApr(String apr) {
        Apr = apr;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
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

    @SerializedName("GIFT")
    @Expose
    private String GIFT;

    public String getAPPROVE() {
        return APPROVE;
    }

    public void setAPPROVE(String APPROVE) {
        this.APPROVE = APPROVE;
    }

    @SerializedName("DATE")
    @Expose

    private String DATE;

    @SerializedName("MOBILE")
    @Expose
    private String MOBILE;

    @SerializedName("APPROVE")
    @Expose
    private String APPROVE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @SerializedName("NAME")
    @Expose
    private String NAME;

    public String getPOINT() {
        return POINT;
    }

    public void setPOINT(String POINT) {
        this.POINT = POINT;
    }

    public String getGIFT() {
        return GIFT;
    }

    public void setGIFT(String GIFT) {
        this.GIFT = GIFT;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }
}
