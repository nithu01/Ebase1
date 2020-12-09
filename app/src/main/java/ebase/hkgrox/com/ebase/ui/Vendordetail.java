package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ebase.hkgrox.com.ebase.Api.Apireport;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Vendordetail extends AppCompatActivity {
    String login= Config.ip_url;
Vender vender;
    TextView State,venders,vender_phone,Segment,category,Checkin,Checkout,Checkin_date,Checkout_date,Checkin_time,Checkout_time,c_person,Mobile2,emai,pm,e_order,Order_detail,addres,remark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendordetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Buyer Details", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        vender = (Vender) getIntent().getExtras().getSerializable("DATA");
       // Toast.makeText(this, ""+vender.getTime(), Toast.LENGTH_SHORT).show();
        State=(TextView)findViewById(R.id.state);
        venders=(TextView)findViewById(R.id.party);
        category=(TextView)findViewById(R.id.retwrk);
        Checkin=(TextView)findViewById(R.id.checkin);
        Checkout=(TextView)findViewById(R.id.checkout);
        Checkin_date=(TextView)findViewById(R.id.checkindate);
        Checkout_date=(TextView)findViewById(R.id.checkoutdate);
        Checkin_time=(TextView)findViewById(R.id.checkintime);
        Checkout_time=(TextView)findViewById(R.id.time);
        Segment=(TextView)findViewById(R.id.segment);
        c_person=(TextView)findViewById(R.id.contactperson);
        vender_phone=(TextView)findViewById(R.id.phone);
        Mobile2=(TextView)findViewById(R.id.Mobile2);
        emai=(TextView)findViewById(R.id.email);
        pm=(TextView)findViewById(R.id.pot_pm);


        e_order=(TextView)findViewById(R.id.e_order);
        Order_detail=(TextView)findViewById(R.id.orderdetails);
        addres=(TextView)findViewById(R.id.address);
        remark=(TextView)findViewById(R.id.remarks);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apireport=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apireport.getvender(vender.getParty(),vender.getVender_phone(),vender.getDate(),vender.getTime());
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                List<Vender> list = response.body();


                Vender vender = null;
                for (int i = 0; i < list.size(); i++) {

                    vender = new Vender();
                    String party = list.get(i).getParty();
                    String retailer = list.get(i).getRetailer();
                    String segment = list.get(i).getSegment();
                    String cperson = list.get(i).getContact_person();
                    String phone = list.get(i).getPhone();
                    String potential = list.get(i).getPotentail();
                    String order = list.get(i).getE_order();
                    String address = list.get(i).getAddress();
                    String remarks = list.get(i).getRemark();
                    String mobile2 = list.get(i).getMobile2();
                    String orderdetail = list.get(i).getOrderdetail();
                    String checkin = list.get(i).getCheckin();
                    String checkout = list.get(i).getCheckout();
                    String email = list.get(i).getEmail();
                    String time = list.get(i).getTime();
                    String checkin_time = list.get(i).getCheckintime();
                    String state=list.get(i).getState();
                    String checkin_date = list.get(i).getCheckin_date();
                    String checkout_date = list.get(i).getDate();

                     State.setText(state);
                     venders.setText(party);
                     category.setText(retailer);
                     Checkin.setText(checkin);
                     Checkout.setText(checkout);
                     Checkin_date.setText(checkin_date);
                     Checkout_date.setText(vender.getDate());
                     Checkin_time.setText(checkin_time);
                     Checkout_time.setText(time);
                     Segment.setText(segment);
                     c_person.setText(cperson);
                     vender_phone.setText(phone);
                     Mobile2.setText(mobile2);
                     Checkout_date.setText(checkout_date);
                     emai.setText(email);
                     pm.setText(potential);
                     e_order.setText(order);
                     Order_detail.setText(orderdetail);
                     addres.setText(address);
                     remark.setText(remarks);
                   /* vender.setE_name(ename);
                    vender.setE_phone(ephone);
                    vender.setCheckin_date(checkin_date);
                    vender.setDate(checkout_date);
                    vender.setCheckintime(checkin_time);
                    vender.setParty(party);
                    vender.setRetailer(retailer);
                    vender.setSegment(segment);
                    vender.setMobile2(mobile2);
                    vender.setOrderdetail(orderdetail);
                    vender.setCheckin(checkin);
                    vender.setCheckout(checkout);
                    vender.setContact_person(cperson);
                    vender.setPincode(pincode);
                    vender.setCity(city);
                    vender.setArea(area);
                    vender.setAddress(address);
                    vender.setPotentail(potential);
                    vender.setE_order(order);
                    vender.setEmail(email);
                    vender.setTime(time);


                    vender.setRemark(remarks);
                    vender.setPhone(phone);
                    arraylist.add(vender);*/

                }
            }
            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {

                Toast.makeText(Vendordetail.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
