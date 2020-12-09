package ebase.hkgrox.com.ebase.util;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.COUPON;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zooom HKG on 12/5/2017.
 */

public interface AvailedcouponApi {

    @GET("/ebase/availedcoupon.php")
    Call<List<COUPON>> getcoupon();
}
