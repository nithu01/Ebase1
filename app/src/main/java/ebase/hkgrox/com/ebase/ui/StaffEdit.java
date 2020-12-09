package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.AdapterExecutives;
import ebase.hkgrox.com.ebase.adapter.MyAdapter;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffEdit extends AppCompatActivity {

    private RecyclerView rv_list;
    private SearchView sv_search;
    private AdapterExecutives adapter;
    private List<USER> userList;
    private AppCompatButton btn_get_details;
    private Spinner et_name;
    private ArrayList<USER> day_sales;
    Config config;

    String login_url2 = config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        
            MUtil.setToolBarNew(this, "Staff Edit", true);

        btn_get_details = (AppCompatButton) findViewById(R.id.btn_get_details);
        et_name = (Spinner) findViewById(R.id.et_name);

        btn_get_details.setText("Edit");


        MUtil.showProgressDialog(this);
        day_sales = new ArrayList<USER>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender=retrofit.create(Addvender.class);
        Call<List<USER>> call=addvender.retrieve();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list=response.body();
                USER user=null;
                USER user1 = new USER();
                user1.setNAME(" Select Staff");
                day_sales.add(user1);
                for(int i=0;i<list.size();i++){
                    user=new USER();
                    String name=list.get(i).getNAME();
                    String desgnation=list.get(i).getDEGINATION();
                    String mobile=list.get(i).getMOBILE();
                    String email=list.get(i).getEMAIL();
                    String location=list.get(i).getLOCATION();
                    String address=list.get(i).getADDRESS();

                    String city=list.get(i).getCITY();
                    String enable=list.get(i).getENABLE();
                    String point=list.get(i).getPOINTS();
                    String pincode=list.get(i).getPINCODE();
                    String password=list.get(i).getPASSWORD();
                    String state=list.get(i).getSTATE();

                    String current_sstg=list.get(i).getCURRENT_SSTG();
                    String date=list.get(i).getDATE();
                    String monthaly_collection=list.get(i).getMONTHALY_COLLECTION();
                    String monthaly_sale=list.get(i).getMONTHALY_SALE();
                    String sstg=list.get(i).getSSTG();
                    String today_sstg=list.get(i).getTODAY_SSTG();
                    String current_mct=list.get(i).getCURRENT_MCT();

                    String current_mst=list.get(i).getCURRENT_MST();
                    String today_mct=list.get(i).getTODAY_MCT();
                    String today_mst=list.get(i).getTODAY_MST();

                    user.setNAME(name);
                    user.setDEGINATION(desgnation);
                    user.setMOBILE(mobile);
                    user.setEMAIL(email);
                    user.setLOCATION(location);
                    user.setADDRESS(address);

                    user.setCITY(city);
                    user.setENABLE(enable);
                    user.setPOINTS(point);
                    user.setPINCODE(pincode);
                    user.setPASSWORD(password);
                    user.setSTATE(state);

                    user.setCURRENT_SSTG(current_sstg);
                    user.setDATE(date);
                    user.setMONTHALY_COLLECTION(monthaly_collection);
                    user.setMONTHALY_SALE(monthaly_sale);
                    user.setSSTG(sstg);
                    user.setTODAY_SSTG(today_sstg);

                    user.setCURRENT_MCT(current_mct);
                    user.setCURRENT_MST(current_mst);
                    user.setTODAY_MCT(today_mct);
                    user.setTODAY_MST(today_mst);

                    day_sales.add(user);


                }
                et_name.setAdapter(new MyAdapter(StaffEdit.this,day_sales));
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
                 day_sales = new ArrayList<USER>();
                USER user1 = new USER();
                user1.setNAME(" Select Staff");
                day_sales.add(user1);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    USER user = postSnapshot.getValue(USER.class);
                    if (!user.getDEGINATION().equalsIgnoreCase("admin")  && !user.getDEGINATION().equalsIgnoreCase("user")) {
                        day_sales.add(user);
                    }

                }

                et_name.setAdapter(new MyAdapter(StaffEdit.this,day_sales));
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/
        btn_get_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getSelectedItemPosition()==0){
                    MUtil.showInfoAlertDialog(StaffEdit.this,"Please Select user first.");
                }else{
                    startHomeActivity(day_sales.get(et_name.getSelectedItemPosition()));
                }
            }
        });



    }



    private void startHomeActivity(USER user) {
        Intent intent = null;
        if (user.getDEGINATION().equalsIgnoreCase("USER")) {
            intent = new Intent(this, UserPointsActivity.class);
        }
        if (user.getDEGINATION().toLowerCase().contains("executive")) {
            intent = new Intent(this, StaffForm.class);
        }
        if(user.getDEGINATION().toLowerCase().contains("manager")){
            intent = new Intent(this, StaffForm.class);
        }

        if(intent!=null) {
            intent.putExtra("DATA", user);
            intent.putExtra("FROM", "staffedit");
            startActivity(intent);
          //  finish();
        }else{
            MUtil.showInfoAlertDialog(StaffEdit.this,"Something went wrong try again");
        }

    }


}
