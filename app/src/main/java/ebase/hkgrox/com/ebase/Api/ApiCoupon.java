package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.USER;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zoom on 3/5/2018.
 */

public interface ApiCoupon {

    @GET("/ebase/couponstatus.php")
    Call<List<COUPON>> ggetstatus(@Query("coupon") String Coupon);

    @GET("/ebase/getcoupon.php")
    Call<List<COUPON>> getcoupon();

    @FormUrlEncoded
    @POST("/ebase/addcoupon.php")
    Call<List<COUPON>> addcoupon(@Field("points") String points, @Field("is_valid") String isvalid, @Field("valid_til") String valid_til, @Field("date_upload") String date_upload);
}
