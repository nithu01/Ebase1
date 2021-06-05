package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.USER;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/**
 * Created by Zooom HKG on 1/2/2018.
 */

public interface Addvender {

    @FormUrlEncoded
    @POST("/ebase/addvendoru.php")
    Call<List<USER>> inseru(@Field("name") String name, @Field("city") String city, @Field("phn") String phn, @Field("address") String address, @Field("email") String email, @Field("password") String password, @Field("pincode") String pincode, @Field("state") String state);

    @FormUrlEncoded
    @POST("/ebase/addvender.php")
    Call<List<USER>> inser(@Field("date") String date,@Field("name") String name, @Field("city") String city, @Field("phn") String phn, @Field("address") String address, @Field("email") String email, @Field("password") String password, @Field("pincode") String pincode, @Field("state") String state, @Field("designation") String usertype, @Field("distributor") String distributor);

    @FormUrlEncoded
    @POST("/ebase/staffform.php")
    Call<List<USER>> staffform(@Field("name") String name,@Field("mobile")String mobile,@Field("email")String email,@Field("location")String location,@Field("password")String password,@Field("position")String position,@Field("ste")String ste,@Field("stm")String stm,@Field("scm")String scm,@Field("enable")String enable);

    @GET("/ebase/staffedit.php")
    Call<List<USER>> retrieve();

    @GET("/ebase/getdistributor.php")
    Call<List<USER>> getdistributor();

    @FormUrlEncoded
    @POST("/ebase/staffupdate.php")
    Call<List<USER>> staffupdate(@Field("name") String name,@Field("mobile")String mobile,@Field("email")String email,@Field("location")String location,@Field("password")String password,@Field("position")String position,@Field("ste")String ste,@Field("stm")String stm,@Field("scm")String scm,@Field("enable")String enable);

    @FormUrlEncoded
    @POST("/ebase/Passwordchange.php")
    Call<List<USER>> updatepass(@Field("oldp") String oldp, @Field("newp") String newp);

    @FormUrlEncoded
    @POST("/ebase/updateuser.php")
    Call<List<USER>> updateuser(@Field("phone") String phone, @Field("enable") String enable);

    @FormUrlEncoded
    @POST("/ebase/Forget.php")
    Call<List<USER>> forget(@Field("mobile") String mobile, @Field("email") String email);

    @FormUrlEncoded
    @POST("/ebase/updateexecutive.php")
    Call<List<USER>> updateexecutive(@Field("mobile")String mobile,@Field("date")String date,@Field("csstg")String csstg,@Field("tsstg")String tsstg);

    @FormUrlEncoded
    @POST("/ebase/updatemanager.php")
    Call<List<USER>> updatemanager(@Field("mobile")String mobile,@Field("date")String date,@Field("cmct")String cmct,@Field("cmst")String cmst,@Field("tmct")String tmct,@Field("tmst")String tmst);

    @GET("/ebase/executivecompiled.php")
    Call<List<USER>> executivecompiled(@Query("month")String month, @Query("year")String year);

    @GET("/ebase/managercompiled.php")
    Call<List<USER>> managercompiled(@Query("month")String month,@Query("year")String year);

    @FormUrlEncoded
    @POST("/ebase/updateman.php")
    Call<List<USER>> updateman(@Field("mobile")String mobile,@Field("cmct")String cmct,@Field("cmst")String cmst);

    @FormUrlEncoded
    @POST("/ebase/updateexe.php")
    Call<List<USER>> updateexe(@Field("mobile")String mobile,@Field("csstg")String csstg);


}
