package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import java.util.List;
import ebase.hkgrox.com.ebase.Api.Loginservice;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //private String login_url2 = "http://192.168.0.101";
    Config config;
    String login_url2 =config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
       /*
        setSupportActionBar(toolbar);
*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Products",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        try {
            from = getIntent().getExtras().getString(AppUtil.FROM);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (from != null && from.equalsIgnoreCase("customer")) {
            MUtil.setToolBarNew(this, "Customer Login", false);
        } else if (from != null && from.equalsIgnoreCase("staff")) {
            MUtil.setToolBarNew(this, "Staff Login", false);
        } else {
            MUtil.setToolBarNew(this, "Login", false);
        }


        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forget.class);
                startActivity(intent);
            }
        });

    }


    private String from = "";
    private EditText etMobileNo;
    private EditText etPassword;
    private AppCompatButton btnLogin;
    private TextView tvCantAccessAccount;
    private TextView tvNewUser;
    private TextView tv_forget;


    /**
     * Find the Views in the layout<br />
     * <br />
     */
    private void findViews() {
        etMobileNo = (EditText) findViewById(R.id.et_mobile_no);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        tvCantAccessAccount = (TextView) findViewById(R.id.tv_cant_access_account);
        tvNewUser = (TextView) findViewById(R.id.tvNewUser);

        btnLogin.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     */

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            // Handle clicks for btnLogin
            if (isValid())
                MUtil.showProgressDialog(LoginActivity.this);
            Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
            Loginservice loginservice=retrofit.create(Loginservice.class);
            Call<List<USER>> call=loginservice.login(etMobileNo.getText().toString());
            call.enqueue(new Callback<List<USER>>(){

                @Override
                public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {

                    //     Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                    List<USER> list=response.body();
                    USER user=null;
                    for(int i=0;i<list.size();i++)
                    {
                        user=new USER();
                        String name=list.get(i).getNAME();
                        String desgnation=list.get(i).getDEGINATION();
                        String mobile=list.get(i).getMOBILE();
                        String email=list.get(i).getEMAIL();
                        String location=list.get(i).getLOCATION();
                        String address=list.get(i).getADDRESS();

                        String city=list.get(i).getCITY();
                        String enable=list.get(i).getENABLE();
                        String point=list.get(i).getPOINTS();
                        String pincode=list.get(i).getPINCODE();
                        String password=list.get(i).getPASSWORD();
                        String state=list.get(i).getSTATE();

                        String current_sstg=list.get(i).getCURRENT_SSTG();
                        String date=list.get(i).getDATE();
                        String monthaly_collection=list.get(i).getMONTHALY_COLLECTION();
                        String monthaly_sale=list.get(i).getMONTHALY_SALE();
                        String sstg=list.get(i).getSSTG();
                        String today_sstg=list.get(i).getTODAY_SSTG();
                        String current_mct=list.get(i).getCURRENT_MCT();

                        String current_mst=list.get(i).getCURRENT_MST();
                        String today_mct=list.get(i).getTODAY_MCT();
                        String today_mst=list.get(i).getTODAY_MST();

                        user.setNAME(name);
                        user.setDEGINATION(desgnation);
                        user.setMOBILE(mobile);
                        user.setEMAIL(email);
                        user.setLOCATION(location);
                        user.setADDRESS(address);

                        user.setCITY(city);
                        user.setENABLE(enable);
                        user.setPOINTS(point);
                        user.setPINCODE(pincode);
                        user.setPASSWORD(password);
                        user.setSTATE(state);

                        user.setCURRENT_SSTG(current_sstg);
                        user.setDATE(date);
                        user.setMONTHALY_COLLECTION(monthaly_collection);
                        user.setMONTHALY_SALE(monthaly_sale);
                        user.setSSTG(sstg);
                        user.setTODAY_SSTG(today_sstg);

                        user.setCURRENT_MCT(current_mct);
                        user.setCURRENT_MST(current_mst);
                        user.setTODAY_MCT(today_mct);
                        user.setTODAY_MST(today_mst);

                    }
                    if (user.getNAME() == null) {
                        MUtil.showInfoAlertDialog(LoginActivity.this, "User does not exist with this number try another no or register");
                    } else {
                        if (user.getPASSWORD() != null && etPassword.getText().toString().equalsIgnoreCase(user.getPASSWORD())) {
                            if (user.getENABLE() == null || user.getENABLE().equalsIgnoreCase("true")) {

                                           /* if (from != null && from.equalsIgnoreCase("customer") && !user.getDEGINATION().equalsIgnoreCase("USER")) {
                                                MUtil.showInfoAlertDialog(HomePageActivity.this, "Invalid Username and Password try again");
                                                //  }else{*/
                                startHomeActivity(user);
                                // }
                                //}
                            }else {
                                MUtil.showInfoAlertDialog(LoginActivity.this, "You are not allowed to access this application. Please contact with administater");

                            }
                        } else {
                            MUtil.showInfoAlertDialog(LoginActivity.this, "Password does not match try again");
                        }
                    }
                        MUtil.dismissProgressDialog();
                    }


                @Override
                public void onFailure(Call<List<USER>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"User doesn't exist"+t,Toast.LENGTH_SHORT).show();
                    MUtil.dismissProgressDialog();
                }
            });



            /*
            DatabaseReference databaseReferencenew = AppUtil.getUserReference(LoginActivity.this).child(etMobileNo.getText().toString());
            databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    USER user = dataSnapshot.getValue(USER.class);
                    if (user == null) {
                        MUtil.showInfoAlertDialog(LoginActivity.this, "User does not exist with this number try another no or register");
                    } else {
                        if (user.getPASSWORD() != null && etPassword.getText().toString().equalsIgnoreCase(user.getPASSWORD())) {
                            if (user.getENABLE() == null || user.getENABLE().equalsIgnoreCase("true")) {

                                if (from != null && from.equalsIgnoreCase("customer") && !user.getDEGINATION().equalsIgnoreCase("USER")) {
                                    MUtil.showInfoAlertDialog(LoginActivity.this, "Invalid Username and Password try again");
                                } else {
                                    startHomeActivity(user);
                                }
                            } else {
                                MUtil.showInfoAlertDialog(LoginActivity.this, "You are not allowed to access this application. Please contact with administater");
                            }
                        } else {
                            MUtil.showInfoAlertDialog(LoginActivity.this, "Password does not match try again");
                        }
                    }
                    MUtil.dismissProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    MUtil.dismissProgressDialog();
                }
            });*/
        }

        if (v == tvNewUser) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void startHomeActivity(USER user) {
        Intent intent = null;
        if (user.getDEGINATION().equalsIgnoreCase("USER")) {
            intent = new Intent(this, UserHome.class);
        }
        if (user.getDEGINATION().toLowerCase().contains("executive")) {
            intent = new Intent(this, ExecutiveHome.class);
        }
        if (user.getDEGINATION().toLowerCase().contains("manager")) {
            intent = new Intent(this, ManagerHome.class);
        }

        if (user.getDEGINATION().toLowerCase().contains("admin")) {
            intent = new Intent(this, AdminHome.class);
        }
        if (intent != null) {
            intent.putExtra("DATA", user);
            startActivity(intent);
            finish();
        } else {
            MUtil.showInfoAlertDialog(LoginActivity.this, "Something went wrong try again");
        }

    }

    private boolean isValid() {
        return true;
    }


}
