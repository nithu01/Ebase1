package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.CouponAdminUserDetail;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UserCouponApi;
import ebase.hkgrox.com.ebase.adapter.AdminUserCouponAdapter;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Coupondetails extends AppCompatActivity {

    AdminUserCouponAdapter adapter;
    String login_url2 = Config.ip_url;
    String user ;
    RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupondetails);
//        MUtil.setToolBarNew(this,"Coupon Details",true);
        MUtil.showProgressDialog(Coupondetails.this);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        user = getIntent().getStringExtra("mobile");
      //  Toast.makeText(Coupondetails.this,""+user,Toast.LENGTH_SHORT).show();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        UserCouponApi userCouponApi=retrofit.create(UserCouponApi.class);
        final List<COUPON> arraylist=new ArrayList<>();
        Call<List<COUPON>> call=userCouponApi.newreport(user);
        call.enqueue(new Callback<List<COUPON>>() {
            @Override
            public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                List<COUPON> list=response.body();
                COUPON cou=null;
                for(int i=0;i<list.size();i++) {
                    cou=new COUPON();
                    String date=list.get(i).getDate_availed();
                    String coupon=list.get(i).getCoupon();
                    String point=list.get(i).getPoints();

                    cou.setDate_availed(date);
                    cou.setCoupon(coupon);
                    cou.setPoints(point);
                    arraylist.add(cou);
                }

                adapter=new AdminUserCouponAdapter(Coupondetails.this,arraylist);
                rv_list.setAdapter(adapter);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {

            }
        });
    }
}