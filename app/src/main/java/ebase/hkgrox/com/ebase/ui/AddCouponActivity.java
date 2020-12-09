package ebase.hkgrox.com.ebase.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.GiftpointApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UserpointApi;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import ebase.hkgrox.com.ebase.util.MySingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCouponActivity extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private TextView userName;




    AlertDialog.Builder builder;
    String phn;
    Config config;

   String login_url2 =config.ip_url;//"http://192.168.0.103";
    String login_url=config.checkcoupon;
    String login_url1=config.updatecoupon;
    String pt,gpt,userpt,giftpt;
    int fin;
    String coupons;
    ImageView img_Scan;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        MUtil.showProgressDialog(this);
        findViews();
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        img_Scan=findViewById(R.id.scan);
        img_Scan.setOnClickListener(this);
        setData();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Availed Coupon",true);
        builder=new AlertDialog.Builder(AddCouponActivity.this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setData() {
       /* if (user != null && user.getPOINTS() != null) {
            tvTotalPoints.setText(user.getPOINTS());
        }else{
            tvTotalPoints.setText("0");
        }*/

        phn=user.getMOBILE();

        userpoint();
       /*
*/

//fin=Integer.parseInt(userpt) - Integer.parseInt(giftpt);
        // String finalpt=String.valueOf(fin);
        //tvTotalPoints.setText(pt);
        userName.setText("Welcome "+user.getNAME());

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
              //  Toast.makeText(AddCouponActivity.this,"Response"+pt,Toast.LENGTH_SHORT).show();
                userpt =pt;
                giftpoint();
                // tvTotalPoints.setText(pt);
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(AddCouponActivity.this,"error",Toast.LENGTH_SHORT).show();
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
            int gp=0;
            fin=ut-gp;
            String finalpt = String.valueOf(fin);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();
        }
           else if (giftpt == null) {
                int gp = 0;
                fin = Integer.parseInt(userpt) - gp;
                String finalpt = String.valueOf(fin);
                tvTotalPoints.setText(finalpt);
                MUtil.dismissProgressDialog();

            } else {
                fin = Integer.parseInt(userpt) - Integer.parseInt(giftpt);
                String finalpt = String.valueOf(fin);
                tvTotalPoints.setText(finalpt);
                MUtil.dismissProgressDialog();

        }
    }
       /* phn=user.getMOBILE();
        MUtil.showProgressDialog(this);
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
                Toast.makeText(AddCouponActivity.this,"Response"+pt,Toast.LENGTH_SHORT).show();

                tvTotalPoints.setText(pt);
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(AddCouponActivity.this,"Response12",Toast.LENGTH_SHORT).show();
            }
        });

        userName.setText("Welcome "+user.getNAME());
        MUtil.dismissProgressDialog();*/



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
        tvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        add = (AppCompatButton) findViewById(R.id.btnLogin);
        userName = (TextView) findViewById(R.id.tv_name);
        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddCoupon) {
            // Handle clicks for btnAddCoupon
            add.setVisibility(View.VISIBLE);
            etCoupon.setVisibility(View.VISIBLE);
            etCoupon.setText("");
            btnAddCoupon.setVisibility(View.GONE);
        } else if(v==img_Scan){
            Intent i = new Intent(AddCouponActivity.this, QrCodeActivity.class);
            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
        } else if (v == add) {
            // Handle clicks for add
/*
            if (isValid()) {
                MUtil.showProgressDialog(AddCouponActivity.this);
                DatabaseReference databaseReferencenew = AppUtil.getCouponReference(AddCouponActivity.this).child(etCoupon.getText().toString().toUpperCase());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        MUtil.dismissProgressDialog();
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(AddCouponActivity.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(AddCouponActivity.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(AddCouponActivity.this, "Coupon availed try with another coupon");
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

            coupons=etCoupon.getText().toString();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Toast.makeText(getApplicationContext(),"Response"+response,Toast.LENGTH_SHORT).show();
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

                            //Toast.makeText(AddCouponActivity.this,point,Toast.LENGTH_SHORT).show();
                            //addPoints(point);
                            updateCoupon(coupons);
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
                  //  Toast.makeText(AddCouponActivity.this,"jdfh",Toast.LENGTH_SHORT).show();
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
            MySingleton.getmInstance(AddCouponActivity.this).addToRequestque(stringRequest);
        }
    }



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
        DatabaseReference databaseReference = AppUtil.getUserReference(AddCouponActivity.this);
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
                    MUtil.showSnakbar(AddCouponActivity.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    updateCoupon(coupons);
                }
            }
        });

    }


   /* private void addPoints(final COUPON coupon) {



        DatabaseReference databaseReference = AppUtil.getUserReference(AddCouponActivity.this);
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
                    MUtil.showSnakbar(AddCouponActivity.this, "Coupon addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                   updateCoupon(coupon);
                }
            }
        });
    }
    */
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
       MySingleton.getmInstance(AddCouponActivity.this).addToRequestque(stringRequest1);

       MUtil.showSnakbar(AddCouponActivity.this, "Coupon added successfully.");
       Intent intent = new Intent(AddCouponActivity.this,UserPointsActivity.class);
       intent.putExtra("DATA", user);
       startActivity(intent);
       setResult(RESULT_OK,new Intent().putExtra("DATA",user));
       finish();
       setData();

   }
 /*   private void updateCoupon(COUPON coupon) {
        coupon.setIs_availed("YES");
        coupon.setDate_availed(MUtil.getCurrentDate());
        coupon.setAvailed_by_email(user.getEMAIL());
        coupon.setAvailed_by_mobile(user.getMOBILE());
        coupon.setAvailed_by_name(user.getNAME());
        DatabaseReference databaseReference = AppUtil.getCouponReference(AddCouponActivity.this);
        databaseReference.child(etCoupon.getText().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(AddCouponActivity.this, "Coupon addition failed try again ");
                } else {
                    MUtil.showSnakbar(AddCouponActivity.this, "Coupon added successfully.");
                    Intent intent = new Intent(AddCouponActivity.this,UserPointsActivity.class);
                    intent.putExtra("DATA", user);
                    startActivity(intent);
                    setResult(RESULT_OK,new Intent().putExtra("DATA",user));
                    finish();
                }
            }
        });
        setData();

    }
*/

    private boolean isExpired(String valid_til) {
        return false;
    }


    private boolean isValid() {
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {

            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                etCoupon.setText(result);
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            etCoupon.setText(result);

        }
    }

}
