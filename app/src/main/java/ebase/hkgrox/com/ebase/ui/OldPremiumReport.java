package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UserCouponApi;
import ebase.hkgrox.com.ebase.adapter.AdminUserCouponAdapter;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.CurrentPointResponse;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OldPremiumReport extends AppCompatActivity {

    LinearLayout linearLayout;
    private RecyclerView rv_list;
    private SearchView sv_search;
    Config config;
    TextView txt_curr,txt_effect_point,txt_total,txt_check;
    String login_url2 = config.ip_url;
    //private String login_url2 ="http://192.168.0.103";
    private AdminUserCouponAdapter adapter;
    USER user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_premium_report);


        txt_curr=findViewById(R.id.month_point);
        txt_effect_point=findViewById(R.id.effective_point);
        txt_total=findViewById(R.id.total);
//        txt_check=findViewById(R.id.check);
//        txt_check.setPaintFlags(txt_check.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user =(USER) getIntent().getExtras().getSerializable("DATA");
//        txt_check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(OldPremiumReport.this, CheckAllMonthPremiumRetailer.class).putExtra("DATA",user.getNAME()));
//            }
//        });
        String phn = user.getMOBILE();
        String nam = user.getNAME();
//        if(user.getDEGINATION().equals("Premium Retailer")){
//            linearLayout.setVisibility(View.VISIBLE);
//            getcurrentpoint();
//        }
        getcurrentpoint();
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
        Call<List<COUPON>> call=userCouponApi.oldreport(phn);
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

                adapter=new AdminUserCouponAdapter(OldPremiumReport.this,arraylist);
                rv_list.setAdapter(adapter);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {

            }
        });



    }
    public void getcurrentpoint(){
        MUtil.showProgressDialog(this);
        //   Log.d("TAG","DATEeee"+txt_start.getText().toString()+txt_end.getText().toString());
//        ApiService apiService=new Retrofit.Builder().baseUrl(Config.ip_url).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
//        CompositeDisposable compositeDisposiable=new CompositeDisposable();
//        compositeDisposiable.add(apiService.getReport(String.valueOf(month+1),String.valueOf(year))
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(this::handleResponse,this::handleError));
        //  Toast.makeText(OldPremiumReport.this,""+user.getMOBILE(),Toast.LENGTH_SHORT).show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<CurrentPointResponse>> call=apiService.getcurrentPoint(user.getMOBILE());
        call.enqueue(new Callback<List<CurrentPointResponse>>() {
            @Override
            public void onResponse(Call<List<CurrentPointResponse>> call, Response<List<CurrentPointResponse>> response) {
                //Toast.makeText(OldPremiumReport.this,""+response.body().size(),Toast.LENGTH_SHORT).show();
                try{
                    if(response.body().size()!=0){
                        txt_curr.setText(response.body().get(0).getCurrentPoint());
                        txt_effect_point.setText(response.body().get(0).getEffective_Point());
                        txt_total.setText(response.body().get(0).getTotalPoints());
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<List<CurrentPointResponse>> call, Throwable t) {
                Toast.makeText(OldPremiumReport.this,""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }
}