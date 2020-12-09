package ebase.hkgrox.com.ebase;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.USER;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 12/7/2017.
 */

public interface UserpointApi {
    @GET("/ebase/userpt.php")
    Call<List<USER>> userpoint(@Query("phn")String phn);
}
