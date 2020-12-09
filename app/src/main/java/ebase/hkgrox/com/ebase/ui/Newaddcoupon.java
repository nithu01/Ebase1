package ebase.hkgrox.com.ebase.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.ApiCoupon;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ebase.hkgrox.com.ebase.R.id.points;

public class Newaddcoupon extends AppCompatActivity {

    Button adds;
    String login = Config.ip_url;
    String endDate;
    EditText coupons, point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaddcoupon);
        coupons = (EditText) findViewById(R.id.coupon);
        point = (EditText) findViewById(points);
        adds = (Button) findViewById(R.id.button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
        ApiCoupon apiCoupon = retrofit.create(ApiCoupon.class);
        //Toast.makeText(AddCoupon.this,coupon.getText().toString()+points.getText().toString(), Toast.LENGTH_SHORT).show();
        Call<List<COUPON>> call = apiCoupon.getcoupon();
        call.enqueue(new Callback<List<COUPON>>() {
            @Override
            public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                // Toast.makeText(AddCoupon.this, "Success", Toast.LENGTH_SHORT).show();
                List<COUPON> list=response.body();
                for(int i=0;i<list.size();i++)
                {
                    COUPON coupon=new COUPON();
                    String cou=list.get(i).getCoupon();

                    coupons.setText(cou);
                }

            }

            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {
                Toast.makeText(Newaddcoupon.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Calendar cal = Calendar.getInstance();
        //cal.setTime(current);
          int year = cal.get(Calendar.YEAR) + 1;
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        endDate= new StringBuilder().append(year)
               .append("-").append(month).append("-").append(day).toString().trim();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MUtil.setToolBarNew(this, "Add Coupon", true);
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit=new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
                ApiCoupon apiCoupon=retrofit.create(ApiCoupon.class);
                //Toast.makeText(AddCoupon.this,coupon.getText().toString()+points.getText().toString(), Toast.LENGTH_SHORT).show();
                Call<List<COUPON>> call=apiCoupon.addcoupon(point.getText().toString(),"no",endDate,MUtil.getTodayDate());
                call.enqueue(new Callback<List<COUPON>>() {
                    @Override
                    public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                        // Toast.makeText(AddCoupon.this, "Success", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Newaddcoupon.this);
                        builder1.setMessage("Record inserted");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }

                    @Override
                    public void onFailure(Call<List<COUPON>> call, Throwable t) {
                        Toast.makeText(Newaddcoupon.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
