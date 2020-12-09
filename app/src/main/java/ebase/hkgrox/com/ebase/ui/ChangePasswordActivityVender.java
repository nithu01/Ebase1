package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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

public class ChangePasswordActivityVender extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    Config config;

    String login_url2 =config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_vender);
        findViews();
          user = (USER)getIntent().getExtras().getSerializable("DATA");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Change Password",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    private EditText etPassword;
    private EditText etConfirmPassword;
    private AppCompatButton btnSubmit;

    /**
     * Find the Views in the layout<br />
     * <br />
     */
    private void findViews() {
        etPassword = (EditText)findViewById( R.id.etPassword );
        etConfirmPassword = (EditText)findViewById( R.id.etConfirmPassword );
        btnSubmit = (AppCompatButton)findViewById( R.id.btnSubmit );

        btnSubmit.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if ( v == btnSubmit ) {
            MUtil.showProgressDialog(this);
            if (isValid()) {
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                    Addvender addvender = retrofit.create(Addvender.class);
                    Call<List<USER>> call = addvender.updatepass(user.getMOBILE(), etPassword.getText().toString());
                    call.enqueue(new Callback<List<USER>>() {
                        @Override
                        public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                            // Toast.makeText(getApplicationContext(), "response" + response, Toast.LENGTH_SHORT).show();
                            MUtil.dismissProgressDialog();
                            MUtil.showSnakbar(ChangePasswordActivityVender.this, "Password changed successfully.");
                            finish();
                        }

                        @Override
                        public void onFailure(Call<List<USER>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                MUtil.dismissProgressDialog();
                MUtil.showSnakbar(ChangePasswordActivityVender.this, "Password Doesn't Match ");


                /*
                MUtil.showProgressDialog(ChangePasswordActivityVender.this);
                    DatabaseReference databaseReference = AppUtil.getUserReference(ChangePasswordActivityVender.this);
                    user.setPASSWORD(etPassword.getText().toString());
                  

                    databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            MUtil.dismissProgressDialog();
                            if (databaseError != null) {
                                MUtil.showSnakbar(ChangePasswordActivityVender.this, "Password could not change try again ");
                            } else {
                                MUtil.showSnakbar(ChangePasswordActivityVender.this, "Password changed successfully.");
                                finish();
                            }
                        }
                    });

*/
                
            }
        }
    }

    private boolean isValid() {
        return true;
    }


}
