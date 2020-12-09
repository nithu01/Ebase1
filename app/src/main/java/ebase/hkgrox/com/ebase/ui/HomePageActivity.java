package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
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

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_forget;
    Config config;

    String login_url2 =config.ip_url;
   // private String login_url2 = "http://192.168.0.101";

    List<USER> arraylist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
       // setNavigationDrawer();
        findViews();
    }

    private AppBarLayout appbar;
    private Toolbar toolbar;
    private TextView btnILove;
    private TextView btnCouponGifts;
    private EditText etMobileNo;
    private EditText etPassword;
    private TextView btnLogin;

    private TextView tvCantAccessAccount;
    private TextView tvNewUser;
    private NavigationView navView;

    /**
     * Find the Views in the layout<br />
     * <br />
     */
    private void findViews() {
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnILove = (TextView) findViewById(R.id.btn_i_love);
        btnCouponGifts = (TextView) findViewById(R.id.btn_coupon_gifts);
        etMobileNo = (EditText) findViewById(R.id.et_mobile_no);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        tvCantAccessAccount = (TextView) findViewById(R.id.tv_cant_access_account);
        tvNewUser = (TextView) findViewById(R.id.tvNewUser);

        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Forget.class);
                startActivity(intent);
            }
        });

        navView = (NavigationView) findViewById(R.id.nav_view);

        btnILove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCouponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePageActivity.this,Coupon)
                Intent intent = new Intent(HomePageActivity.this, CouponGifts.class);
                // intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);
    }


    private String from = "";


    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            // Handle clicks for btnLogin
            if (isValid()) {
                MUtil.showProgressDialog(HomePageActivity.this);
                Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                Loginservice loginservice=retrofit.create(Loginservice.class);
                Call<List<USER>> call=loginservice.login(etMobileNo.getText().toString());
                call.enqueue(new Callback<List<USER>>(){

                    @Override
                    public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {

                   //     Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                            List<USER> list=response.body();
                            USER user=null;
                            for(int i=0;i<list.size();i++) {
                                user = new USER();
                                String name = list.get(i).getNAME();
                                String desgnation = list.get(i).getDEGINATION();
                                String mobile = list.get(i).getMOBILE();
                                String email = list.get(i).getEMAIL();
                                String location = list.get(i).getLOCATION();
                                String address = list.get(i).getADDRESS();

                                String city = list.get(i).getCITY();
                                String enable = list.get(i).getENABLE();
                                String point = list.get(i).getPOINTS();
                                String pincode = list.get(i).getPINCODE();
                                String password = list.get(i).getPASSWORD();
                                String state = list.get(i).getSTATE();

                                String current_sstg = list.get(i).getCURRENT_SSTG();
                                String date = list.get(i).getDATE();
                                String monthaly_collection = list.get(i).getMONTHALY_COLLECTION();
                                String monthaly_sale = list.get(i).getMONTHALY_SALE();
                                String sstg = list.get(i).getSSTG();
                                String today_sstg = list.get(i).getTODAY_SSTG();
                                String current_mct = list.get(i).getCURRENT_MCT();

                                String current_mst = list.get(i).getCURRENT_MST();
                                String today_mct = list.get(i).getTODAY_MCT();
                                String today_mst = list.get(i).getTODAY_MST();

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
                                    MUtil.showInfoAlertDialog(HomePageActivity.this, "User does not exist with this number try another no or register");
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
                                                MUtil.showInfoAlertDialog(HomePageActivity.this, "You are not allowed to access this application. Please contact with administater");

                                        }
                            } else {
                                MUtil.showInfoAlertDialog(HomePageActivity.this, "Password does not match try again");
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
                DatabaseReference databaseReferencenew = AppUtil.getUserReference(HomePageActivity.this).child(etMobileNo.getText().toString());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        USER user = dataSnapshot.getValue(USER.class);
                        if (user == null) {
                            MUtil.showInfoAlertDialog(HomePageActivity.this, "User does not exist with this number try another no or register");
                        } else {
                            if (user.getPASSWORD() != null && etPassword.getText().toString().equalsIgnoreCase(user.getPASSWORD())) {
                                if (user.getENABLE() == null || user.getENABLE().equalsIgnoreCase("true")) {

                              /*  if(from!=null && from.equalsIgnoreCase("customer") && !user.getDEGINATION().equalsIgnoreCase("USER")){
                                    MUtil.showInfoAlertDialog(HomePageActivity.this,"Invalid Username and Password try again");
                              //  }else{
                                    startHomeActivity(user);
                                    // }
                                } else {
                                    MUtil.showInfoAlertDialog(HomePageActivity.this, "You are not allowed to access this application. Please contact with administater");
                                }
                            } else {
                                MUtil.showInfoAlertDialog(HomePageActivity.this, "Password does not match try again");
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
        }

        if (v == tvNewUser) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // finish();
        }
    }

    private void startHomeActivity(USER user) {
        Intent intent = null;
        if(user.getDEGINATION().equalsIgnoreCase("Distributor")){
            intent = new Intent(this, UserHome.class);
            intent.putExtra("Usertype","distributor");
//            startActivity();
        }
        if (user.getDEGINATION().equalsIgnoreCase("USER")) {
            intent = new Intent(this, UserHome.class);
            intent.putExtra("Usertype","user");
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
            MUtil.showInfoAlertDialog(HomePageActivity.this, "Something went wrong try again");
        }

    }

    private boolean isValid() {
        return true;
    }


    private void setNavigationDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Home", false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ImageView navImageView = (ImageView) navigationView.findViewById(R.id.nav_imageView);
        TextView navName = (TextView) navigationView.findViewById(R.id.nav_name);
        TextView navEmail = (TextView) navigationView.findViewById(R.id.nav_email);

        TextView navHome = (TextView) navigationView.findViewById(R.id.nav_home);
        TextView navAdd = (TextView) navigationView.findViewById(R.id.nav_add);
        TextView navView = (TextView) navigationView.findViewById(R.id.nav_view_sale);
        TextView navChangePassword = (TextView) navigationView.findViewById(R.id.nav_change_password);
        TextView navAbout = (TextView) navigationView.findViewById(R.id.nav_about);
        TextView navContactUs = (TextView) navigationView.findViewById(R.id.nav_contact_us);
        TextView nav_enquiry = (TextView) navigationView.findViewById(R.id.nav_enquiry);

        /*navName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        navEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,ProfileActivity.class);
                intent.putExtra("DATA", user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,ProfileActivity.class);
                intent.putExtra("DATA", user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });*/


        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM, "Staff");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM, "Customer");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Products.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AboutUs.class);
                //intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ContactUs.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Enquiry.class);
                // intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        TextView couponGifts = (TextView) navigationView.findViewById(R.id.nav_couponGifts);
        couponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CouponGifts.class);
                // intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

    }

}
