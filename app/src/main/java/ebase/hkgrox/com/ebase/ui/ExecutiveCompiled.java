package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExecutiveCompiled extends AppCompatActivity {

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
        setContentView(R.layout.activity_executive_compiled);
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
        Call<List<USER>> call=addvender.executivecompiled(MUtil.getCurrentMonth(),MUtil.getCurrentYear());
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list = response.body();
                USER user = null;
                for (int i = 0; i < list.size(); i++) {
                    user = new USER();
                    String name = list.get(i).getNAME();
                    String sstg = list.get(i).getSSTG();
                    String csstg = list.get(i).getCURRENT_SSTG();
                    String tsstg = list.get(i).getTODAY_SSTG();
                    String date=list.get(i).getDATE();

                    user.setNAME(name);
                    user.setSSTG(sstg);
                    user.setCURRENT_SSTG(csstg);
                    user.setTODAY_SSTG(tsstg);
                    user.setDATE(date);
                    userList.add(user);
                }
                Double totalToday = 0.0;
                Double totalMonth = 0.0;
                for (USER user1 : userList) {
                    String today;
                    String total = user1.getCURRENT_SSTG();
                    if (user1.getDATE() != null && user1.getDATE().equalsIgnoreCase("" + MUtil.getCurrentYear() + "-" + MUtil.getCurrentMonth() + "-" + MUtil.getDayOfMonth())) {
                        today = user1.getTODAY_SSTG();
                    } else {
                        today = "";
                    }

                    if (today != null && !today.trim().isEmpty()) {
                        totalToday = totalToday + Double.parseDouble(today);
                    }
                    if (total != null && !total.trim().isEmpty()) {
                        totalMonth = totalMonth + Double.parseDouble(total);
                    }
                }
                    USER userL = new USER();
                    userL.setNAME("Total");
                    userL.setTODAY_SSTG(String.valueOf(totalToday));
                    userL.setCURRENT_SSTG(String.valueOf(totalMonth));
                    userL.setSSTG("");
                    userL.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());
                    userList.add(userL);



/*
                Double totalToday = 0.0;
                Double totalMonth = 0.0;
                for (USER user1 : userList) {
                    String today;
                    String total = user1.getCURRENT_SSTG();
                    if (user1.getDATE() != null && user1.getDATE().equalsIgnoreCase("" + MUtil.getCurrentYear() + "-" + MUtil.getCurrentMonth() + "-" + MUtil.getDayOfMonth())) {
                        today = user1.getTODAY_SSTG();
                    } else {
                        today = "";
                    }

                    if (today != null && !today.trim().isEmpty()) {
                        totalToday = totalToday + Double.parseDouble(today);
                    }
                    if (total != null && !total.trim().isEmpty()) {
                        totalMonth = totalMonth + Double.parseDouble(total);
                    }
                }

                USER userL = new USER();
                userL.setNAME("Total");
                userL.setTODAY_SSTG(String.valueOf(totalToday));
                userL.setCURRENT_SSTG(String.valueOf(totalMonth));
                userL.setSSTG("");
                userL.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());
                day_sales.add(userL);
              //  rv_list.setAdapter(new Compiled(ExecutiveCompiled.this, day_sales));
            }
*/
                rv_list.setAdapter(new Compiled(ExecutiveCompiled.this, userList));
                MUtil.dismissProgressDialog();
            }
            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                MUtil.dismissProgressDialog();
                Toast.makeText(ExecutiveCompiled.this,""+t,Toast.LENGTH_SHORT).show();
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
                if (page.equalsIgnoreCase("managers")) {


                    userList = day_sales;
                    rv_list.setAdapter(new CompiledManager(ExecutiveCompiled.this, day_sales));
                } else if (page.equalsIgnoreCase("vender")) {
                    userList = day_sales;
                    adapter = new Compiled(ExecutiveCompiled.this, day_sales);
                    rv_list.setAdapter(adapter);
                } else {
                    Double totalToday = 0.0;
                    Double totalMonth = 0.0;
                    for (USER user : day_sales) {
                        String today;
                        String total = user.getCURRENT_SSTG();
                        if (user.getDATE() != null && user.getDATE().equalsIgnoreCase("" + MUtil.getCurrentYear() + "-" + MUtil.getCurrentMonth() + "-" + MUtil.getDayOfMonth())) {
                            today = user.getTODAY_SSTG();
                        } else {
                            today = "";
                        }

                        if (today != null && !today.trim().isEmpty()) {
                            totalToday = totalToday + Double.parseDouble(today);
                        }
                        if (total != null && !total.trim().isEmpty()) {
                            totalMonth = totalMonth + Double.parseDouble(total);
                        }
                    }

                    USER userL = new USER();
                    userL.setNAME("Total");
                    userL.setTODAY_SSTG(String.valueOf(totalToday));
                    userL.setCURRENT_SSTG(String.valueOf(totalMonth));
                    userL.setSSTG("");
                    userL.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());
                    day_sales.add(userL);
                    rv_list.setAdapter(new Compiled(ExecutiveCompiled.this, day_sales));
                }


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
}
