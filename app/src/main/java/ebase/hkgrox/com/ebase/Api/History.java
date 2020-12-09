package ebase.hkgrox.com.ebase.Api;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.DAY_SALE;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zooom HKG on 1/9/2018.
 */

public interface History {
    @GET("/ebase/historymonth.php")
    Call<List<DAY_SALE>> historymonth(@Query("phn") String phn,@Query("year") String year,@Query("month") String month);

    @GET("/ebase/historytoday.php")
    Call<List<DAY_SALE>> historytoday(@Query("phn") String phn,@Query("year") String year,@Query("month") String month,@Query("day") String day);

@FormUrlEncoded
    @POST("/ebase/addsale.php")
    Call<List<DAY_SALE>> addsale(@Field("phn") String phn,@Field("year") String year,@Field("month") String month,@Field("day") String day,@Field("sale") String sale);

    @FormUrlEncoded
    @POST("/ebase/addsalemanager.php")
    Call<List<DAY_SALE>> addsalemanager(@Field("phn") String phn,@Field("year") String year,@Field("month") String month,@Field("day") String day,@Field("sale") String sale,@Field("collection")String collection);
}
