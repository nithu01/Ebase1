package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration extends AppCompatActivity {
    private USER user;
    EditText Name,Mobile,Email,Address,City,State,Pincode,Pass,Cpass;
    Config config;
    String login_url2 =config.ip_url;
    Button btnSubmit;
    String datee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        datee= formatter.format(date);

        btnSubmit=findViewById(R.id.btnSubmit);
        Name = findViewById(R.id.name);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.email);
        Address = findViewById(R.id.address);
        City = findViewById(R.id.city);
        State = findViewById(R.id.et_state);
        Pincode = findViewById(R.id.et_pincode);
        Pass = findViewById(R.id.password);
        Cpass =findViewById(R.id.confirmPassword);
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty()){
                    Name.setError("Enter Name");
                }else if (Mobile.getText().toString().isEmpty()) {
                    Mobile.setError("Enter Mobile Number");
                }else if (Mobile.getText().toString().length()<10){
                    Mobile.setError("Enter vaild Mobile Number ");
                }else if (Email.getText().toString().isEmpty()){
                    Email.setError("Enter Email Id");
                }else if (Address.getText().toString().isEmpty()){
                    Address.setError("Enter Address");
                }else if (City.getText().toString().isEmpty()){
                    City.setError("Enter City");
                }else if (State.getText().toString().isEmpty()){
                    State.setError("Enter State");
                }else if (Pincode.getText().toString().isEmpty()){
                    Pincode.setError("Enter Pincode");
                }else if (Pincode.getText().toString().length()<6){
                    Pincode.setError("Enter vaild Pincode ");
                }else if (Pass.getText().toString().isEmpty()){
                    Pass.setError("Enter Password");
                }else if (Cpass.getText().toString().isEmpty()){
                    Cpass.setError("Enter Confirm Password");
                }else if (!Pass.getText().toString().equals(Cpass.getText().toString())){
                    Pass.setError("Password not match");
                    Cpass.setError("Password not match");
                }else {
                    MUtil.showProgressDialog(Registration.this);
                    Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                    Addvender addvender=retrofit.create(Addvender.class);
                    Call<List<USER>> call=addvender.inser(datee,Name.getText().toString(),City.getText().toString(),Mobile.getText().toString(),Address.getText().toString(),Email.getText().toString(),Pass.getText().toString(),Pincode.getText().toString(),State.getText().toString(),"Premium Retailer",user.getNAME());
                    call.enqueue(new Callback<List<USER>>() {
                        @Override
                        public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                            List<USER> list= response.body();
                            // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();

                            //String code=list.get("Code");
                            for(int i=0;i<list.size();i++)
                            {

                                if(list.get(i).getNAME()==null) {
                                    Toast.makeText(getApplicationContext(),"Already exist",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startHomeActivity(user,"distributor");
                                    //arraylist.add(user);

                                }
                            }
                            MUtil.dismissProgressDialog();
                        }
                        @Override
                        public void onFailure(Call<List<USER>> call, Throwable t) {

                        }
                    });
//
                }
            }
        });

    }
    private void startHomeActivity(USER user,String usertype) {
        Intent intent = new Intent(this,UserHome.class);
        intent.putExtra("Usertype","distributor");
        intent.putExtra("DATA", user);
        startActivity(intent);
//        finish();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,UserHome.class);
        intent.putExtra("DATA", user);
        startActivity(intent);
        finish();
    }
}
