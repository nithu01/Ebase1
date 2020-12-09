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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    Config config;

    String login_url2 =config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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



    private EditText etOldPassword;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private AppCompatButton btnSubmit;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-05 21:45:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etOldPassword = (EditText)findViewById( R.id.etOldPassword );
        etPassword = (EditText)findViewById( R.id.etPassword );
        etConfirmPassword = (EditText)findViewById( R.id.etConfirmPassword );
        btnSubmit = (AppCompatButton)findViewById( R.id.btnSubmit );

        btnSubmit.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-02-05 21:45:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            if (isValid()) {
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    MUtil.showProgressDialog(ChangePasswordActivity.this);
                    if (user.getPASSWORD().equals(etOldPassword.getText().toString())) {


                        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                        Addvender addvender = retrofit.create(Addvender.class);
                        Call<List<USER>> call = addvender.updatepass(user.getMOBILE(), etPassword.getText().toString());
                        call.enqueue(new Callback<List<USER>>() {
                            @Override
                            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                               // Toast.makeText(getApplicationContext(), "response" + response, Toast.LENGTH_SHORT).show();
                                MUtil.dismissProgressDialog();
                                MUtil.showSnakbar(ChangePasswordActivity.this, "Password changed successfully.");
                                finish();
                            }

                            @Override
                            public void onFailure(Call<List<USER>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        MUtil.dismissProgressDialog();
                        MUtil.showSnakbar(ChangePasswordActivity.this, "Old Password Doesn't Match ");

                    }
                }else {
                    MUtil.showSnakbar(ChangePasswordActivity.this, "Password Doesn't Match ");
                }

            }
               /*     DatabaseReference databaseReference = AppUtil.getUserReference(ChangePasswordActivity.this);
                    user.setPASSWORD(etPassword.getText().toString());
                  

                    databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            MUtil.dismissProgressDialog();
                            if (databaseError != null) {
                                MUtil.showSnakbar(ChangePasswordActivity.this, "Password could not change try again ");
                            } else {
                                MUtil.showSnakbar(ChangePasswordActivity.this, "Password changed successfully.");
                                finish();
                            }
                        }
                    });
*/


        }

    }

    private boolean isValid() {

        return true;
    }


}
