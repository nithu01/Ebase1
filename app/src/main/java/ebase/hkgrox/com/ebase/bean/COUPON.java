package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rajeev on 1/2/17.
 */

public class COUPON implements Serializable {

/*
    public COUPON() {
    }

    public COUPON(String availed_by_email, String availed_by_mobile, String availed_by_name, String coupon, String date_availed, String date_upload, String is_availed, String points, String valid_til) {
    this.availed_by_email=availed_by_email;
        this.availed_by_mobile=availed_by_mobile;
        this.availed_by_name=availed_by_name;
        this.coupon=coupon;this.date_availed=date_availed;
        this.date_upload=date_upload;
        this.is_availed=is_availed;
        this.points=points;
        this.valid_til=valid_til;
    }
*/


    public String getValid_til() {
        return valid_til;
    }

    public void setValid_til(String VALID_TIL) {
        this.valid_til = VALID_TIL;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getIs_availed() {
        return is_availed;
    }

    public void setIs_availed(String is_availed) {
        this.is_availed = is_availed;
    }

    @SerializedName("sno")
    @Expose
    private String sno;
    @SerializedName("totalcoupon")
    @Expose
    private String totalcoupon;

    public String getTotalcoupon() {
        return totalcoupon;
    }

    @SerializedName("availedcoupon")
    @Expose
    private String availedcoupon;

    public String getAvailedcoupon() {
        return availedcoupon;
    }

    public void setAvailedcoupon(String availedcoupon) {
        this.availedcoupon = availedcoupon;
    }

    public void setTotalcoupon(String totalcoupon) {
        this.totalcoupon = totalcoupon;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    @SerializedName("valid_til")
    @Expose
    private String valid_til;

    @SerializedName("points")
    @Expose
    private String points;


    @SerializedName("coupon")
    @Expose
   private String coupon;


    @SerializedName("is_availed")
    @Expose
   private String is_availed;


    @SerializedName("availed_by_mobile")
    @Expose
    private String availed_by_mobile;

    public String getAvailed_by_email() {
        return availed_by_email;
    }

    public void setAvailed_by_email(String availed_by_email) {
        this.availed_by_email = availed_by_email;
    }

    public String getAvailed_by_mobile() {
        return availed_by_mobile;
    }

    public void setAvailed_by_mobile(String availed_by_mobile) {
        this.availed_by_mobile = availed_by_mobile;
    }

    public String getAvailed_by_name() {
        return availed_by_name;
    }

    public void setAvailed_by_name(String availed_by_name) {
        this.availed_by_name = availed_by_name;
    }

    public String getDate_availed() {
        return date_availed;
    }


    public void setDate_availed(String date_availed) {
        this.date_availed = date_availed;
    }

    @SerializedName("availed_by_email")
    @Expose
    private String availed_by_email;

    @SerializedName("availed_by_name")
    @Expose
    private String availed_by_name;

    @SerializedName("date_availed")
    @Expose
    private String date_availed;

    public String getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(String date_upload) {
        this.date_upload = date_upload;
    }

    @SerializedName("date_upload")
    @Expose
    private String date_upload;


}
