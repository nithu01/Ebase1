package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.GIFTS;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 1/16/2018.
 */

public interface ApiGifts {
    @GET("/ebase/giftprice.php")
    Call<List<GIFTS>> getpoint();

    @GET("/ebase/giftitems.php")
    Call<List<GIFTS>> getitems(@Query("points")String points);
}
