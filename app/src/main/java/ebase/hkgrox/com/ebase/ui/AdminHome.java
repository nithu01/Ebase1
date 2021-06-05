package ebase.hkgrox.com.ebase.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.DownloadCoupon;
import ebase.hkgrox.com.ebase.GetUserPointApi;
import ebase.hkgrox.com.ebase.Premium_Retailer;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.RegisterationActivity;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.Registerdevice;
import ebase.hkgrox.com.ebase.bean.ReportReponse;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private Button btnGifts;
    private Button btn_vender_details,upgrade_user;
    private Button btn_change_password,btn_add_coupon,btn_address,btn_send_notification,btn_premium_retailer,btn_registeration,notification,btn_pr_points;
    Config config;
    String login_url2 =config.ip_url;
    //String login_url2="http://192.168.0.103";
    int month,year;
    String date,date2;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        calendar=Calendar.getInstance();
        findViews();
        savePemiumRetailer();
//      calendar.get(Calendar.DATE)
        if(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)==28){

            savedoublepoints();
        }
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        setNavigationDrawer();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            Log.d("TAG","token"+token);
                            storetoken(token);
                        }
                    }
                });

    }

    public void savedoublepoints(){
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        date=sdf.format(calendar.getTime());
//        date="2021-02-";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<com.google.android.gms.common.api.Response> call=apiService.doublepoints(date);
        call.enqueue(new Callback<com.google.android.gms.common.api.Response>() {
            @Override
            public void onResponse(Call<com.google.android.gms.common.api.Response> call, Response<com.google.android.gms.common.api.Response> response) {
                Toast.makeText(AdminHome.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.google.android.gms.common.api.Response> call, Throwable t) {
                // Toast.makeText(AdminHome.this,""+t,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void savePemiumRetailer(){

        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        date=sdf.format(calendar.getTime());
//        date="2021-02-";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        date2=sdf2.format(calendar.getTime());
//        date2="2021-02-28";
       // Toast.makeText(AdminHome.this,"Success"+date,Toast.LENGTH_SHORT).show();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<ReportReponse>> call=apiService.saveretailer(date,date2);
        call.enqueue(new Callback<List<ReportReponse>>() {
            @Override
            public void onResponse(Call<List<ReportReponse>> call, Response<List<ReportReponse>> response) {
                 //  Toast.makeText(AdminHome.this,"Success",Toast.LENGTH_SHORT).show();
                totalpoints();
            }

            @Override
            public void onFailure(Call<List<ReportReponse>> call, Throwable t) {
               // Toast.makeText(AdminHome.this,""+t,Toast.LENGTH_SHORT).show();
                totalpoints();
            }
        });
    }
    public void totalpoints(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<ReportReponse>> call=apiService.totalpoints();
        call.enqueue(new Callback<List<ReportReponse>>() {
            @Override
            public void onResponse(Call<List<ReportReponse>> call, Response<List<ReportReponse>> response) {
                //  Toast.makeText(AdminHome.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ReportReponse>> call, Throwable t) {
                // Toast.makeText(AdminHome.this,""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void storetoken(String token)
    {
        //  Toast.makeText(AdminHome.this, "Welcome to Shree Shiv Sewak"+user.getMOBILE(), Toast.LENGTH_SHORT).show();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiinterface=retrofit.create(ApiService.class);
        Call<List<Registerdevice>> call=apiinterface.storetoken(token,user.getMOBILE());
        call.enqueue(new Callback<List<Registerdevice>>() {
            @Override
            public void onResponse(Call<List<Registerdevice>> call, Response<List<Registerdevice>> response) {

                Toast.makeText(AdminHome.this, "Welcome to Euroils", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Registerdevice>> call, Throwable t) {
                Toast.makeText(AdminHome.this,"Error",Toast.LENGTH_SHORT).show();
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

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM,"Staff");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, LoginActivity.class);
                intent.putExtra(AppUtil.FROM,"Customer");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, Products.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AboutUs.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, ContactUs.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, Enquiry.class);
                intent.putExtra(AppUtil.DATA,user);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        TextView couponGifts = (TextView) navigationView.findViewById(R.id.nav_couponGifts);
        couponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, FirstPage.class);
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
//            startActivity(new Intent(AdminHome.class,M));
        }
    }


    private AppBarLayout appbar;
    private Toolbar toolbar;
    private Button btncouponstatus;
    private Button btnCouponSummary;
    private Button btnCouponDetails;
    private Button btnPointsTally;
    private Button btnDailyIndiviusal;
    private Button btnExecutivesCompiled;
    private Button btnManagersCompiled;
    private Button btnStaffCreation;
    private Button btnStaffEdit;
    private Button btnVendersEdit;
    private Button report;
    private Button btngiftstatus;
    private Button addcoupon;
    private Button newadd;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-12 19:42:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        btn_pr_points=findViewById(R.id.btn_pr_points_tally);
        upgrade_user=findViewById(R.id.upgrade);
        notification=findViewById(R.id.notification);
        notification.setOnClickListener(this);
        btn_pr_points.setOnClickListener(this);
        btn_registeration=findViewById(R.id.btn_registeration);
        btn_premium_retailer=findViewById(R.id.premium_retailer);
        btn_send_notification=findViewById(R.id.btn_notification);
        btn_address=findViewById(R.id.btn_address);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  newadd=(Button)findViewById(R.id.btn_newadd_coupon);
       // addcoupon=(Button)findViewById(R.id.btn_add_coupon);
        btn_add_coupon=(Button)findViewById(R.id.btn_add_coupons);
        btngiftstatus=(Button)findViewById(R.id.btn_gftstatus);
        btncouponstatus=(Button)findViewById(R.id.btn_couponstatus);
        btnCouponSummary = (Button) findViewById(R.id.btn_coupon_summary);
        btnCouponDetails = (Button) findViewById(R.id.btn_coupon_details);
        btnPointsTally = (Button) findViewById(R.id.btn_points_tally);
        btnDailyIndiviusal = (Button) findViewById(R.id.btn_daily_indiviusal);
        btnExecutivesCompiled = (Button) findViewById(R.id.btn_executives_compiled);
        btnManagersCompiled = (Button) findViewById(R.id.btn_managers_compiled);
        btnStaffCreation = (Button) findViewById(R.id.btn_staff_creation);
        btnStaffEdit = (Button) findViewById(R.id.btn_staff_edit);
        btnVendersEdit = (Button) findViewById(R.id.btn_venders_edit);
        btn_change_password= (Button) findViewById(R.id.btn_change_password);
        report=(Button)findViewById(R.id.btn_report);
        btn_vender_details = (Button) findViewById(R.id.btn_vender_details);
        btnGifts = (Button) findViewById(R.id.btn_gifts);

       // addcoupon.setOnClickListener(this);
        upgrade_user.setOnClickListener(this);
        btn_send_notification.setOnClickListener(this);
        btn_address.setOnClickListener(this);
        report.setOnClickListener(this);
        btn_premium_retailer.setOnClickListener(this);
        btn_add_coupon.setOnClickListener(this);
        btngiftstatus.setOnClickListener(this);
        btncouponstatus.setOnClickListener(this);
        btnGifts.setOnClickListener(this);
        btnCouponSummary.setOnClickListener(this);
        btnCouponDetails.setOnClickListener(this);
        btnPointsTally.setOnClickListener(this);
        btnDailyIndiviusal.setOnClickListener(this);
        btnExecutivesCompiled.setOnClickListener(this);
        btnManagersCompiled.setOnClickListener(this);
        btnStaffCreation.setOnClickListener(this);
        btnStaffEdit.setOnClickListener(this);
        btnVendersEdit.setOnClickListener(this);
        btn_vender_details.setOnClickListener(this);
        btn_change_password.setOnClickListener(this);
        btn_registeration.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-02-12 19:42:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    String[] couponArray ;
    @Override
    public void onClick(View v) {
        if(v==btn_address){
            startActivity(new Intent(AdminHome.this,UserAddress.class));
        }
        if(v==upgrade_user){
            startActivity(new Intent(AdminHome.this,upgrade_user.class));
        }
        if(v==btn_registeration){
            startActivity(new Intent(AdminHome.this, RegisterationActivity.class));
        }
        if(v==btn_send_notification){
            startActivity(new Intent(AdminHome.this,SendNotification.class).putExtra("DATA", user));
        }
        if(v==notification){
            startActivity(new Intent(AdminHome.this,MessageActivity.class));
        }
//        if(v==btn_pr_points){
//            startActivity(new Intent(AdminHome.this,MessageActivity.class));
//        }
        if(v==btn_pr_points){
            startActivity(new Intent(AdminHome.this,Pr_points_tally.class));
        }
        if (v == btnCouponSummary) {
            Intent intent = new Intent(this, CouponSummary.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }else if(v==btn_add_coupon) {
            Intent intent = new Intent(this, Newaddcoupon.class);
//            intent.putExtra("DATA", user);
            startActivity(intent);

        }/*else
         if(v==addcoupon) {
           // Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AdminHome.this,AddCoupon.class);
            startActivity(intent);
        }*/else if(v==newadd) {
            Toast.makeText(this, "newadd", Toast.LENGTH_SHORT).show();

        }else
         if (v == report) {
            Intent intent=new Intent(this, Reports.class);
            //intent.putExtra("PAGE", "download");
            startActivity(intent);
        } else if (v == btnCouponDetails) {
            Intent intent=new Intent(this, DownloadCoupon.class);
            intent.putExtra("PAGE", "download");
            startActivity(intent);
            //createFile();
            // String readFile = readFile();
            //couponArray = splitString(readFile);
            //executeExcel();
        } else if (v == btnPointsTally) {
            Intent intent = new Intent(this, PointsDetails.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "points");
            startActivity(intent);
        } else if (v == btnDailyIndiviusal) {
            Intent intent = new Intent(this, Indivisual.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "all");
            startActivity(intent);
        } else if (v == btnExecutivesCompiled) {
            Intent intent = new Intent(this, ExecutiveCompiled.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "executives");
            startActivity(intent);
        } else if (v == btnManagersCompiled) {
            Intent intent = new Intent(this, ManagerCompiled.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "managers");
            startActivity(intent);
        } else if (v == btnStaffCreation) {
            Intent intent = new Intent(this, StaffForm.class);
            intent.putExtra("DATA", user);
            intent.putExtra(AppUtil.FROM, "Staff");
            startActivity(intent);
        } else if (v == btnStaffEdit) {
            Intent intent = new Intent(this, StaffEdit.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "staff_edit");
            startActivity(intent);
        } else if (v == btnVendersEdit) {
            Intent intent = new Intent(this, PointsDetails.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "vender");
            startActivity(intent);
        }
        else if (v == btnGifts) {
            Intent intent = new Intent(this, GiftDetails.class);
            intent.putExtra("DATA", user);
            intent.putExtra("PAGE", "vender");
            startActivity(intent);
        }else if (v == btn_vender_details) {
            MUtil.showProgressDialog(this);
             if (!checkPermission()) {
                 requestPermission();
                 //  createFile();
                 //  Toast.makeText(DownloadCoupon.this,"",Toast.LENGTH_SHORT).show();
             } else {

                 createFileVendor();
             }

        } else if (v == btn_change_password) {
            Intent intent = new Intent(this,ChangePasswordActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
        }else if (v==btncouponstatus)
        {
            Intent intent=new Intent(this,Couponstatus.class);
            intent.putExtra("DATA",user);
            startActivity(intent);
        }else if(v==btngiftstatus)
        {
            Intent intent=new Intent(this,Giftstatus.class);
            intent.putExtra("DATA",user);
            startActivity(intent);
        }
         else if(v==btn_premium_retailer)
         {
             Intent intent=new Intent(this, PremiumRetailerReport.class);
             intent.putExtra("DATA", user);
             startActivity(intent);
         }

    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && cameraAccepted) {
                        createFileVendor();
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                            1);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AdminHome.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void createFileVendor() {


        List<USER> arraylist=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        GetUserPointApi getUserPointApi=retrofit.create(GetUserPointApi.class);
        Call<List<USER>> call=getUserPointApi.getdetails();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list = response.body();
                List<USER> arraylist = new ArrayList<>();
                // List<USER> day_sales = new ArrayList<USER>();

                String type = "";
                USER user = null;
                for (int i = 0; i < list.size(); i++) {

                    user = new USER();

                    String name = list.get(i).getNAME();
                    String mobile = list.get(i).getMOBILE();
                    String gift = list.get(i).getGP();
                    String points = list.get(i).getTP();
                    String degination = list.get(i).getDEGINATION();
                    String enable = list.get(i).getENABLE();
                    String state=list.get(i).getSTATE();
                    String pincode=list.get(i).getPINCODE();
                    String address=list.get(i).getADDRESS();
                    String city=list.get(i).getCITY();
                    String email=list.get(i).getEMAIL();

                    user.setDEGINATION(degination);


                    //  if (page.equalsIgnoreCase("points")) {
                    type = "user";
                    //  if (degination.equalsIgnoreCase("USER")) {
                    // day_sales.add(use);
                    user.setMOBILE(mobile);
                    user.setNAME(name);
                    user.setEMAIL(email);
                    user.setADDRESS(address);
                    user.setCITY(city);
                    user.setSTATE(state);
                    user.setPINCODE(pincode);
                    user.setENABLE(enable);
                    if (points == null) {
                        int tpoints = 0;
                        int gpoints = 0;
                        int rpoints = 0;
                        user.setGP(String.valueOf(gpoints));
                        user.setRP(String.valueOf(rpoints));
                        user.setTP(String.valueOf(tpoints));
                        //arraylist.add(user);
                    } else if (gift == null) {
                            int gif = 0;
                            String gi = String.valueOf(gif);
                            int to = Integer.parseInt(points);
                            // int gi=Integer.parseInt(gif);
                            int fi = to - gif;
                            String fina = String.valueOf(fi);
                            user.setGP(gi);
                            user.setRP(fina);
                            user.setTP(points);
                            //arraylist.add(user);
                        } else {
                            int to = Integer.parseInt(points);
                            int gi = Integer.parseInt(gift);
                            int fi = to - gi;
                            String fina = String.valueOf(fi);

                            user.setGP(gift);
                            user.setRP(fina);
                            user.setTP(points);

                        }
                        arraylist.add(user);
                    }
                    createExcelVendor(arraylist);
                    MUtil.dismissProgressDialog();
                }



                @Override
                public void onFailure(Call<List<USER>> call, Throwable t) {
                    MUtil.dismissProgressDialog();
                }
            });



        /*
        DatabaseReference databaseReferencenew = AppUtil.getUserReference(AdminHome.this);
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<USER> day_sales = new ArrayList<USER>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    USER user = postSnapshot.getValue(USER.class);
                    if(user.getDEGINATION() != null && user.getDEGINATION().equalsIgnoreCase("user")){
                        day_sales.add(user);
                    }
                }
                createExcelVendor(day_sales);
                MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });

*/
    }

    private void createExcelVendor(List<USER> day_sales) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("coupon");

        HSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("NAME");
        row.createCell((short) 1).setCellValue("MOBILE");
        row.createCell((short) 2).setCellValue("EMAIL");
        row.createCell((short) 3).setCellValue("ADDRESS");

        row.createCell((short) 4).setCellValue("CITY");
        row.createCell((short) 5).setCellValue("STATE");
        row.createCell((short) 6).setCellValue("PINCODE");
        row.createCell((short) 7).setCellValue("ENABLE");
        row.createCell((short) 8).setCellValue("TOTAL POINTS");
        row.createCell((short) 9).setCellValue("GIFT POINTS");
        row.createCell((short) 10).setCellValue("REMAINING POINTS");





        int i = 1;
        for (USER user : day_sales) {
            HSSFRow row1 = sheet.createRow((short) i);
            row1.createCell((short) 0).setCellValue(user.getNAME());
            row1.createCell((short) 1).setCellValue(user.getMOBILE());
            row1.createCell((short) 2).setCellValue(user.getEMAIL());
            row1.createCell((short) 3).setCellValue(user.getADDRESS());

            row1.createCell((short) 4).setCellValue(user.getCITY());
            row1.createCell((short) 5).setCellValue(user.getSTATE());
            row1.createCell((short) 6).setCellValue(user.getPINCODE());
            row1.createCell((short) 7).setCellValue(user.getENABLE());
            row1.createCell((short) 8).setCellValue(user.getTP());
            row1.createCell((short) 9).setCellValue(user.getGP());
            row1.createCell((short) 10).setCellValue(user.getRP());

            i++;
        }


        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "user.xls");

        if (outputFile.exists()) {
            try {
                outputFile.delete();
            } catch (Exception e) {
            }
        }
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (Exception e) {
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(outputFile);

            wb.write(fileOut);
            fileOut.close();

            MUtil.showInfoAlertDialog(this, "Excel is downloaded in your phone internal memory as the name of Downloaduser.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] splitString(String readFile) {
        String[] strings = new String[2000];
        for (int i =0;i< strings.length;i++){
            if (readFile.length() > 12) {
                int initial = (i*12);
                strings[i] = readFile.substring(initial,(initial+12));
            }
        }
        return strings;
    }


/*
    private void createFile() {
        MUtil.showProgressDialog(this);


        JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.POST,login_url2,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              //  Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
               List<COUPON> day_sales = new ArrayList<COUPON>();
                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject person = (JSONObject) response.get(i);

                        String availed_by_email = person.getString("availed_by_email");
                        String availed_by_mobile = person.getString("availed_by_mobile");
                        String availed_by_name = person.getString("availed_by_name");
                        String coupon = person.getString("coupon");
                        String date_availed = person.getString("date_availed");
                        String date_upload = person.getString("date_upload");
                        String is_availed = person.getString("is_availed");
                        String points = person.getString("points");
                        String valid_til = person.getString("valid_til");

                        // Toast.makeText(getApplicationContext(),"Coupon"+coupon,Toast.LENGTH_SHORT).show();
                        COUPON cou = new COUPON(availed_by_email, availed_by_mobile, availed_by_name, coupon, date_availed, date_upload, is_availed, points, valid_til);

                        day_sales.add(cou);

                       createExcel(day_sales);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



                    MUtil.dismissProgressDialog();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getmInstance(getApplicationContext()).addToRequestque(jsonarray);
    }
    */

/*
    private void createFile() {
        MUtil.showProgressDialog(this);
        final List<COUPON> day_sales = new ArrayList<COUPON>();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service=retrofit.create(ApiService.class);
        Call<List<COUPON>> call=service.listRespo();
        call.enqueue(new Callback<List<COUPON>>() {
            @Override
            public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {
                List<COUPON> list=response.body();
                COUPON cou=null;

                for(int i=0;i<list.size();i++){
                    cou=new COUPON();
                    String sno = list.get(i).getSno();
                    String availed_by_email = list.get(i).getAvailed_by_email();
                    String availed_by_mobile = list.get(i).getAvailed_by_mobile();
                    String availed_by_name = list.get(i).getAvailed_by_name();
                    String coupon = list.get(i).getCoupon();
                    String date_availed = list.get(i).getDate_availed();
                    String date_upload = list.get(i).getDate_upload();
                    String is_availed = list.get(i).getIs_availed();
                    String points = list.get(i).getPoints();
                    String valid_til = list.get(i).getValid_til();

                    cou.setSno(sno);
                    cou.setAvailed_by_email(availed_by_email);
                    cou.setAvailed_by_mobile(availed_by_mobile);
                    cou.setAvailed_by_name(availed_by_name);
                    cou.setCoupon(coupon);
                    cou.setDate_availed(date_availed);
                    cou.setDate_upload(date_upload);
                    cou.setIs_availed(is_availed);
                    cou.setPoints(points);
                    cou.setValid_til(valid_til);

                    day_sales.add(cou);
                }
                try {
                    createExcel(day_sales);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MUtil.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<List<COUPON>> call, Throwable t) {

            }
        });

    }
*/
/*
    private void createFile() {


        MUtil.showProgressDialog(this);
        DatabaseReference databaseReferencenew = AppUtil.getCouponReference(AdminHome.this);
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<COUPON> day_sales = new ArrayList<COUPON>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    day_sales.add(postSnapshot.getValue(COUPON.class));
                }
                try {
                    createExcel(day_sales);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });


    }
    */

    private void createExcel(List<COUPON> day_sales) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("coupons");

        HSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("SNO");
        row.createCell((short) 1).setCellValue("COUPON");
        row.createCell((short) 2).setCellValue("IS_AVAILED");
        row.createCell((short) 3).setCellValue("VALID_TILL");
        row.createCell((short) 4).setCellValue("POINTS");

        row.createCell((short) 5).setCellValue("Date_Availed");
        row.createCell((short) 6).setCellValue("Availed_by_name");
        row.createCell((short) 7).setCellValue("Availed_by_mobile");
        row.createCell((short) 8).setCellValue("Availed_by_email");



        int count = 1;

        int i = 0;
        for (COUPON coupon : day_sales) {
            if(i<0){
                return;
            }

          //  if(day_sales.get(i).getIs_availed() !=null && day_sales.get(i).getIs_availed().equalsIgnoreCase("YES")) {
                HSSFRow row1 = sheet.createRow(count);
            row1.createCell((short) 0).setCellValue(coupon.getSno());
                row1.createCell((short) 1).setCellValue(coupon.getCoupon());
                row1.createCell((short) 2).setCellValue(coupon.getIs_availed());
                row1.createCell((short) 3).setCellValue(coupon.getValid_til());
                row1.createCell((short) 4).setCellValue(coupon.getPoints());

                row1.createCell((short) 5).setCellValue(coupon.getDate_availed());
                row1.createCell((short) 6).setCellValue(coupon.getAvailed_by_name());
                row1.createCell((short) 7).setCellValue(coupon.getAvailed_by_mobile());
                row1.createCell((short) 8).setCellValue(coupon.getAvailed_by_email());

                count++;

           // }

            i++;
        }


        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "coupon.xls");

        if (outputFile.exists()) {
            try {
                outputFile.delete();
            } catch (Exception e) {
            }
        }
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (Exception e) {
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(outputFile);

            wb.write(fileOut);
            fileOut.close();

            MUtil.showInfoAlertDialog(this, "Excel is downloaded in your phone internal memory as the name of Downloadcoupon.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    int i = 0;
    private void executeExcel() {

        if ( i < couponArray.length) {
            final COUPON coupon = new COUPON();
            coupon.setCoupon(couponArray[i]);
            coupon.setDate_upload(MUtil.getCurrentDate());
            coupon.setValid_til("2018-03-31");
            coupon.setIs_availed("no");
            coupon.setPoints("60");
            DatabaseReference databaseReference = AppUtil.getCouponReference(AdminHome.this);
            databaseReference.child(coupon.getCoupon().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    //MUtil.dismissProgressDialog();
                   /* if (databaseError != null) {
                        MUtil.showSnakbar(AdminHome.this, "Coupon addition failed try again ");
                    } else {
                        MUtil.showSnakbar(AdminHome.this, "Coupon added successfully.");

                    }*/
                    Log.i("execute excel",""+coupon.getCoupon()+" "+i+" -points-"+coupon.getPoints());
                    i++;
                    executeExcel();
                }
            });
        }
    }


    public static synchronized String readFile() {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "coupons.txt");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                reader = new BufferedReader(inputStreamReader);
                String currentLine = "";
                while ((currentLine = reader.readLine()) != null) {
                    fileData.append(currentLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }

        }
        String data = fileData.toString();
        if (data != null && data.length() == 0) {
            return "ERROR";
        } else if (data == null) {
            return "ERROR";
        } else {
            return data;
        }
    }



    /*DatabaseReference databaseReference = AppUtil.getDataReference(AdminHome.this);
    databaseReference.child("COUPON").setValue("");*/

}
