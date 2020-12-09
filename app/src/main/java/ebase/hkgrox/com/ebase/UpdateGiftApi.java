package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/15/2017.
 */

public interface UpdateGiftApi {
    @GET("/ebase/acceptgift.php")
    Call<List<CouponRedeem>> acceptgift(@Query("srn")String srn);
}
