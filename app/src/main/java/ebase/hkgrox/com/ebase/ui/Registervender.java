package ebase.hkgrox.com.ebase.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import ebase.hkgrox.com.ebase.Api.Apiorder;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registervender extends AppCompatActivity {
    EditText party,segment,pincode,city,area,address,potential,phone,email,state,cperson,phone2;
    Spinner retailer;
    Button submit;
    USER user;

    Config config;
    String url=config.ip_url;
    String[] retwor={"Retailer","Distributor","Mechanic","Stockist"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registervender);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Add Vendor",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cperson=(EditText)findViewById(R.id.contactperson);
        phone2=(EditText)findViewById(R.id.phn);
        state=(EditText)findViewById(R.id.state);
        user=(USER) getIntent().getExtras().getSerializable("DATA");
        email=(EditText)findViewById(R.id.email);
        submit=(Button)findViewById(R.id.submit);
        retailer=(Spinner)findViewById(R.id.retailer);
        party=(EditText)findViewById(R.id.party);
        segment=(EditText)findViewById(R.id.segment);
        pincode=(EditText)findViewById(R.id.pincode);
        city=(EditText)findViewById(R.id.city);
        area=(EditText)findViewById(R.id.area);
        address=(EditText)findViewById(R.id.address);
        potential=(EditText)findViewById(R.id.potential);
        phone=(EditText)findViewById(R.id.phone);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,retwor);
        aa.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Setting the ArrayAdapter data on the Spinner
        retailer.setAdapter(aa);
       // retailer.setAdapter(new ArrayAdapter<String>(Registervender.this,android.R.layout.simple_spinner_item,retwor));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                Apiorder apiorder=retrofit.create(Apiorder.class);
                Call<List<Vender>> call=apiorder.register(user.getNAME(),user.getMOBILE(),state.getText().toString(),party.getText().toString(),cperson.getText().toString(),phone2.getText().toString(),phone.getText().toString(),email.getText().toString(),retailer.getSelectedItem().toString(),segment.getText().toString(),pincode.getText().toString(),city.getText().toString(),area.getText().toString(),address.getText().toString(),potential.getText().toString(),MUtil.getTodayDate());
                Toast.makeText(Registervender.this,cperson.getText().toString()+phone2.getText().toString(), Toast.LENGTH_SHORT).show();
                call.enqueue(new Callback<List<Vender>>() {
                    @Override
                    public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {

                        List<Vender> list = response.body();
                        Vender vender = null;
                        for (int i = 0; i < list.size(); i++) {
                            vender = new Vender();
                            String state = list.get(i).toString();
                            String party = list.get(i).getParty();
                            String retailer = list.get(i).getRetailer();
                            String segment = list.get(i).getSegment();
                            String pincode = list.get(i).getPincode();
                            String city = list.get(i).getCity();
                            String area = list.get(i).getArea();
                            String address = list.get(i).getAddress();
                            String potential = list.get(i).getPotentail();
                            String phone = list.get(i).getPhone();
                            String email = list.get(i).getEmail();

                            vender.setState(state);
                            vender.setEmail(email);
                            vender.setParty(party);
                            vender.setRetailer(retailer);
                            vender.setSegment(segment);
                            vender.setPincode(pincode);
                            vender.setCity(city);
                            vender.setArea(area);
                            vender.setAddress(address);
                            vender.setPotentail(potential);
                            vender.setPhone(phone);

                            if (vender.getParty() == null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Registervender.this);
                                builder1.setMessage("Vender already exist");
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


                        }

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Registervender.this);
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
                    public void onFailure(Call<List<Vender>> call, Throwable t) {
                        Toast.makeText(Registervender.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public  void startorderActivity(Vender vender)
    {
        Intent intent = new Intent(this,DailyVisitReport.class);
        intent.putExtra("DATA",user);
        intent.putExtra("Venders", vender);
        startActivity(intent);
        finish();
    }
}
