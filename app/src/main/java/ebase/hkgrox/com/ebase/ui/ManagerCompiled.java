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

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.Compiled;
import ebase.hkgrox.com.ebase.adapter.CompiledManager;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerCompiled extends AppCompatActivity {

    private RecyclerView rv_list;
    private SearchView sv_search;
    private Compiled adapter;
    private List<USER> userList=new ArrayList<>();
    Config config;

    String login_url2 =config.ip_url;
    //private String login_url2 = "http://192.168.0.101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_compiled);
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


        MUtil.showProgressDialog(this);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender=retrofit.create(Addvender.class);
        Call<List<USER>> call=addvender.managercompiled(MUtil.getCurrentMonth(),MUtil.getCurrentYear());
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list = response.body();
                USER user = null;
                for (int i = 0; i < list.size(); i++) {
                    user = new USER();
                    String name = list.get(i).getNAME();
                    String mst = list.get(i).getMONTHALY_SALE();
                    String mct = list.get(i).getMONTHALY_COLLECTION();
                    String ts = list.get(i).getTODAY_MST();
                    String tc = list.get(i).getTODAY_MCT();
                    String std = list.get(i).getCURRENT_MST();
                    String ctd = list.get(i).getCURRENT_MCT();
                    String date=list.get(i).getDATE();

                    user.setNAME(name);
                    user.setMONTHALY_SALE(mst);
                    user.setMONTHALY_COLLECTION(mct);
                    user.setTODAY_MST(ts);
                    user.setTODAY_MCT(tc);
                    user.setCURRENT_MST(std);
                    user.setCURRENT_MCT(ctd);
                    user.setDATE(date);
                    userList.add(user);
                }
                Double totalTodaySale = 0.0;
                Double totalTodayCollection = 0.0;
                Double totalMonthSale = 0.0;
                Double totalMonthCollection = 0.0;
                for (USER user1 : userList) {
                    String todaysale;
                    String totalSale = user1.getCURRENT_MST();
                    String todayColl;
                    String totalColl = user1.getCURRENT_MCT();
                    if (user1.getDATE() != null && user1.getDATE().equalsIgnoreCase("" + MUtil.getCurrentYear() + "-" + MUtil.getCurrentMonth() + "-" + MUtil.getDayOfMonth())) {
                        todaysale = user1.getTODAY_MST();
                        todayColl = user1.getTODAY_MCT();
                    } else {
                        todaysale = "";
                        todayColl ="";
                    }

                    if (todaysale != null && !todaysale.trim().isEmpty()) {
                        totalTodaySale = totalTodaySale + Double.parseDouble(todaysale);
                    }

                    if (todayColl != null && !todayColl.trim().isEmpty()) {
                        totalTodayCollection = totalTodayCollection + Double.parseDouble(todayColl);
                    }
                    if (totalSale != null && !totalSale.trim().isEmpty()) {
                        totalMonthSale = totalMonthSale + Double.parseDouble(totalSale);
                    }

                    if (totalColl != null && !totalColl.trim().isEmpty()) {
                        totalMonthCollection = totalMonthCollection + Double.parseDouble(totalColl);
                    }
                }

                USER userL = new USER();
                userL.setNAME("Total");
                userL.setTODAY_MST(String.valueOf(totalTodaySale));
                userL.setTODAY_MCT(String.valueOf(totalTodayCollection));

                userL.setCURRENT_MST(String.valueOf(totalMonthSale));
                userL.setCURRENT_MCT(String.valueOf(totalMonthCollection));

                userL.setMONTHALY_SALE("");
                userL.setMONTHALY_COLLECTION("");
                userList.add(userL);
                userL.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());

                rv_list.setAdapter(new CompiledManager(ManagerCompiled.this, userList));

                MUtil.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {

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


                    if (user.getDEGINATION().toLowerCase().contains("manager")||user.getDEGINATION().toLowerCase().equalsIgnoreCase("manager")) {
                        day_sales.add(user);
                    }
                }

                userList = day_sales;


                Double totalTodaySale = 0.0;
                Double totalTodayCollection = 0.0;
                Double totalMonthSale = 0.0;
                Double totalMonthCollection = 0.0;
                for (USER user : day_sales) {
                    String todaysale;
                    String totalSale = user.getCURRENT_MST();
                    String todayColl;
                    String totalColl = user.getCURRENT_MCT();
                    if (user.getDATE() != null && user.getDATE().equalsIgnoreCase("" + MUtil.getCurrentYear() + "-" + MUtil.getCurrentMonth() + "-" + MUtil.getDayOfMonth())) {
                        todaysale = user.getTODAY_MST();
                        todayColl = user.getTODAY_MCT();
                    } else {
                        todaysale = "";
                        todayColl ="";
                    }

                    if (todaysale != null && !todaysale.trim().isEmpty()) {
                        totalTodaySale = totalTodaySale + Double.parseDouble(todaysale);
                    }

                    if (todayColl != null && !todayColl.trim().isEmpty()) {
                        totalTodayCollection = totalTodayCollection + Double.parseDouble(todayColl);
                    }
                    if (totalSale != null && !totalSale.trim().isEmpty()) {
                        totalMonthSale = totalMonthSale + Double.parseDouble(totalSale);
                    }

                    if (totalColl != null && !totalColl.trim().isEmpty()) {
                        totalMonthCollection = totalMonthCollection + Double.parseDouble(totalColl);
                    }
                }

                USER userL = new USER();
                userL.setNAME("Total");
                userL.setTODAY_MST(String.valueOf(totalTodaySale));
                userL.setTODAY_MCT(String.valueOf(totalTodayCollection));

                userL.setCURRENT_MST(String.valueOf(totalMonthSale));
                userL.setCURRENT_MCT(String.valueOf(totalMonthCollection));

                userL.setMONTHALY_SALE("");
                userL.setMONTHALY_COLLECTION("");
                day_sales.add(userL);
                userL.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());

                rv_list.setAdapter(new CompiledManager(ManagerCompiled.this, day_sales));

                MUtil.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });

*/
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
            MUtil.setToolBarNew(this, "Vender Edit", true);


        }


    }



}
