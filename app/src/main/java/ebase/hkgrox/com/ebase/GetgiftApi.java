package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zooom HKG on 12/8/2017.
 */

public interface GetgiftApi {
   @GET("/ebase/getgift.php")
    Call<List<CouponRedeem>> getdetails();
//
//    @GET("/ebase/redeemgift.php")
//    Call<String> requestgift();
}
