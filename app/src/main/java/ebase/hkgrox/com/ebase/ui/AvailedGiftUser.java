package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.GetUserGiftApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.GiftAdapterUser;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvailedGiftUser extends AppCompatActivity implements View.OnClickListener {
    private USER user;
    private RecyclerView rv_list;
    Config config;

    String login_url2 =config.ip_url;
    // String login_url2 = "http://192.168.0.103";
    private TextView userName;
    String phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availed_gift_user);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        MUtil.showProgressDialog(this);
        findViews();
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        setData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Availed Gifts", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setData() {

        phn=user.getMOBILE();

        usergift();
        userName.setText("Welcome "+user.getNAME());

    }

    private void usergift() {

        final List<CouponRedeem> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        GetUserGiftApi getusergiftapi=retrofit.create(GetUserGiftApi.class);
        Call<List<CouponRedeem>> call=getusergiftapi.getdetails(phn);
        call.enqueue(new Callback<List<CouponRedeem>>() {
            @Override
            public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
                List<CouponRedeem> list=response.body();
                CouponRedeem cou=null;
                for(int i=0;i<list.size();i++) {
                    cou = new CouponRedeem();
                    String srn=list.get(i).getSrn();
                    String approve = list.get(i).getAPPROVE();
                    String date = list.get(i).getDATE();
                    String gift = list.get(i).getGIFT();
                    String mobile = list.get(i).getMOBILE();
                    String name = list.get(i).getNAME();
                    String points = list.get(i).getPOINT();

                    cou.setSrn(srn);
                    cou.setAPPROVE(approve);
                    cou.setDATE(date);
                    cou.setGIFT(gift);
                    cou.setMOBILE(mobile);
                    cou.setNAME(name);
                    cou.setPOINT(points);
                    arraylist.add(cou);

                }

                rv_list.setAdapter(new GiftAdapterUser(AvailedGiftUser.this, arraylist));




                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {

            }
        });
    }

    // private TextView tv;
    private void findViews() {
        userName= (TextView) findViewById(R.id.tv_name);
    }
    @Override
    public void onClick(View view) {

    }
}
