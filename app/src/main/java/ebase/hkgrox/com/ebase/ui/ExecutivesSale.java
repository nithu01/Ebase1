package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.History;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.DAY_SALE;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExecutivesSale extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private List<DAY_SALE> day_sales;
    private TextView tvName;
    Config config;

    String login_url2 =config.ip_url;
   //private String login_url2 = "http://192.168.0.101";
    private String sale,todaysale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executives_sale);
        findViews();
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        setData();
        getHistory(user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"View Sale",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      try{  if(getIntent().getExtras().getString("SALE").equalsIgnoreCase("false")){
            btnAddCoupon.setVisibility(View.GONE);
        }}catch (Exception e){

      }


        String from  = getIntent().getExtras().getString("FROM");
        if(from!=null && from.equalsIgnoreCase("individual")){
            btnAddCoupon.setVisibility(View.GONE);
        }
    }





    private void getHistoryDay(USER user) {

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historytoday(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth(),MUtil.getDayOfMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
               // Toast.makeText(ExecutivesSale.this, "Success"+list, Toast.LENGTH_SHORT).show();
                DAY_SALE daySale=null;
                String a;
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for(int i=0;i<list.size();i++) {
                   // daySale = new DAY_SALE();
                    sale = list.get(i).getSALE();
                    //daySale.setSALE(a);
                }
                   // day_sales.add(daySale);
                todaysale=sale;
               if(todaysale!=null){
                    try {

                        tvSaleToday.setText(todaysale + "  liters");
                       // tvSaleToday.setText(day_sales.getSALE() + "  liters");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {
                Toast.makeText(ExecutivesSale.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
      //  MUtil.showProgressDialog(ExecutivesSale.this);
     /*   DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DAY_SALE day_sales = dataSnapshot.getValue(DAY_SALE.class);
                if(day_sales!=null){
                    try {
                        tvSaleToday.setText(day_sales.getSALE() + "  liters");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

    }



    private void getHistory(final USER user) {
       // MUtil.showProgressDialog(ExecutivesSale.this);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historymonth(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
                    DAY_SALE daySale=null;
                    List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                    for(int i=0;i<list.size();i++) {
                        daySale = new DAY_SALE();
                        String a = list.get(i).getSALE();
                        daySale.setSALE(a);
                        day_sales.add(daySale);
                    }
                    getHistoryDay(user);
                    setValue(day_sales);


            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });
/*
        DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    day_sales.add(postSnapshot.getValue(DAY_SALE.class));
                }

                if (day_sales.size() > 0) {
                    setValue(day_sales);
                }
                getHistoryDay(user);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

    }

    private void setValue(List<DAY_SALE> day_sales) {
        this.day_sales =day_sales;

        int totalSale = 0;
        for (DAY_SALE day_sale : day_sales) {
            String sale = day_sale.getSALE();
            int saleInt = 0;
            if (sale != null && sale.length() > 0) {
                saleInt = Integer.valueOf(sale);
                totalSale = totalSale + saleInt;
            }
        }
        tvSaleMonth.setText(String.valueOf(totalSale)+"  liters");
    }

    private void setData() {
        if (user != null && user.getSSTG() != null) {
            tvMonthTargets.setText(user.getSSTG()+"  liters");
        } else {
            tvMonthTargets.setText("0");
        }

        tvName.setText("Welcome "+user.getNAME());
        try{  if(getIntent().getExtras().getString("SALE").equalsIgnoreCase("false")){
            btnAddCoupon.setVisibility(View.GONE);
            tvName.setText(""+user.getNAME());
        }}catch (Exception e){

        }
    }


    private TextView tvMonthTargets;
    private TextView tvSaleToday;
    private TextView tvSaleMonth;
    private AppCompatButton btnAddCoupon;
    private EditText etCoupon;
    private AppCompatButton add;

    /**
     * Find the Views in the layout<br />
     */
    private void findViews() {
        tvMonthTargets = (TextView) findViewById(R.id.tv_month_targets);
        tvSaleToday = (TextView) findViewById(R.id.tv_sale_today);
        tvSaleMonth = (TextView) findViewById(R.id.tv_sale_month);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        add = (AppCompatButton) findViewById(R.id.btnLogin);
        tvName = (TextView)findViewById(R.id.tv_name);
        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tv_name);
    }

    /**
     * Handle button click events<br />
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddCoupon) {
          /*  add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,ExecutivesSaleAdd.class);
            intent.putExtra("DATA",user);
            startActivity(intent);
            finish();
        } else if (v == add) {
           // addTarget(user);


        }


    }

    private void addTarget(final USER user) {
        MUtil.showProgressDialog(ExecutivesSale.this);
        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DAY_SALE day_sale = dataSnapshot.getValue(DAY_SALE.class);
                addTarget(user, day_sale);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });

    }

    private void addTarget(USER user, DAY_SALE day_sale) {

        if (day_sale == null || day_sale.getSALE().length() < 1) {
            day_sale = new DAY_SALE();
            day_sale.setSALE(etCoupon.getText().toString());
        } else {
            int totalSale = Integer.parseInt(etCoupon.getText().toString());
            String sale = day_sale.getSALE();
            int saleInt = 0;
            try {
                saleInt = Integer.valueOf(sale);
            } catch (Exception e) {

            }
            totalSale = totalSale + saleInt;
            day_sale.setSALE(String.valueOf(totalSale)+"  liters");
        }

        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase())
        ;
        final DAY_SALE finalDay_sale = day_sale;
        databaseReferencenew.child(MUtil.getDayOfMonth()).setValue(day_sale, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(ExecutivesSale.this, "Target addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    if(day_sales!=null && day_sales.size()>0){
                        day_sales.remove(day_sales.size()-1);
                        day_sales.add(finalDay_sale);
                        setValue(day_sales);

                    }

                    add.setVisibility(View.GONE);
                    etCoupon.setVisibility(View.GONE);
                    btnAddCoupon.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
