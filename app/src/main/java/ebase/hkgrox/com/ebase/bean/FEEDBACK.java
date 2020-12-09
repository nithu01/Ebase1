
package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FEEDBACK implements Serializable {

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("MESSAGE")
    @Expose
    private String MESSAGE;

    @SerializedName("TITLE")
    @Expose
    private String TITLE;

    public String getTIME_STAMP() {
        return TIME_STAMP;
    }

    public void setTIME_STAMP(String TIME_STAMP) {
        this.TIME_STAMP = TIME_STAMP;
    }

    @SerializedName("TIME_STAMP")
    @Expose
    private String TIME_STAMP;


}
