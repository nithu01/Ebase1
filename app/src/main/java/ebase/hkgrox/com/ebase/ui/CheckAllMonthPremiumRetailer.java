package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.GetgiftApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.CheckAllPremiumRetailerAdapter;
import ebase.hkgrox.com.ebase.bean.PremiumRetailer;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckAllMonthPremiumRetailer extends AppCompatActivity {

    CheckAllPremiumRetailerAdapter checkAllMonthPremiumRetailer;
    RecyclerView recyclerView;
    ArrayList<PremiumRetailer> arrayList=new ArrayList<>();
    String user;
    TextView check_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_all_month_premium_retailer);
        check_coupon= findViewById(R.id.check_coupon);
        user = getIntent().getStringExtra("DATA");
        check_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckAllMonthPremiumRetailer.this,Coupondetails.class).putExtra("mobile",user));
            }
        });

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MUtil.showProgressDialog(CheckAllMonthPremiumRetailer.this);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
        GetgiftApi getgiftApi=retrofit.create(GetgiftApi.class);
        //Toast.makeText(CheckAllMonthPremiumRetailer.this, ""+user, Toast.LENGTH_SHORT).show();
        Call<List<PremiumRetailer>> call=getgiftApi.getpremiumretailer(user.trim());
        call.enqueue(new Callback<List<PremiumRetailer>>() {
            @Override
            public void onResponse(Call<List<PremiumRetailer>> call, Response<List<PremiumRetailer>> response) {
                List<PremiumRetailer> list=response.body();
                PremiumRetailer cou=null;

                for(int i=0; i<list.size() ;i++) {
                    cou = new PremiumRetailer();
                   // Toast.makeText(CheckAllMonthPremiumRetailer.this,""+list.get(0).getMonth(),Toast.LENGTH_SHORT).show();

                    String srn=list.get(i).getMonth();
                    String approve = list.get(i).getCurrent_Point();

                    cou.setMonth(srn);
                    cou.setCurrent_Point(approve);
                    cou.setEffective_Point(list.get(i).getEffective_Point());
                    arrayList.add(cou);

                }

                checkAllMonthPremiumRetailer=new CheckAllPremiumRetailerAdapter(CheckAllMonthPremiumRetailer.this, arrayList);
                recyclerView.setAdapter(checkAllMonthPremiumRetailer);
                MUtil.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<List<PremiumRetailer>> call, Throwable t) {
               // Toast.makeText(getApplicationContext(),""+t,Toast.LENGTH_SHORT).show();
            }
        });
    }
}