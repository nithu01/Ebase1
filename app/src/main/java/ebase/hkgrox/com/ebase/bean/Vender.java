package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Zooom HKG on 1/17/2018.
 */

public class Vender implements Serializable,Cloneable{

    @SerializedName("checkin_date")
    String checkin_date;

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    @SerializedName("date")
    String date;

    @SerializedName("mobile2")
    String mobile2;

    @SerializedName("orderdetail")
    String orderdetail;

    @SerializedName("checkin")
    String checkin;

    @SerializedName("checkout")
    String checkout;

    @SerializedName("email")
    String email;

    @SerializedName("party")
    String party;

    @SerializedName("retailer_workshop")
    String retailer;

    @SerializedName("segment")
    String segment;

    @SerializedName("pincode")
    String pincode;

    @SerializedName("city")
    String city;

    @SerializedName("month")

    String month;

    @SerializedName("day")
    String day;

    @SerializedName("time")
    String time;

    @SerializedName("checkin_time")
    String checkintime;

    @SerializedName("area")
    String area;

    @SerializedName("address")
    String address;

    @SerializedName("potential_pm")
    String potential;

    @SerializedName("vender_phone")
    String vender_phone;


    @SerializedName("contact_person")
    String contact_person;

    @SerializedName("euroils_order")
    String e_order;

    @SerializedName("year")
    String year;
    @SerializedName("executive_name")
    String e_name;

    @SerializedName("state")
    String state;

    @SerializedName("executive_phone")
    String e_phone;

    @SerializedName("remarks")
    String remark;

    @SerializedName("status")
    String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(String orderdetail) {
        this.orderdetail = orderdetail;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_phone() {
        return e_phone;
    }

    public void setE_phone(String e_phone) {
        this.e_phone = e_phone;
    }

    public String getPotential() {
        return potential;
    }

    public void setPotential(String potential) {
        this.potential = potential;
    }

    public String getVender_phone() {
        return vender_phone;
    }

    public void setVender_phone(String vender_phone) {
        this.vender_phone = vender_phone;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getE_order() {
        return e_order;
    }

    public void setE_order(String e_order) {
        this.e_order = e_order;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }



    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPotentail() {
        return potential;
    }

    public void setPotentail(String potentail) {
        this.potential = potentail;
    }

    public String getPhone() {
        return vender_phone;
    }

    public void setPhone(String vender_phone) {
        this.vender_phone = vender_phone;
    }
}
