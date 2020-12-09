package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.GetgiftApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.AdapterExecutives;
import ebase.hkgrox.com.ebase.adapter.GiftAdapter;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiftDetails extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView rv_list;
    private SearchView sv_search;
    private AdapterExecutives adapter;
    private List<USER> userList;
    Config config;
    GiftAdapter giftsadapter;
    String login_url2 =config.ip_url;
    // String login_url2 = "http://192.168.0.103";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String page = getIntent().getExtras().getString("PAGE");
        MUtil.setToolBarNew(this, "Requested Gift", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        sv_search = (SearchView) findViewById(R.id.sv_search);
        sv_search.setOnQueryTextListener(this);

     // sv_search.setVisibility(View.GONE);
       /* if (page.equalsIgnoreCase("staff_edit") || page.equalsIgnoreCase("vender")) {
            sv_search.setVisibility(View.VISIBLE);
        } else {
            sv_search.setVisibility(View.GONE);
        }*/


        MUtil.showProgressDialog(this);
        final List<CouponRedeem> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        GetgiftApi getgiftApi=retrofit.create(GetgiftApi.class);
        Call<List<CouponRedeem>> call=getgiftApi.getdetails();
        call.enqueue(new Callback<List<CouponRedeem>>() {
            @Override
            public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
               // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
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
                giftsadapter=new GiftAdapter(GiftDetails.this, arraylist);
                rv_list.setAdapter(giftsadapter);




                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {

            }
        });
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    giftsadapter.getFilter().filter(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    giftsadapter.getFilter().filter(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

/*
        DatabaseReference databaseReferencenew = AppUtil.getGiftReference(this);
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = "";
                List<CouponRedeem> day_sales = new ArrayList<CouponRedeem>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CouponRedeem couponRedeem = postSnapshot.getValue(CouponRedeem.class);
                    day_sales.add(couponRedeem);

                }
                rv_list.setAdapter(new GiftAdapter(GiftDetails.this, day_sales));
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

       /* if (page.equalsIgnoreCase("points")) {
            MUtil.setToolBarNew(this, "Points Details", true);


        }

        if (page.equalsIgnoreCase("executives")) {
            MUtil.setToolBarNew(this, "Executives Compiled", true);


        }

        if (page.equalsIgnoreCase("managers")) {
            MUtil.setToolBarNew(this, "Managers Compiled", true);


        }

        if (page.equalsIgnoreCase("all")) {
            MUtil.setToolBarNew(this, "Indivisual", true);


        }

        if (page.equalsIgnoreCase("staff_edit")) {
            MUtil.setToolBarNew(this, "Staff Edit", true);


        }

        if (page.equalsIgnoreCase("vender")) {
            MUtil.setToolBarNew(this, "Vender Edit", true);


        }*/


    }


    public void onSearch(String query) {

        ArrayList<USER> contactsBeenLocal = new ArrayList<>();

        contactsBeenLocal.addAll(userList);
        ArrayList<USER> filteredModelList = new ArrayList<>();
        if (contactsBeenLocal != null) {
            if (query == null || query.length() == 0) {
                filteredModelList.addAll(contactsBeenLocal);
            } else {

                query = query.toLowerCase();
                for (USER model : contactsBeenLocal) {
                    String text = "";
                    if (model.getNAME() != null) {
                        text = model.getNAME().toLowerCase();
                    }
                    String handler = "";
                    if (model.getMOBILE() != null) {
                        handler = model.getMOBILE().toLowerCase();
                    }
                    String phone = "";
                    if (model.getEMAIL() != null) {
                        phone = model.getEMAIL().toLowerCase();
                    }

                    if (text.contains(query) || handler.contains(query) || phone.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
            if (adapter != null) {
                adapter.filter(filteredModelList);
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}
