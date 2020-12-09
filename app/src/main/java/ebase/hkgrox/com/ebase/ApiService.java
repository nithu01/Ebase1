package ebase.hkgrox.com.ebase;

import java.util.List;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.Registerdevice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/1/2017.
 */

public interface ApiService {

    @GET("/ebase/couponexcel.php")
    Call<List<COUPON>> listRespo(@Query("from")String from,@Query("to")String to);

    @GET("/ebase/registerdevice.php")
    Call<List<Registerdevice>> storetoken(@Query("token")String token, @Query("mobile")String mobile);

}
