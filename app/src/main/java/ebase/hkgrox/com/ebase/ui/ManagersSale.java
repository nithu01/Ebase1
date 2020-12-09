package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class ManagersSale extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private List<DAY_SALE> day_sales;
    private TextView tv_name;
    Config config;

    String login_url2 = config.ip_url;
   // private String login_url2 = "http://192.168.0.101";
    private String sale,todaysale,collection,todaycollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_sale);
        findViews();
        user = (USER)getIntent().getExtras().getSerializable("DATA");
        setData(user);
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

    private void getHistory(final USER user) {
        MUtil.showProgressDialog(ManagersSale.this);


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
                    String b=list.get(i).getCOLLECTION();
                    daySale.setSALE(a);
                    daySale.setCOLLECTION(b);
                    day_sales.add(daySale);
                }
                getHistoryDay(user);
                setValue(day_sales);


            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });
        /*DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase());
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
               // MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

    }



    private void getHistoryDay(USER user) {
        //  MUtil.showProgressDialog(ExecutivesSale.this);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historytoday(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth(),MUtil.getDayOfMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
                DAY_SALE daySale=null;
                String a;
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for(int i=0;i<list.size();i++) {
                    // daySale = new DAY_SALE();
                    sale = list.get(i).getSALE();
                    collection=list.get(i).getCOLLECTION();

                    //daySale.setSALE(a);
                }
                // day_sales.add(daySale);
                todaycollection=collection;
                todaysale=sale;
                if(todaysale!=null){
                  try {

                      tvSaleToday.setText(todaysale + "  liters");
                      tvCollectionToday.setText(todaycollection+" rupees");
                      // tvSaleToday.setText(day_sales.getSALE() + "  liters");
                      //tvCollectionToday.setText(day_sales.getCOLLECTION()+" rupees");
                      }catch (Exception e) {
                          e.printStackTrace();
                      }

                }
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });



        /*
        DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DAY_SALE day_sales = dataSnapshot.getValue(DAY_SALE.class);
                if(day_sales!=null){
                    try {
                        tvSaleToday.setText(day_sales.getSALE() + "  liters");
                        tvCollectionToday.setText(day_sales.getCOLLECTION()+" rupees");
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



    private void setValue(List<DAY_SALE> day_sales) {
        this.day_sales =day_sales;

        double totalSale = 0;
        for (DAY_SALE day_sale : day_sales) {
            String sale = day_sale.getSALE();
            double saleInt = 0;
            if (sale != null && sale.length() > 0) {
                saleInt = Double.parseDouble(sale);
                totalSale = totalSale + saleInt;
            }
        }
        if(user.getMONTHALY_SALE()!=null && user.getMONTHALY_SALE().length()>0){
            double targetSale = Double.parseDouble(user.getMONTHALY_SALE());
            tvSaleBalance.setText(String.valueOf(Math.round(targetSale-totalSale))+" liters");
        }
        tvSaleMonth.setText(String.valueOf(Math.round(totalSale))+" liters");



        double totalCollection = 0;
        for (DAY_SALE day_sale : day_sales) {
            String collection = day_sale.getCOLLECTION();
            double collectionInt = 0;
            if (collection != null && collection.length() > 0) {
                collectionInt = Double.parseDouble(collection);
                totalCollection = totalCollection + collectionInt;
            }
        }

        if(user.getMONTHALY_COLLECTION()!=null && user.getMONTHALY_COLLECTION().length()>0){
            double targetCollection = Double.parseDouble(user.getMONTHALY_COLLECTION());
            tvCollectionBalance.setText(String.valueOf(Math.round(targetCollection-totalCollection))+" rupees");
        }


        tvCollectionMonth.setText(String.valueOf(Math.round(totalCollection))+" rupees");
    }


    private void setData(USER user) {
        if(user!=null){
            if(user.getMONTHALY_COLLECTION()!=null){
                tvMonthTargetsCollection.setText(user.getMONTHALY_COLLECTION() +" rupees");
            }
            if(user.getMONTHALY_SALE()!=null){
                tvMonthTargetsSale.setText(user.getMONTHALY_SALE()+" liters");
            }
        }

        tv_name.setText("Welcome "+user.getNAME());
        try{  if(getIntent().getExtras().getString("SALE").equalsIgnoreCase("false")){
            btnAddCoupon.setVisibility(View.GONE);
            tv_name.setText(""+user.getNAME());
        }}catch (Exception e){

        }
    }


    private TextView tvMonthTargetsSale;
    private TextView tvMonthTargetsCollection;
    private TextView tvSaleToday;
    private TextView tvSaleMonth;
    private TextView tvSaleBalance;
    private TextView tvCollectionToday;
    private TextView tvCollectionMonth;
    private TextView tvCollectionBalance;
    private AppCompatButton btnAddCoupon;
    private EditText etSale;
    private EditText etCollection;
    private AppCompatButton add;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-01 23:49:40 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvMonthTargetsSale = (TextView) findViewById(R.id.tv_month_targets_sale);
        tvMonthTargetsCollection = (TextView) findViewById(R.id.tv_month_targets_collection);
        tvSaleToday = (TextView) findViewById(R.id.tv_sale_today);
        tvSaleMonth = (TextView) findViewById(R.id.tv_sale_month);
        tvSaleBalance = (TextView) findViewById(R.id.tv_sale_balance);
        tvCollectionToday = (TextView) findViewById(R.id.tv_collection_today);
        tvCollectionMonth = (TextView) findViewById(R.id.tv_collection_month);
        tvCollectionBalance = (TextView) findViewById(R.id.tv_collection_balance);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etSale = (EditText) findViewById(R.id.et_sale);
        etCollection = (EditText) findViewById(R.id.et_collection);
        add = (AppCompatButton) findViewById(R.id.btnLogin);

        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
    }

    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddCoupon) {
            Intent intent = new Intent(this,ManagersAddSale.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
            finish();

           /* add.setVisibility(View.VISIBLE);
            etSale.setVisibility(View.VISIBLE);
            etSale.setText("");
            etCollection.setVisibility(View.VISIBLE);
            etCollection.setText("");
            btnAddCoupon.setVisibility(View.GONE);
            ((NestedScrollView)findViewById(R.id.nsv_main)).fullScroll(View.FOCUS_DOWN);*/
            // Handle clicks for btnAddCoupon
        } else if (v == add) {
            // Handle clicks for add
            if(isValid()){
                //addTarget(user);
            }
        }
    }

    private boolean isValid() {
        return true;
    }


    private void addTarget(final USER user) {
        MUtil.showProgressDialog(ManagersSale.this);
        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
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
            day_sale.setSALE(etSale.getText().toString());
        } else {
            int totalSale = Integer.parseInt(etSale.getText().toString());
            String sale = day_sale.getSALE();
            int saleInt = 0;
            try {
                saleInt = Integer.valueOf(sale);
            } catch (Exception e) {

            }
            totalSale = totalSale + saleInt;
            day_sale.setSALE(String.valueOf(totalSale));
        }


        if (day_sale.getCOLLECTION() == null || day_sale.getCOLLECTION().length() < 1) {
            day_sale.setCOLLECTION(etCollection.getText().toString());
        } else {
            int totalCollection = Integer.parseInt(etCollection.getText().toString());
            String collection = day_sale.getSALE();
            int collectionInt = 0;
            try {
                collectionInt = Integer.valueOf(collection);
            } catch (Exception e) {

            }
            totalCollection = totalCollection + collectionInt;
            day_sale.setCOLLECTION(String.valueOf(totalCollection));
        }






        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase())
                ;
        final DAY_SALE finalDay_sale = day_sale;
        databaseReferencenew.child(MUtil.getDayOfMonth()).setValue(day_sale, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(ManagersSale.this, "Target addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    if(day_sales!=null && day_sales.size()>0){
                        day_sales.remove(day_sales.size()-1);
                        day_sales.add(finalDay_sale);
                        setValue(day_sales);

                    }

                    add.setVisibility(View.GONE);
                    etSale.setVisibility(View.GONE);
                    etCollection.setVisibility(View.GONE);
                    btnAddCoupon.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
