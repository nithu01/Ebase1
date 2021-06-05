package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.PremiumRetailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/8/2017.
 */

public interface GetgiftApi {
    @GET("/ebase/getgift.php")
    Call<List<CouponRedeem>> getdetails();

    @POST("/ebase/getPremiumretailerpoints.php")
    Call<List<PremiumRetailer>> getpremiumretailer(@Query("name")String name);
//
//    @GET("/ebase/redeemgift.php")
//    Call<String> requestgift();
}
