package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.Registerdevice;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    String usertype;
    private AppCompatButton btn_view;
    private AppCompatButton btn_change_Password;
    private AppCompatButton btn_gift,btn_availed_gift;
    private AppCompatButton btn_create;
    private AppCompatButton btn_notification;
    AppCompatButton btn_old_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        findViews();

        user = (USER) getIntent().getExtras().getSerializable("DATA");

//        usertype=getIntent().getStringExtra("Usertype");
        usertype=user.getDEGINATION();
        setData();
        if(usertype.equals("distributor"))
        {
            btn_create.setVisibility(View.VISIBLE);
//            btn_view.setVisibility(View.GONE);
//            btn_change_Password.setVisibility(View.GONE);
//            btn_gift.setVisibility(View.GONE);
//            btn_availed_gift.setVisibility(View.GONE);
//            btnAddCoupon.setVisibility(View.GONE);
//            add.setVisibility(View.GONE);
        }else{
            btn_create.setVisibility(View.GONE);

        }
        if(usertype.equals("Premium Retailer"))
        {
            btn_old_report.setVisibility(View.VISIBLE);

        }
        setNavigationDrawer();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            storetoken(token);
                        }
                    }
                });
        final Context context = this;
        AppCompatButton logout = (AppCompatButton)findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setNavigationDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Welcome "+user.getNAME(), false);
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
                Intent intent = new Intent(UserHome.this,ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        navEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this,ProfileActivity.class);
                intent.putExtra("DATA", user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this,ProfileActivity.class);
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
                Intent intent = new Intent(UserHome.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM,"Staff");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM,"Customer");
                startActivity(intent);

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, Products.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, AboutUs.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, ContactUs.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, Enquiry.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        TextView couponGifts = (TextView) navigationView.findViewById(R.id.nav_couponGifts);
        couponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, FirstPage.class);
                // intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(UserHome.this,HomePageActivity.class);
            startActivity(intent);
            finish();
        } else {
//            super.onBackPressed();
            Intent intent = new Intent(UserHome.this,HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void setData() {
        if (user != null && user.getNAME() != null) {
            tvTotalPoints.setText("Welcome "+user.getNAME());
        }else{
            tvTotalPoints.setText("Welcome User");
        }
    }


    private TextView tvTotalPoints;
    private AppCompatButton btnAddCoupon;
    private EditText etCoupon;
    private AppCompatButton add;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-01 20:34:16 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    private void findViews() {
        btn_old_report=findViewById(R.id.old_report);
        btn_old_report.setOnClickListener(this);
        btn_notification=findViewById(R.id.btn_notification);
        btn_notification.setOnClickListener(this);
        btn_create=findViewById(R.id.btn_create);
        tvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        add = (AppCompatButton) findViewById(R.id.btnLogin);
        btn_view = (AppCompatButton) findViewById(R.id.btn_view);
        btn_change_Password = (AppCompatButton) findViewById(R.id.btn_change_Password);
        btn_create.setOnClickListener(this);
        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_change_Password.setOnClickListener(this);
        btn_gift = (AppCompatButton) findViewById(R.id.btn_gift);
        btn_gift.setOnClickListener(this);
        btn_availed_gift=(AppCompatButton)findViewById(R.id.btn_availedgift);
        btn_availed_gift.setOnClickListener(this);
    }

    public void storetoken(String token)
    {
        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
            ApiService apiinterface = retrofit.create(ApiService.class);
            Call<List<Registerdevice>> call = apiinterface.storetoken(token, user.getMOBILE());
            call.enqueue(new Callback<List<Registerdevice>>() {
                @Override
                public void onResponse(Call<List<Registerdevice>> call, Response<List<Registerdevice>> response) {

//                Toast.makeText(UserHome.this, "Welcome to Shree Shiv Sewak", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<List<Registerdevice>> call, Throwable t) {
                    Toast.makeText(UserHome.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_create){
            Intent intent = new Intent(this,Registration.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }

        if (v == btn_gift) {

            Intent intent = new Intent(this,GIFT.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }

        else if(v==btn_availed_gift){
            Intent intent = new Intent(this,AvailedGiftUser.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }

        else if(v==btn_notification){
            Intent intent = new Intent(this,MessageActivity.class);
//            intent.putExtra("DATA", user);
            startActivity(intent);
        }
        else if (v == btn_change_Password) {
            // Handle clicks for btnAddCoupon
           /* add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,ChangePasswordActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        } else
        if (v == add) {
            // Handle clicks for btnAddCoupon
            /*add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,AddCouponActivity.class);
            intent.putExtra("DATA", user);
            startActivityForResult(intent,000010);

        } else if (v == btn_view) {
           /* // Handle clicks for btnAddCoupon
            add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);;
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,UserPointsActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }
        else if(v==btn_old_report){
            Intent intent = new Intent(this,OldPremiumReport.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }
        /*else if (v == btnAddCoupon) {
            // Handle clicks for add

            if (isValid()) {
                MUtil.showProgressDialog(UserHome.this);
                DatabaseReference databaseReferencenew = AppUtil.getCouponReference(UserHome.this).child(etCoupon.getText().toString().toUpperCase());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(UserHome.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(UserHome.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(UserHome.this, "Coupon availed try with another coupon");
                                    MUtil.dismissProgressDialog();
                                }else{
                                    addPoints(coupon);
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        MUtil.dismissProgressDialog();
                    }
                });

            }



        }*/
    }


    private void addPoints(final COUPON coupon) {



        DatabaseReference databaseReference = AppUtil.getUserReference(UserHome.this);
        String points = user.getPOINTS();
        String pointsAdd = coupon.getPoints();
        String totalPoints = "";
        if(points!=null){
            int pointsInt = Integer.parseInt(points);
            int pointsIntAdd = Integer.parseInt(pointsAdd);
            totalPoints = String.valueOf(pointsInt+pointsIntAdd);
        }else{
            totalPoints = pointsAdd;
        }
        user.setPOINTS(totalPoints);

        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    MUtil.showSnakbar(UserHome.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    updateCoupon(coupon);
                }
            }
        });
    }

    private void updateCoupon(COUPON coupon) {
        coupon.setIs_availed("yes");
        DatabaseReference databaseReference = AppUtil.getCouponReference(UserHome.this);
        databaseReference.child(etCoupon.getText().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(UserHome.this, "Coupon addition failed try again ");
                } else {
                    MUtil.showSnakbar(UserHome.this, "Coupon added successfully.");
                    add.setVisibility(View.GONE);
                    etCoupon.setVisibility(View.GONE);
                    btnAddCoupon.setVisibility(View.VISIBLE);
                    tvTotalPoints.setText(user.getPOINTS());
                }
            }
        });

    }


    private boolean isExpired(String valid_til) {
        return false;
    }


    private boolean isValid() {
        return true;
    }


  /*  @Override
    protected void onResume() {
        super.onResume();
        MUtil.showProgressDialog(UserHome.this);
        DatabaseReference databaseReferencenew = AppUtil.getUserReference(UserHome.this).child(user.getMOBILE());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                USER user = dataSnapshot.getValue(USER.class);
                if(user==null){
                    MUtil.showInfoAlertDialog(UserHome.this,"Data does not referesh try again");
                }else{
                   UserHome.this.user = user;
                }
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 000010 && resultCode == RESULT_OK) {
                USER user = (USER) data.getExtras().getSerializable("DATA");
                if (user != null) {
                    this.user = user;
                }
            }
        }catch (Exception e){

        }
    }
}
