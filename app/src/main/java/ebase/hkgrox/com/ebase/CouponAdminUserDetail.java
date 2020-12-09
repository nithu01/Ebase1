package ebase.hkgrox.com.ebase;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.adapter.AdminUserCouponAdapter;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CouponAdminUserDetail extends AppCompatActivity {
    private RecyclerView rv_list;
    private SearchView sv_search;
    Config config;

    String login_url2 = config.ip_url;
    //private String login_url2 ="http://192.168.0.103";
    private AdminUserCouponAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_admin_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        USER user =(USER) getIntent().getExtras().getSerializable("DATA");
        String phn = user.getMOBILE();
        String nam = user.getNAME();
        //getSupportActionBar().setTitle(phn);
        MUtil.setToolBarNew(this, phn+"("+nam+")", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        sv_search = (SearchView) findViewById(R.id.sv_search);
sv_search.setVisibility(View.GONE);

        MUtil.showProgressDialog(this);
        final List<COUPON> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        UserCouponApi userCouponApi=retrofit.create(UserCouponApi.class);
        Call<List<COUPON>> call=userCouponApi.usercoupon(phn);
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

                adapter=new AdminUserCouponAdapter(CouponAdminUserDetail.this,arraylist);
                rv_list.setAdapter(adapter);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {

            }
        });



    }
}
