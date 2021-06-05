package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.AddressAdapter;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAddress extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    AddressAdapter addressAdapter;
    Config config;
    String login_url2 =config.ip_url;
    SearchView sv_search;
    List<USER> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sv_search = (SearchView) findViewById(R.id.sv_research);
       // getaddress();
        sv_search.setOnQueryTextListener(this);
        final List<USER> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
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
                    String address=list.get(i).getADDRESS();
                    String city=list.get(i).getCITY();
                    String pincode=list.get(i).getPINCODE();
                    String state=list.get(i).getSTATE();

                    user.setNAME(name);
                    user.setADDRESS(address);
                    user.setCITY(city);
                    user.setPINCODE(pincode);
                    user.setSTATE(state);

                    arraylist.add(user);
//                    userList = arraylist;

                }
                addressAdapter=new AddressAdapter(UserAddress.this, arraylist);
                recyclerView.setAdapter(addressAdapter);
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(UserAddress.this,"Failure"+t,Toast.LENGTH_SHORT).show();
            }
        });

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TAG","userdata"+query);
                    addressAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    addressAdapter.getFilter().filter(newText);

                return false;
            }
        });
        MUtil.setToolBarNew(this, "User Address", true);
        MUtil.showProgressDialog(this);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    public void getaddress(){
//
//        final List<USER> arraylist=new ArrayList<>();
//        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
//        ApiService apiService=retrofit.create(ApiService.class);
//        Call<List<USER>> call=apiService.getuserdetails();
//        call.enqueue(new Callback<List<USER>>() {
//            @Override
//            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
//
//                List<USER> list=response.body();
//                USER user=null;
//                USER user1 = new USER();
//                arraylist.add(user1);
//                for(int i=0;i<list.size();i++){
//                    user=new USER();
//                    String name=list.get(i).getNAME();
//                    String address=list.get(i).getADDRESS();
//                    String city=list.get(i).getCITY();
//                    String pincode=list.get(i).getPINCODE();
//                    String state=list.get(i).getSTATE();
//
//                    user.setNAME(name);
//                    user.setADDRESS(address);
//                    user.setCITY(city);
//                    user.setPINCODE(pincode);
//                    user.setSTATE(state);
//
//                    arraylist.add(user);
//                    userList = arraylist;
//
//                }
//                addressAdapter=new AddressAdapter(UserAddress.this, arraylist);
//                recyclerView.setAdapter(addressAdapter);
//                MUtil.dismissProgressDialog();
//            }
//
//            @Override
//            public void onFailure(Call<List<USER>> call, Throwable t) {
//                Toast.makeText(UserAddress.this,"Failure"+t,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public void onSearch(String query) {
//
//        ArrayList<USER> contactsBeenLocal = new ArrayList<>();
//
//        contactsBeenLocal.addAll(userList);
//        ArrayList<USER> filteredModelList = new ArrayList<>();
//        if (contactsBeenLocal != null) {
//            if (query == null || query.length() == 0) {
//                filteredModelList.addAll(contactsBeenLocal);
//            } else {
//
//                query = query.toLowerCase();
//                for (USER model : contactsBeenLocal) {
//                    String text = "";
//                    if (model.getNAME() != null) {
//                        text = model.getNAME().toLowerCase();
//                    }
//                    String handler = "";
//                    if (model.getMOBILE() != null) {
//                        handler = model.getMOBILE().toLowerCase();
//                    }
//                    String phone = "";
//                    if (model.getEMAIL() != null) {
//                        phone = model.getEMAIL().toLowerCase();
//                    }
//
//                    if (text.contains(query) || handler.contains(query) || phone.contains(query)) {
//                        filteredModelList.add(model);
//                    }
//                }
//            }
//            if (addressAdapter != null) {
//                addressAdapter.filter(filteredModelList);
//            }
////            if (adapter != null) {
////                adapter.filter(filteredModelList);
////            }
//        }
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}