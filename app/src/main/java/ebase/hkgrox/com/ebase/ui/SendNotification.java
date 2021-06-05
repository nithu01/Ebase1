package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendNotification extends AppCompatActivity {

    String login= Config.ip_url;
    EditText message;
    Button send;
    String strDate;
    private USER user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        send=findViewById(R.id.button);
        message=findViewById(R.id.message);
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        strDate= formatter.format(date);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MUtil.setToolBarNew(this, "Send Notification", true);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storenotification();
                sendnotification();
            }
        });
    }
    public void storenotification(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<ebase.hkgrox.com.ebase.bean.Notification> call = apiService.addmessage(strDate, message.getText().toString());
        call.enqueue(new Callback<ebase.hkgrox.com.ebase.bean.Notification>() {
            @Override
            public void onResponse(Call<ebase.hkgrox.com.ebase.bean.Notification> call, Response<ebase.hkgrox.com.ebase.bean.Notification> response) {
                MUtil.dismissProgressDialog();
//                Toast.makeText(SendNotification.this,"Message send Succesfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SendNotification.this,AdminHome.class).putExtra("DATA",user));
            }

            @Override
            public void onFailure(Call<ebase.hkgrox.com.ebase.bean.Notification> call, Throwable t) {
                MUtil.dismissProgressDialog();
//                Toast.makeText(SendNotification.this,"Message send Succesfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SendNotification.this,AdminHome.class).putExtra("DATA",user));
            }

        });
    }
    public void sendnotification(){
        MUtil.showProgressDialog(this);
        Retrofit retrofit= new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<ebase.hkgrox.com.ebase.bean.Notification>> call=apiService.sendnotification(message.getText().toString());
        call.enqueue(new Callback<List<ebase.hkgrox.com.ebase.bean.Notification>>() {
            @Override
            public void onResponse(Call<List<ebase.hkgrox.com.ebase.bean.Notification>> call, Response<List<ebase.hkgrox.com.ebase.bean.Notification>> response) {
                MUtil.dismissProgressDialog();
                Toast.makeText(SendNotification.this,"Message send Succesfully",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<ebase.hkgrox.com.ebase.bean.Notification>> call, Throwable t) {
                MUtil.dismissProgressDialog();
                Toast.makeText(SendNotification.this,"Message send Succesfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}