package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.COUPON;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zooom HKG on 12/5/2017.
 */

public interface CouponApi {
    @GET("/ebase/coupon.php")
    Call<List<COUPON>> listRespo();
}
