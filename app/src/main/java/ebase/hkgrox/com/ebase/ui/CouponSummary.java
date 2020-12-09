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

import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.CouponApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.DAY_SALE;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AvailedcouponApi;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CouponSummary extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private List<DAY_SALE> day_sales;
    private TextView tvName;
    Config config;

    String login_url2 =config.ip_url;
    // private String login_url2 = "http://192.168.0.103";
    String totalcp;
    String availed;
    private TextView tvMonthTargets;
    private TextView tvSaleToday;
    private TextView tvSaleMonth;
    private AppCompatButton btnAddCoupon;
    private EditText etCoupon;
    private AppCompatButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_summary);
        findViews();
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        //setData();
      //  getHistory(user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Coupon Summary",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
MUtil.showProgressDialog(this);
       totalcoupon();


      //  Toast.makeText(getApplicationContext(), this.totalcp,Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getApplicationContext(), this.availed,Toast.LENGTH_SHORT).show();
       // int total=Integer.parseInt(totalcp);
        //int avail= Integer.parseInt(availed);
        //int left=total-avail;
        //tvSaleMonth.setText(left);


     //   couponleft(totalcp,availed);

    }

    public void totalcoupon()
    {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        CouponApi couponApi=retrofit.create(CouponApi.class);
        Call<List<COUPON>> call=couponApi.listRespo();
        call.enqueue(new Callback<List<COUPON>>() {
            @Override
            public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                //Toast.makeText(getApplicationContext(),"qwerty"+response,Toast.LENGTH_SHORT).show();
                List<COUPON> arraylist=response.body();

                for(int i=0;i<arraylist.size();i++) {
                    totalcp = arraylist.get(i).getTotalcoupon();

                }
                tvMonthTargets.setText(totalcp);
                //couponleft(totalcp, availed);
                availedcoupon();
            }

            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void availedcoupon()
    {
        Retrofit retrofit1=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        AvailedcouponApi availedcouponApi=retrofit1.create(AvailedcouponApi.class);
        Call<List<COUPON>> call1=availedcouponApi.getcoupon();
        call1.enqueue(new Callback<List<COUPON>>() {
            @Override
            public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                List<COUPON> arraylist=response.body();
                for(int i=0;i<arraylist.size();i++)
                {
                    availed=arraylist.get(i).getAvailedcoupon();
                }
                tvSaleToday.setText(availed);
              //  MUtil.dismissProgressDialog();
                couponleft();

            }

            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {

            }

        });
    }

    public void couponleft()
    {
        //Toast.makeText(getApplicationContext(), this.totalcp,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), this.availed,Toast.LENGTH_SHORT).show();
       int total=Integer.parseInt(this.totalcp);
       int avail= Integer.parseInt(this.availed);
        int left=total-avail;
        String ac= String.valueOf(left);
        tvSaleMonth.setText(ac);
        MUtil.dismissProgressDialog();
    }

/*
    private void getHistory(USER user) {
        MUtil.showProgressDialog(CouponSummary.this);
        DatabaseReference databaseReferencenew = AppUtil.getCouponReference(CouponSummary.this);
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<COUPON> day_sales = new ArrayList<COUPON>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    day_sales.add(postSnapshot.getValue(COUPON.class));
                }
                setData(day_sales);
                MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });


    }

    private void setData(List<COUPON> day_sales) {

        if(day_sales!=null){
            tvMonthTargets.setText(""+day_sales.size());
            int i=0;
            for (COUPON coupon: day_sales) {
                if(coupon.getIs_availed()!=null && coupon.getIs_availed().equalsIgnoreCase("yes")){
                    i++;
                }
            }

            tvSaleToday.setText(""+i);
            tvSaleMonth.setText(""+(day_sales.size()-i));


        }
    }


    private void setValue(List<DAY_SALE> day_sales) {
        this.day_sales =day_sales;
        tvSaleToday.setText(day_sales.get(day_sales.size() - 1).getSALE()+"  liters");
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
    }

*/


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
          //  addTarget(user);


        }


    }
/*
    private void addTarget(final USER user) {
        MUtil.showProgressDialog(CouponSummary.this);
        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(CouponSummary.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
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

        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(CouponSummary.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase())
        ;
        final DAY_SALE finalDay_sale = day_sale;
        databaseReferencenew.child(MUtil.getDayOfMonth()).setValue(day_sale, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(CouponSummary.this, "Target addition failed try again ");
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
*/

}
