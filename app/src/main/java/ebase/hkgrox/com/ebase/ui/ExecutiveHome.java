package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;

import static ebase.hkgrox.com.ebase.R.id.btn_order;

public class ExecutiveHome extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private AppCompatButton btn_view;
    private AppCompatButton btn_change_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        findViews();

        user = (USER) getIntent().getExtras().getSerializable("DATA");
        setData();
        setNavigationDrawer();


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
                Intent intent = new Intent(ExecutiveHome.this,ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        navEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this,ProfileActivity.class);
                intent.putExtra("DATA", user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this,ProfileActivity.class);
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
                Intent intent = new Intent(ExecutiveHome.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM,"Staff");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, LoginActivity.class);
                startActivity(intent);
                intent.putExtra(AppUtil.FROM,"Customer");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, Products.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, AboutUs.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, ContactUs.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, Enquiry.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        TextView couponGifts = (TextView) navigationView.findViewById(R.id.nav_couponGifts);
        couponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExecutiveHome.this, FirstPage.class);
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
        } else {
            super.onBackPressed();
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
    Button order;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-01 20:34:16 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        add = (AppCompatButton) findViewById(R.id.btnLogin);
        order=(Button)findViewById(btn_order);
        btn_view = (AppCompatButton) findViewById(R.id.btn_view);
        btn_change_Password = (AppCompatButton) findViewById(R.id.btn_change_Password);
        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_change_Password.setOnClickListener(this);
        order.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if (v == btn_change_Password) {
            // Handle clicks for btnAddCoupon
           /* add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,ChangePasswordActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        } else
            if(v==order)
            {Intent intent = new Intent(this,SelectVender.class);
                intent.putExtra("DATA", user);
                startActivity(intent);

            }else
        if (v == add) {
            // Handle clicks for btnAddCoupon
           /* add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,ExecutivesSaleAdd.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        } else if (v == btn_view) {
           /* // Handle clicks for btnAddCoupon
            add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);*/
            Intent intent = new Intent(this,ExecutivesSale.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        } else if (v == btnAddCoupon) {
            // Handle clicks for add

            if (isValid()) {

                /*MUtil.showProgressDialog(ExecutiveHome.this);
                DatabaseReference databaseReferencenew = AppUtil.getCouponReference(ExecutiveHome.this).child(etCoupon.getText().toString().toUpperCase());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(ExecutiveHome.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(ExecutiveHome.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(ExecutiveHome.this, "Coupon availed try with another coupon");
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
*/
            }



        }
    }

    private void addPoints(final COUPON coupon) {



        DatabaseReference databaseReference = AppUtil.getUserReference(ExecutiveHome.this);
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
                    MUtil.showSnakbar(ExecutiveHome.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    updateCoupon(coupon);
                }
            }
        });
    }

    private void updateCoupon(COUPON coupon) {
        coupon.setIs_availed("yes");
        DatabaseReference databaseReference = AppUtil.getCouponReference(ExecutiveHome.this);
        databaseReference.child(etCoupon.getText().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(ExecutiveHome.this, "Coupon addition failed try again ");
                } else {
                    MUtil.showSnakbar(ExecutiveHome.this, "Coupon added successfully.");
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


}
