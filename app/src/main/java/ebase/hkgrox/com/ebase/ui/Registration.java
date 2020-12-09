package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration extends AppCompatActivity {
    EditText Name,Mobile,Email,Address,City,State,Pincode,Pass,Cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Name = findViewById(R.id.name);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.email);
        Address = findViewById(R.id.address);
        City = findViewById(R.id.city);
        State = findViewById(R.id.state);
        Pincode = findViewById(R.id.pincode);
        Pass = findViewById(R.id.password);
        Cpass =findViewById(R.id.confirmPassword);

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
        }else if (Pass.getText().toString().equals(Cpass.getText().toString())){
            Pass.setError("Password not match");
            Cpass.setError("Password not match");
        }else {
//            Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
//            Addvender addvender=retrofit.create(Addvender.class);
//            Call<List<USER>> call=addvender.register();
//            call.enqueue(new Callback<List<USER>>() {
//                @Override
//                public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
//
//                }
//                @Override
//                public void onFailure(Call<List<USER>> call, Throwable t) {
//
//                }
//            });
//
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,UserHome.class);
        startActivity(intent);
        finish();
    }
}
