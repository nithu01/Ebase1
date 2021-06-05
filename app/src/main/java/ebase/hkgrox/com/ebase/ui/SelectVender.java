package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ebase.hkgrox.com.ebase.Api.Apiorder;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.recyclervender;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectVender extends AppCompatActivity {

    USER user;
    Button trackvisit,add_newv,dailyvisit;
    List<Vender> arrylist;
    Config config;
    String url=config.ip_url;
    SearchView searchView;
    RecyclerView recyclerView;
    recyclervender adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vender);

        user = (USER) getIntent().getExtras().getSerializable("DATA");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        trackvisit=(Button)findViewById(R.id.trackvisit);
        add_newv=(Button)findViewById(R.id.add_vender);
        dailyvisit=(Button)findViewById(R.id.dailyvisit);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Refine your search");
        //searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.hintSearchMess) + "</font>"));
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Select Buyer",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        trackvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectVender.this,trackvisit.class);
                intent.putExtra("DATA",user);
                startActivity(intent);
            }
        });
        dailyvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectVender.this,DailyVisitReport.class);
                intent.putExtra("DATA",user);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    onSearch(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    onSearch(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apiorder apiorder=retrofit.create(Apiorder.class);
        Call<List<Vender>> call=apiorder.status(user.getMOBILE(),MUtil.getTodayDate());
         // Toast.makeText(this, user.getMOBILE()+MUtil.getTodayDate(), Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {

               List<Vender> list=response.body();
               List<Vender> arraylist=new ArrayList<Vender>();
               Vender vender=null;
               for(int i=0;i<list.size();i++) {

                    vender = new Vender();
                    String party = list.get(i).getParty();
                    String retailer = list.get(i).getRetailer();
                    String segment = list.get(i).getSegment();
                    String pincode = list.get(i).getPincode();
                    String city = list.get(i).getCity();
                    String area = list.get(i).getArea();
                    String address = list.get(i).getAddress();
                    String potential = list.get(i).getPotentail();
                    String phone = list.get(i).getPhone();
                    String date=list.get(i).getDate();

                    String time=list.get(i).getTime();
                    //String state=list.get(i).getState();

                    vender.setDate(date);
                    vender.setParty(party);
                    vender.setRetailer(retailer);
                    vender.setSegment(segment);
                    vender.setPincode(pincode);
                    vender.setCity(city);
                    vender.setArea(area);
                    vender.setTime(time);
                    vender.setAddress(address);
                    vender.setPotentail(potential);
                    vender.setPhone(phone);
                    arraylist.add(vender);

                }
                arrylist=arraylist;
                adapter=new recyclervender(SelectVender.this,arraylist,user);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(SelectVender.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });




        add_newv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Registervender.class);
                intent.putExtra("DATA",user);
                startActivity(intent);
            }
        });


    }
    public void onSearch(String query) {

        ArrayList<Vender> contactsBeenLocal = new ArrayList<>();

        contactsBeenLocal.addAll(arrylist);
        ArrayList<Vender> filteredModelList = new ArrayList<>();
        if (contactsBeenLocal != null) {
            if (query == null || query.length() == 0) {
                filteredModelList.addAll(contactsBeenLocal);
            } else {

                query = query.toLowerCase();
                for (Vender model : contactsBeenLocal) {
                    String text = "";
                    if (model.getParty() != null) {
                        text = model.getParty().toLowerCase();
                    }
                    String handler = "";
                    if (model.getPhone() != null) {
                        handler = model.getPhone().toLowerCase();
                    }
                    if (text.contains(query) || handler.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
            if (adapter != null) {
                adapter.filter(filteredModelList);
            }

        }
    }

}


