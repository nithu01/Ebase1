package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPointResponse {

    @SerializedName("Sno")
    @Expose
    private String sno;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Current_Point")
    @Expose
    private String currentPoint;
    @SerializedName("Total_Points")
    @Expose
    private String totalPoints;

    @SerializedName("Effective_Point")
    @Expose
    private String Effective_Point;

    public String getEffective_Point() {
        return Effective_Point;
    }

    public void setEffective_Point(String effective_Point) {
        Effective_Point = effective_Point;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint = currentPoint;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }
}
