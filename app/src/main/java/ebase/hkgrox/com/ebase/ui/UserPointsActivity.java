package ebase.hkgrox.com.ebase.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.CouponAdminUserDetail;
import ebase.hkgrox.com.ebase.GiftpointApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UserpointApi;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.CurrentPointResponse;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPointsActivity extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private TextView userName;
//    String login_url="http://192.168.0.101/ebase/checkcoupon.php";
   // String login_url1="http://192.168.0.101/ebase/updatecoupon.php";

   // String login_url2 = "http://192.168.0.103";
    Config config;
    Button btn_points,btn_pr_retailer,btn_retailer;
    String login_url2 = config.ip_url;
    String login_url=config.ip_url;
    String login_url1=config.ip_url;
    AlertDialog.Builder builder;
    String coupons;
    String phn;
    int fin;
    String pt,gpt,giftpt,userpt;
    TextView txt_premium_retailer_total,txt_premium_retailer,txt_retailer;
    LinearLayout linearLayout1,linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_points);
        MUtil.showProgressDialog(this);
        findViews();

        user = (USER) getIntent().getExtras().getSerializable("DATA");
        userName.setText("Welcome "+user.getNAME());
      //  Toast.makeText(UserPointsActivity.this,""+user.getUpgrade().equals("yes"),Toast.LENGTH_SHORT).show();
        if(user.getDEGINATION().equals("Premium Retailer") || user.getUpgrade().equals("yes")){

             getdata();
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.GONE);

        }else{

            setData();
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);

        }
//        button=(Button)findViewById(R.id.points);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(UserPointsActivity.this,CouponAdminUserDetail.class);
//                intent.putExtra("DATA",user);
//                startActivity(intent);
//            }
//        });





        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Points",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String from  = getIntent().getExtras().getString("FROM");
        if(from!=null && from.equalsIgnoreCase("individual")){
       //     btnAddCoupon.setVisibility(View.GONE);
        }
    }

    public void getdata(){
//        Toast.makeText(UserPointsActivity.this,"Success",Toast.LENGTH_SHORT).show();
       Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
       ApiService apiService = retrofit.create(ApiService.class);
       Call<List<CouponRedeem>> listCall= apiService.pr_points(user.getMOBILE());
       listCall.enqueue(new Callback<List<CouponRedeem>>() {
           @Override
           public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
               MUtil.dismissProgressDialog();
              // Toast.makeText(UserPointsActivity.this,"Success",Toast.LENGTH_SHORT).show();
               for(int i =0 ;i<response.body().size();i++){

                   txt_premium_retailer.setText( response.body().get(i).getApr());
                   if(response.body().get(i).getBpr()==null){
                       txt_retailer.setText("0");

                   }else{
                       txt_retailer.setText(response.body().get(i).getBpr());

                   }
                   txt_premium_retailer_total.setText(response.body().get(i).getTP());
                //   Toast.makeText(UserPointsActivity.this,""+response.body().get(i).getApr()+response.body().get(i).getBpr(),Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {
            Toast.makeText(UserPointsActivity.this,""+t,Toast.LENGTH_SHORT).show();
           }
       });
    }
    private void setData() {
       /* if (user != null && user.getPOINTS() != null) {
            tvTotalPoints.setText(user.getPOINTS());
        }else{
            tvTotalPoints.setText("0");
        }*/
      //  final int[] giftpt = new int[1];
       // final int[] userpt = new int[1];

        phn=user.getMOBILE();
        if(user.getDEGINATION().equals("Premium Retailer")){
            premium_retailer_point();
        }else{
            userpoint();
        }

       /*
*/

//fin=Integer.parseInt(userpt) - Integer.parseInt(giftpt);
       // String finalpt=String.valueOf(fin);
        //tvTotalPoints.setText(pt);


    }
    public void premium_retailer_point(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService userpointApi=retrofit.create(ApiService.class);
        Call<List<CurrentPointResponse>> call=userpointApi.getcurrentPoint(phn);
        call.enqueue(new Callback<List<CurrentPointResponse>>() {
            @Override
            public void onResponse(Call<List<CurrentPointResponse>> call, retrofit2.Response<List<CurrentPointResponse>> response) {

                //Toast.makeText(UserPointsActivity.this,"  "+response,Toast.LENGTH_SHORT).show();
                List<CurrentPointResponse> list=response.body();
                for(int i=0;i<list.size();i++){
                    pt=list.get(i).getTotalPoints();
                }
                // Toast.makeText(UserPointsActivity.this,"Response"+pt,Toast.LENGTH_SHORT).show();
                userpt =pt;
                giftpoint();
                tvTotal.setText(userpt);
            }

            @Override
            public void onFailure(Call<List<CurrentPointResponse>> call, Throwable t) {
                Toast.makeText(UserPointsActivity.this,"Error...",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void userpoint() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        UserpointApi userpointApi=retrofit.create(UserpointApi.class);
        Call<List<USER>> call=userpointApi.userpoint(phn);
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, retrofit2.Response<List<USER>> response) {

                //Toast.makeText(UserPointsActivity.this,"  "+response,Toast.LENGTH_SHORT).show();
                List<USER> list=response.body();
                for(int i=0;i<list.size();i++){
                    pt=list.get(i).getTP();
                }
               // Toast.makeText(UserPointsActivity.this,"Response"+pt,Toast.LENGTH_SHORT).show();
                userpt =pt;
                giftpoint();
                tvTotal.setText(userpt);
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(UserPointsActivity.this,"Error...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void giftpoint() {
        Retrofit retrofit2=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        GiftpointApi giftpointApi=retrofit2.create(GiftpointApi.class);
        Call<List<CouponRedeem>> call2=giftpointApi.giftpoint(phn);
        call2.enqueue(new Callback<List<CouponRedeem>>() {
            @Override
            public void onResponse(Call<List<CouponRedeem>> call, retrofit2.Response<List<CouponRedeem>> response) {
                List<CouponRedeem> arraylist=response.body();
                for(int i=0;i<arraylist.size();i++){
                    gpt=arraylist.get(i).getGP();
                }
                giftpt =gpt;
                total();
              //  Toast.makeText(UserPointsActivity.this,"Response"+gpt,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {

            }
        });
    }

    private void total() {
        if(userpt==null) {
            int ut=0;
            String upt=String.valueOf(ut);
            int gp=0;
            String ugp=String.valueOf(gp);
            fin=ut-gp;
            String finalpt = String.valueOf(fin);
            tvTotal.setText(upt);
            tvGift.setText(ugp);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();
        }
        else if(giftpt==null){
            int gp=0;
            fin = Integer.parseInt(userpt) - gp;
            String ugp=String.valueOf(gp);
            String finalpt = String.valueOf(fin);
            tvTotal.setText(userpt);
            tvGift.setText(ugp);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();

        }else {
            fin = Integer.parseInt(userpt) - Integer.parseInt(giftpt);
            String finalpt = String.valueOf(fin);
            tvTotal.setText(userpt);
            tvGift.setText(giftpt);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();
        }

    }


    private TextView tvTotalPoints,tvTotal,tvGift;
   // private AppCompatButton btnAddCoupon;
    private EditText etCoupon;
   // private AppCompatButton add;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-01 20:34:16 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    private void findViews() {
        btn_retailer =findViewById(R.id.points_r);
        btn_retailer.setOnClickListener(this);
        linearLayout1= findViewById(R.id.linearlayout);
        linearLayout2=findViewById(R.id.linearlayout1);
        txt_premium_retailer = findViewById(R.id.premium_retailer);
        txt_premium_retailer_total = findViewById(R.id.premium_retailer_total);
        txt_retailer = findViewById(R.id.retailer);
        btn_points = findViewById(R.id.old_points);
        btn_pr_retailer = findViewById(R.id.points);
        btn_points.setOnClickListener(this);
        btn_pr_retailer.setOnClickListener(this);
        tvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        tvTotal = (TextView) findViewById(R.id.textView8);
        tvGift = (TextView) findViewById(R.id.textView9);
       // btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        //add = (AppCompatButton) findViewById(R.id.btnLogin);
        userName = (TextView) findViewById(R.id.tv_name);
        //btnAddCoupon.setOnClickListener(this);
      //  add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==btn_points){
            startActivity(new Intent(UserPointsActivity.this, Before_Premium_Retailer.class).putExtra("mobile",user.getMOBILE()));

        }
        if(v==btn_pr_retailer){
            startActivity(new Intent(UserPointsActivity.this, CheckAllMonthPremiumRetailer.class).putExtra("DATA",user.getMOBILE()));

        }
        if(v==btn_retailer){
            startActivity(new Intent(UserPointsActivity.this, Coupondetails.class).putExtra("mobile",user.getMOBILE()));

        }

       /* if (v == btnAddCoupon) {
            Intent intent = new Intent(this,AddCouponActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
            finish();
        } else */



       /*if (v == add) {
            // Handle clicks for add

       /*     if (isValid()) {
                MUtil.showProgressDialog(UserPointsActivity.this);
                DatabaseReference databaseReferencenew = AppUtil.getCouponReference(UserPointsActivity.this).child(etCoupon.getText().toString().toUpperCase());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(UserPointsActivity.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(UserPointsActivity.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(UserPointsActivity.this, "Coupon availed try with another coupon");
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
            */
          /*  coupons=etCoupon.getText().toString();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(),"Response"+response,Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String code=jsonObject.getString("code");
                        if(code.equals("coupon_not_available")){
                            builder.setTitle("Error...");
                            displayAlert(jsonObject.getString("message"));

                        }
                        else if(code.equals("coupon_not_exist")){
                            builder.setTitle("Error...");
                            displayAlert(jsonObject.getString("message"));


                        }
                        else if(code.equals("coupon_expired")){
                            builder.setTitle("Error...");
                            displayAlert(jsonObject.getString("message"));


                        }

                        else if(code.equals("coupon_available")){
                            // builder.setTitle("...");
                            //displayAlert(jsonObject.getString("message"));
                            String point=jsonObject.getString("message");

                            Toast.makeText(UserPointsActivity.this,point,Toast.LENGTH_SHORT).show();
                            addPoints(point);
                        }
                        else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UserPointsActivity.this,"jdfh",Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("coupon",coupons);
                    params.put("date_availed",MUtil.getCurrentDate());

                    return params;
                }
            };
            MySingleton.getmInstance(UserPointsActivity.this).addToRequestque(stringRequest);
        }
    }

*/
/*
    public void  displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etCoupon.setText("");

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void addPoints(String point) {
        COUPON coupon;
        DatabaseReference databaseReference = AppUtil.getUserReference(UserPointsActivity.this);
        String points = user.getPOINTS();
        String pointsAdd = point;
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
                    MUtil.showSnakbar(UserPointsActivity.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    updateCoupon(coupons);
                }
            }
        });

    }
*/
/*
    private void addPoints(final COUPON coupon) {



        DatabaseReference databaseReference = AppUtil.getUserReference(UserPointsActivity.this);
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
                    MUtil.showSnakbar(UserPointsActivity.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    updateCoupon(coupon);
                }
            }
        });
    }
    */
/*
private void updateCoupon(final String coupons) {
    StringRequest stringRequest1=new StringRequest(Request.Method.POST, login_url1, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params=new HashMap<String, String>();
            params.put("coupon",coupons);
            params.put("availed_by_name",user.getNAME());
            params.put("availed_by_mobile",user.getMOBILE());
            params.put("availed_by_email",user.getEMAIL());
            params.put("date_availed",MUtil.getCurrentDate());

            return params;
        }
    };
    MySingleton.getmInstance(UserPointsActivity.this).addToRequestque(stringRequest1);

    MUtil.showSnakbar(UserPointsActivity.this, "Coupon added successfully.");
   // add.setVisibility(View.GONE);
   // etCoupon.setVisibility(View.GONE);
    //btnAddCoupon.setVisibility(View.VISIBLE);
    tvTotalPoints.setText(user.getPOINTS());
    setData();
}
*/

  /*  private void updateCoupon(COUPON coupon) {
        coupon.setIs_availed("yes");
        DatabaseReference databaseReference = AppUtil.getCouponReference(UserPointsActivity.this).child(etCoupon.getText().toString().toUpperCase());
        databaseReference.child(etCoupon.getText().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(UserPointsActivity.this, "Coupon addition failed try again ");
                } else {
                    MUtil.showSnakbar(UserPointsActivity.this, "Coupon added successfully.");
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
*/
    }
}
