package ebase.hkgrox.com.ebase.Api;

import java.util.List;
import ebase.hkgrox.com.ebase.bean.Vender;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apireport {

    @FormUrlEncoded
    @POST("/ebase/reportname.php")
    Call<List<Vender>> byname(@Field("date") String date);


    @FormUrlEncoded
    @POST("/ebase/reportdate.php")
    Call<List<Vender>> bydate(@Field("sdate") String sdate,@Field("edate") String edate);

    @GET("/ebase/fetchdata.php")
    Call<List<Vender>> getdata();

    @GET("/ebase/fetcharea.php")
    Call<List<Vender>> getarea(@Query("state")String state);

    @GET("/ebase/fetchhvender.php")
    Call<List<Vender>> getvender(@Query("party")String party,@Query("phone")String phone,@Query("date")String date,@Query("time")String time);

    @GET("/ebase/fetchparty.php")
    Call<List<Vender>> getparty(@Query("area")String area);

    @GET("/ebase/getdetails.php")
    Call<List<Vender>> getdetails(@Query("party")String party);

    @FormUrlEncoded
    @POST("/ebase/trackvisit.php")
    Call<List<Vender>> gettrackvisit(@Field("sdate") String sdate,@Field("edate") String edate,@Field("executive_phone")String executive_name);

}
