package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.Vender;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface Apiorder {

    @FormUrlEncoded
    @POST("/ebase/registervender.php")
    Call<List<Vender>> register(@Field("executive_name")String executive_name,@Field("executive_phone")String executive_phone,@Field("state")String state,@Field("party")String party,@Field("cperson")String cperson,@Field("mobile2")String mobile2, @Field("phone") String phone,@Field("email")String email, @Field("retailer")String retailer, @Field("segment")String segment, @Field("pincode")String pincode, @Field("city")String city, @Field("area")String area, @Field("address")String address, @Field("potential")String potential,@Field("date")String date);

    @GET("/ebase/getordervender.php")
    Call<List<Vender>> getordervender();

    @FormUrlEncoded
    @POST("/ebase/placeorder.php")
   // Call<List<Vender>> order(@Field("executive_name")String executive_name,@Field("executive_phone")String executive_phone,@Field("party")String party, @Field("phone") String phone, @Field("contact_person")String contact_person, @Field("e_order")String e_order, @Field("year")String year,@Field("month")String month,@Field("day")String day,  @Field("time")String time,@Field("remark")String remark, @Field("status")String status);
    Call<List<Vender>> order(@Field("checkin")String checkin,@Field("checkout")String checkout,@Field("executive_name")String executive_name,@Field("executive_phone")String executive_phone,@Field("party")String party, @Field("phone") String phone, @Field("e_order")String e_order,@Field("o_detail")String orderdetail,@Field("checkindate")String checkin_date, @Field("date")String date,  @Field("time")String time,@Field("checkin_time")String checkin_time,@Field("remark")String remark,@Field("status")String status);

    @GET("/ebase/status.php")
    Call<List<Vender>> status(@Query("executive_phone")String mobile,@Query("date")String date);
}
