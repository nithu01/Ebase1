package ebase.hkgrox.com.ebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.adapter.Premium_Retailer_Adapter;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Premium_Retailer extends AppCompatActivity {

    RecyclerView recyclerView;
    Config config;
    String login_url2 =config.ip_url;
    Premium_Retailer_Adapter premium_retailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium__retailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MUtil.setToolBarNew(this, "Premium Retailer", true);
        MUtil.showProgressDialog(this);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getaddress();
    }
    public void getaddress(){

        final List<USER> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<USER>> call=apiService.getpremiumretailer();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list=response.body();
                Toast.makeText(Premium_Retailer.this,"Failure"+list.get(0).getPOINTS(),Toast.LENGTH_SHORT).show();


                USER user=null;
                USER user1 = new USER();
                arraylist.add(user1);
                for(int i=0;i<list.size();i++){
                    user=new USER();

                    String name=list.get(i).getNAME();
                    String address=list.get(i).getADDRESS();
                    String city=list.get(i).getCITY();
                    String pincode=list.get(i).getPOINTS();
                    String mobile=list.get(i).getMOBILE();

                    user.setNAME(name);
                    user.setADDRESS(address);
                    user.setCITY(city);
                    user.setPINCODE(pincode);
                    user.setMOBILE(mobile);

                    arraylist.add(user);

                }

                premium_retailer=new Premium_Retailer_Adapter(Premium_Retailer.this, arraylist);
                recyclerView.setAdapter(premium_retailer);
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(Premium_Retailer.this,"Failure"+t,Toast.LENGTH_SHORT).show();
            }
        });
    }
}