package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/29/2017.
 */

public interface UserCouponApi {
    @GET("/ebase/usercoupon.php")
    Call<List<COUPON>> usercoupon(@Query("phn")String phn);

    @GET("/ebase/newreport.php")
    Call<List<COUPON>> newreport(@Query("phn")String phn);

    @GET("/ebase/oldreport.php")
    Call<List<COUPON>> oldreport(@Query("phn")String phn);

    @GET("/ebase/usergift.php")
    Call<List<CouponRedeem>> usergift(@Query("phn")String phn);
}
