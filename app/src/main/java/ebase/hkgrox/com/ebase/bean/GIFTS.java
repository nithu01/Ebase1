package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zooom HKG on 1/16/2018.
 */

public class GIFTS {
    @SerializedName("points")
    String points;
    @SerializedName("gift")
    String gift;
    @SerializedName("approve")
    String approve;
    @SerializedName("date")
    String date;
    @SerializedName("mobile")
    String mobile;

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    String name;
    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
