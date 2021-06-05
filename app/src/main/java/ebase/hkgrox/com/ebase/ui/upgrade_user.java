package ebase.hkgrox.com.ebase.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.AddressAdapter;
import ebase.hkgrox.com.ebase.adapter.upgrade_adpater;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class upgrade_user extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    upgrade_adpater adapter;
    List<USER> arrayList;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_user);
        initt();
        //setadpter();
        getdata();

    }

    public void getdata(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<USER>> call=apiService.getuserdetails();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {

                List<USER> list=response.body();
                USER user=null;
//                USER user1 = new USER();
//                arraylist.add(user1);
                  for(int i=0;i<list.size();i++){
                    user=new USER();
                    //Toast.makeText(UserAddress.this,""+list.get(i).getNAME(),Toast.LENGTH_SHORT).show();
                    String name=list.get(i).getNAME();
                    String mobile=list.get(i).getMOBILE();

                    String points=list.get(i).getPOINTS();


                    user.setNAME(name);


                    user.setPOINTS(points);
                    user.setMOBILE(mobile);

                    arrayList.add(user);
//                    userList = arraylist;

                }
                MUtil.dismissProgressDialog();
                recyclerView.setLayoutManager(new LinearLayoutManager(upgrade_user.this));
                adapter = new upgrade_adpater(upgrade_user.this,arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
            }
        });
    }

    public void setadpter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new upgrade_adpater(this,arrayList);
        recyclerView.setAdapter(adapter);
    }

    public void initt(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Upgrade User",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchView=findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(this);
        arrayList=new ArrayList<>();
        MUtil.showProgressDialog(this);
        recyclerView=findViewById(R.id.recyclerview);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("TAG","userdata"+query);
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("TAG","userdata"+newText);
        adapter.getFilter().filter(newText);
        return false;
    }
}