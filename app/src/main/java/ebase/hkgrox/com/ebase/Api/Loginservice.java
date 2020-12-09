package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.USER;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 1/3/2018.
 */

public interface Loginservice {

    @GET("/ebase/loginservice.php")
    Call<List<USER>> login(@Query("phn")String phn);

}
