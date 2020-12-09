package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.GIFTS;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/13/2017.
 */

public interface GiftpointApi {
    @GET("/ebase/giftpt.php")
    Call<List<CouponRedeem>> giftpoint(@Query("phn")String phn);

    @GET("/ebase/giftstatus.php")
    Call<List<GIFTS>> giftstatus();
}
