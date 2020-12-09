package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.USER;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zooom HKG on 12/14/2017.
 */

public interface GetUserPointApi {
    @GET("/ebase/alluserpoints.php")
    Call<List<USER>> getdetails();
}
