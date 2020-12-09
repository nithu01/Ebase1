package ebase.hkgrox.com.ebase.ui;



import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.util.MUtil;

public abstract class AddCoupon extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatePickerDialog datepickerdialog;
    SimpleDateFormat simpledateformat;
    EditText coupon,points;
    EditText getCoupon;
    Button add,dates;
    String login= Config.ip_url;
    TextView textview;
    Date current;
    String endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getCoupon=(EditText)findViewById(R.id.coupon);
        //  textview=(TextView)findViewById(R.id.textView);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MUtil.setToolBarNew(this, "Add Coupon", true);
        //current = new Date();
        //System.out.println(current);
        //Calendar cal = Calendar.getInstance();
        //cal.setTime(current);
       // int year = cal.get(Calendar.YEAR) + 1;
        //int month = cal.get(Calendar.MONTH) + 1;
        //int day = cal.get(Calendar.DAY_OF_MONTH);
        // cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+12));
        //endDate= new StringBuilder().append(year)
        //       .append("-").append(month).append("-").append(day).toString().trim();
        //Toast.makeText(this,endDate, Toast.LENGTH_SHORT).show();
        // System.out.println(current);
        // Toast.makeText(this, ""+cal, Toast.LENGTH_SHORT).show();
        //simpledateformat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        // dates=(Button)findViewById(R.id.date);

      //  points = (EditText) findViewById(R.id.points);
       // add = (Button) findViewById(R.id.button);

      /* add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit=new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
                ApiCoupon apiCoupon=retrofit.create(ApiCoupon.class);
                //Toast.makeText(AddCoupon.this,coupon.getText().toString()+points.getText().toString(), Toast.LENGTH_SHORT).show();
                Call<List<COUPON>> call=apiCoupon.addcoupon(points.getText().toString(),"no",endDate,MUtil.getTodayDate());
                call.enqueue(new Callback<List<COUPON>>() {
                    @Override
                    public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                       // Toast.makeText(AddCoupon.this, "Success", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddCoupon.this);
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
                        Toast.makeText(AddCoupon.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }*/
    }

}
