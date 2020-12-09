package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/18/2017.
 */

public interface GetUserGiftApi {
    @GET("/ebase/getusergift.php")
    Call<List<CouponRedeem>> getdetails(@Query("phn")String phn);
}
