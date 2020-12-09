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

import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiftAdminUserDetail extends AppCompatActivity {
    private RecyclerView rv_list;
    private SearchView sv_search;
    Config config;

    String login_url2 = config.ip_url;
    //private String login_url2 ="http://192.168.0.103";
    private AdminUserGiftAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_admin_user_detail);

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
        final List<CouponRedeem> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        UserCouponApi userCouponApi=retrofit.create(UserCouponApi.class);
        Call<List<CouponRedeem>> call=userCouponApi.usergift(phn);
        call.enqueue(new Callback<List<CouponRedeem>>() {
            @Override
            public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
                List<CouponRedeem> list=response.body();
                CouponRedeem cou=null;
                for(int i=0;i<list.size();i++) {
                    cou=new CouponRedeem();
                    String date=list.get(i).getDATE();
                    String gift=list.get(i).getGIFT();
                    String point=list.get(i).getPOINT();
                    String approved=list.get(i).getAPPROVE();


                    cou.setDATE(date);
                    cou.setGIFT(gift);
                    cou.setPOINT(point);
                    cou.setAPPROVE(approved);
                    arraylist.add(cou);
                }

                adapter=new AdminUserGiftAdapter(GiftAdminUserDetail.this,arraylist);
                rv_list.setAdapter(adapter);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {

            }
        });
    }
}
