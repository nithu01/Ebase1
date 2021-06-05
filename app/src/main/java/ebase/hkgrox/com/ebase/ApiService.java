package ebase.hkgrox.com.ebase;

import java.util.List;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.CurrentPointResponse;
import ebase.hkgrox.com.ebase.bean.Notification;
import ebase.hkgrox.com.ebase.bean.Registerdevice;
import ebase.hkgrox.com.ebase.bean.ReportReponse;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.ui.Message;
import ebase.hkgrox.com.ebase.ui.PremiumRetailerReport;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/ebase/getpremiumretailer.php")
    Call<List<ReportReponse>> getReport(@Query("date") String date);

    @GET("/ebase/prretailertally.php")
    Call<List<CouponRedeem>> pr_tally();

    @GET("/ebase/prretailerpoints.php")
    Call<List<CouponRedeem>> pr_points(@Query("MOBILE") String mobile);

    @POST("/ebase/savepremiumretailer.php")
    Call<List<ReportReponse>> saveretailer(@Query("monthh") String month,@Query("date") String date);

    @POST("/ebase/savedoublepoints.php")
    Call<com.google.android.gms.common.api.Response> doublepoints(@Query("monthh") String month);

    @POST("/ebase/savetotalpoint.php")
    Call<List<ReportReponse>> totalpoints();

    @POST("/ebase/getcurrentpt.php")
    Call<List<CurrentPointResponse>> getcurrentPoint(@Query("phn") String mobile);

//    @GET("/ebase/savepremiumretiler.php")
//    Observable<List<ReportReponse>> saveretailer(@Query("month") String date,@Query("year") String message);

    @GET("/ebase/getpremiumpoints.php")
    Observable<List<ReportReponse>> getpremiumpoint(@Query("date") String date,@Query("mobile") String mobile);

    @GET("/ebase/viewmessage.php")
    Call<List<Message>> viewmessage();

    @FormUrlEncoded
    @POST("/ebase/addmessage.php")
    Call<Notification> addmessage(@Field("Date") String date,@Field("Message") String message);

    @GET("/ebase/couponexcel.php")
    Call<List<COUPON>> listRespo(@Query("from")String from,@Query("to")String to);

    @GET("/ebase/registerdevice.php")
    Call<List<Registerdevice>> storetoken(@Query("token")String token, @Query("mobile")String mobile);

    @GET("/ebase/getpremiumretailer.php")
    Call<List<USER>> getpremiumretailer();

    @GET("/ebase/getuserdetails.php")
    Call<List<USER>> getuserdetails();

    @POST("/ebase/upgrade_user.php")
    Call<List<USER>> upgrade_user(@Query("phn")String phn,@Query("date")String date);

    @FormUrlEncoded
    @POST("/ebase/sendnotification.php")
    Call<List<Notification>> sendnotification(@Field("message") String message);

}
