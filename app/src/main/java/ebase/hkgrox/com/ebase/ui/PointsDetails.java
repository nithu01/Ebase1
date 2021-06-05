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
import ebase.hkgrox.com.ebase.GetUserPointApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.Adapter;
import ebase.hkgrox.com.ebase.adapter.AdapterExecutives;
import ebase.hkgrox.com.ebase.adapter.AdapterExecutivesVenderEdit;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PointsDetails extends AppCompatActivity {

    private RecyclerView rv_list;
    private SearchView sv_search;
    private AdapterExecutives adapter;
    private  AdapterExecutivesVenderEdit adapter1;
    private List<USER> userList;
    Config config;
    String login_url2 = config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String page = getIntent().getExtras().getString("PAGE");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        sv_search = (SearchView) findViewById(R.id.sv_search);

        if (page.equalsIgnoreCase("staff_edit") || page.equalsIgnoreCase("vender")) {
            sv_search.setVisibility(View.VISIBLE);
        } else {
            sv_search.setVisibility(View.VISIBLE);
        }

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        MUtil.showProgressDialog(this);

        //List<USER> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        GetUserPointApi getUserPointApi=retrofit.create(GetUserPointApi.class);
        Call<List<USER>> call=getUserPointApi.getdetails();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list = response.body();
                List<USER> arraylist=new ArrayList<>();
               // List<USER> day_sales = new ArrayList<USER>();

                String type = "";
                USER user = null;
                for (int i = 0; i < list.size(); i++) {
                   /* if (i == 0) {
                        continue;
                    }*/
                    user = new USER();

                    String name = list.get(i).getNAME();
                    String mobile = list.get(i).getMOBILE();
                    String gift = list.get(i).getGP();
                    String points = list.get(i).getTP();
                    String degination=list.get(i).getDEGINATION();
                    String enable=list.get(i).getENABLE();
                    user.setDEGINATION(degination);

                  //  if (page.equalsIgnoreCase("points")) {
                        type = "user";
                      //  if (degination.equalsIgnoreCase("USER")) {
                           // day_sales.add(use);
if(points==null){
    int tpoints=0;
    int gpoints=0;
    int rpoints=0;

    user.setMOBILE(mobile);
    user.setNAME(name);
                        /**/
    user.setGP(String.valueOf(gpoints));
    user.setRP(String.valueOf(rpoints));
    user.setTP(String.valueOf(tpoints));

    user.setENABLE(enable);
    arraylist.add(user);
}
else {
    if (gift == null) {
        int gif = 0;
        String gi = String.valueOf(gif);
        int to = Integer.parseInt(points);
        // int gi=Integer.parseInt(gif);
        int fi = to - gif;
        String fina = String.valueOf(fi);

        user.setMOBILE(mobile);
        user.setNAME(name);

                    /* */
        user.setGP(gi);
        // use.setTP(fina);
                        /* */
        user.setRP(fina);
        user.setTP(points);
        user.setENABLE(enable);

        arraylist.add(user);
    } else {
        int to = Integer.parseInt(points);
        int gi = Integer.parseInt(gift);
        int fi = to - gi;
        String fina = String.valueOf(fi);

        user.setMOBILE(mobile);
        user.setNAME(name);
                        /**/
        user.setGP(gift);
        user.setRP(fina);
        user.setTP(points);

        user.setENABLE(enable);
        arraylist.add(user);
    }
}
                  //  }
/*

                    if (page.equalsIgnoreCase("vender")) {
                        //if (use.getDEGINATION().equalsIgnoreCase("USER")) {
                            if (gift == null) {
                                int gif = 0;
                                String gi = String.valueOf(gif);
                                int to = Integer.parseInt(points);
                                // int gi=Integer.parseInt(gif);
                                int fi = to - gif;
                                String fina = String.valueOf(fi);

                                use.setMOBILE(mobile);
                                use.setNAME(name);

                    /*
                                use.setGP(gi);
                                // use.setTP(fina);
                        /*
                                use.setRP(fina);
                                use.setTP(points);

                                arraylist.add(use);
                            } else {
                                int to = Integer.parseInt(points);
                                int gi = Integer.parseInt(gift);
                                int fi = to - gi;
                                String fina = String.valueOf(fi);

                                use.setMOBILE(mobile);
                                use.setNAME(name);
                        /*
                                use.setGP(gift);
                                use.setRP(fina);
                                use.setTP(points);
                                arraylist.add(use);
                            }
                     //   }

                    }
                    */
                    if (page.equalsIgnoreCase("vender"))
                    {
                        userList = arraylist;
                       adapter1 = new AdapterExecutivesVenderEdit(PointsDetails.this, arraylist);
                        rv_list.setAdapter(adapter1);
                    } else
                    if (page.equalsIgnoreCase("points"))

                    {
                        userList = arraylist;
                        adapter = new AdapterExecutives(PointsDetails.this, arraylist);
                        rv_list.setAdapter(adapter);
                    } else

                    {
                        rv_list.setAdapter(new Adapter(PointsDetails.this, arraylist, type));
                    }
                }

                MUtil.dismissProgressDialog();

/*
            adapter =new

            AdapterExecutives(PointsDetails.this, arraylist);
                rv_list.setAdapter(adapter);
                    MUtil.dismissProgressDialog();

*/
        }



            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                MUtil.dismissProgressDialog();
            }
        });

        /*
        DatabaseReference databaseReferencenew = AppUtil.getUserReference(this);
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = "";
                List<USER> day_sales = new ArrayList<USER>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    USER user = postSnapshot.getValue(USER.class);

                    if (page.equalsIgnoreCase("points")) {
                        type = "user";
                        if (user.getDEGINATION().equalsIgnoreCase("USER")) {
                            day_sales.add(user);
                        }

                    }


                    if (page.equalsIgnoreCase("executives")) {
                        type = "executive";
                        if (user.getDEGINATION().toLowerCase().contains("executive")) {
                            day_sales.add(user);
                        }

                    }

                    if (page.equalsIgnoreCase("managers")) {
                        type = "manager";
                        if (user.getDEGINATION().toLowerCase().contains("manager")) {
                            day_sales.add(user);
                        }

                    }

                    if (page.equalsIgnoreCase("all")) {
                        type = "all";
                        if (!user.getDEGINATION().toLowerCase().contains("admin")) {
                            day_sales.add(user);
                        }

                    }

                    if (page.equalsIgnoreCase("staff_edit")) {

                        if (user.getDEGINATION().toLowerCase().contains("executive")) {
                            day_sales.add(user);
                        }
                        if (user.getDEGINATION().toLowerCase().contains("manager")) {
                            day_sales.add(user);
                        }

                    }

                    if (page.equalsIgnoreCase("vender")) {
                        if (user.getDEGINATION().equalsIgnoreCase("USER")) {
                            day_sales.add(user);
                        }

                    }


                }
                if (page.equalsIgnoreCase("staff_edit")) {
                    userList = day_sales;
                    adapter = new AdapterExecutives(PointsDetails.this, day_sales);
                    rv_list.setAdapter(adapter);
                } else if (page.equalsIgnoreCase("vender")) {
                    userList = day_sales;
                    adapter = new AdapterExecutives(PointsDetails.this, day_sales);
                    rv_list.setAdapter(adapter);
                } else {
                    rv_list.setAdapter(new Adapter(PointsDetails.this, day_sales, type));
                }


                MUtil.dismissProgressDialog();
            }
/*
            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
       });*/


        if (page.equalsIgnoreCase("points")) {
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
            MUtil.setToolBarNew(this, "Vendor Edit", true);


        }


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
            if (adapter1 != null) {
                adapter1.filter(filteredModelList);
            }
            if (adapter != null) {
                adapter.filter(filteredModelList);
            }
        }
    }
}
