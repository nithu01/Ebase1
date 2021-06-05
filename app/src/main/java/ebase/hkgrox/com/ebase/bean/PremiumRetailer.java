package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.SerializedName;

public class PremiumRetailer {
    @SerializedName("Name")
    String message;
    @SerializedName("Mobile")
    String status;
    @SerializedName("Month")
    String Month;
    @SerializedName("Current_Point")
    String Current_Point;

    @SerializedName("Effective_Point")
    String Effective_Point;

    public String getEffective_Point() {
        return Effective_Point;
    }

    public void setEffective_Point(String effective_Point) {
        Effective_Point = effective_Point;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getCurrent_Point() {
        return Current_Point;
    }

    public void setCurrent_Point(String current_Point) {
        Current_Point = current_Point;
    }
}
