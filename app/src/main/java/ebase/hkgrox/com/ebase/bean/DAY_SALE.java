package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rajeev on 1/2/17.
 */

public class DAY_SALE implements Serializable{

    @SerializedName("SALE")
    @Expose
   private String SALE;

    public String getSALE() {
        return SALE;
    }

    public void setSALE(String sale) {
        this.SALE = sale;
    }

    public String getCOLLECTION() {
        return COLLECTION;
    }

    public void setCOLLECTION(String collection) {
        this.COLLECTION = collection;
    }

    @SerializedName("COLLECTION")
    @Expose
    private String COLLECTION;
}
