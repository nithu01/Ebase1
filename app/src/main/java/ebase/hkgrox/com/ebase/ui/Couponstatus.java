package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class Couponstatus extends AppCompatActivity {

    String login= Config.ip_url;
    EditText editText;
    Button submit;
    TextView point,coupons,isavaile,availed_date,availed_byname,availed_bymobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couponstatus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        point=(TextView)findViewById(R.id.points);
        coupons=(TextView)findViewById(R.id.coupon);
        isavaile=(TextView)findViewById(R.id.is_availed);
        availed_date=(TextView)findViewById(R.id.date);
        availed_byname=(TextView)findViewById(R.id.availed_by_name);
        availed_bymobile=(TextView)findViewById(R.id.availed_by_mobile);
        MUtil.setToolBarNew(this,"Coupon Status",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editText=(EditText)findViewById(R.id.couponid);
        submit=(Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
                ApiCoupon apiCoupon=retrofit.create(ApiCoupon.class);
                Call<List<COUPON>> call=apiCoupon.ggetstatus(editText.getText().toString());
                call.enqueue(new Callback<List<COUPON>>() {
                    @Override
                    public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                    //    Toast.makeText(Couponstatus.this, "Success", Toast.LENGTH_SHORT).show();
                        List<COUPON> list=response.body();
                        for(int i=0;i<list.size();i++)
                        {
                            String date=list.get(i).getDate_availed();
                            String isavailed=list.get(i).getIs_availed();
                            String points=list.get(i).getPoints();
                            String coupon=list.get(i).getCoupon();
                            String name=list.get(i).getAvailed_by_name();
                            String mobile=list.get(i).getAvailed_by_mobile();

                          //  Toast.makeText(Couponstatus.this,date+isavailed+points+coupon, Toast.LENGTH_SHORT).show();

                            point.setText(points);
                            availed_date.setText(date);
                            isavaile.setText(isavailed);
                            availed_byname.setText(name);
                            availed_bymobile.setText(mobile);
                            coupons.setText(coupon);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<COUPON>> call, Throwable t) {
                        Toast.makeText(Couponstatus.this, "Coupon doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }

    }
