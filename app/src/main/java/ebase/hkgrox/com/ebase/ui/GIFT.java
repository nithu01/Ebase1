package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ebase.hkgrox.com.ebase.Api.ApiGifts;
import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.AppController;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.GetgiftApi;
import ebase.hkgrox.com.ebase.GiftpointApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UserpointApi;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.CurrentPointResponse;
import ebase.hkgrox.com.ebase.bean.GIFTS;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import ebase.hkgrox.com.ebase.util.Mail;
import ebase.hkgrox.com.ebase.util.MySingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GIFT extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private TextView userName;
    private Spinner etPoints;
    private Spinner etGift;
    private CouponRedeem couponRedeem;
    private boolean flagResult;
    String phn;
    String finalpt;
    String pt,gpt,userpt,giftpt;
    int fin;
    Config config;
    //List<GIFTS> arraylist=new ArrayList<GIFTS>();

    String login_url2 =config.ip_url;
    // String login_url2 = "http://192.168.0.101";
    //String gifturl="http://192.168.0.103/ebase/redeemgift.php";
    String gifturl="http://onlineappm.euroils.com/ebase/redeemgift.php";
    TextView btnCouponGifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gifts);
        MUtil.showProgressDialog(this);
        findViews();


        user = (USER) getIntent().getExtras().getSerializable("DATA");

            setData();


        btnCouponGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePageActivity.this,Coupon)
                Intent intent = new Intent(GIFT.this, FirstPage.class);
                intent.putExtra("DATA", "user");
                startActivity(intent);

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Redeem Gifts", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       /* String from  = getIntent().getExtras().getString("FROM");
        if(from!=null && from.equalsIgnoreCase("individual")){
            btnAddCoupon.setVisibility(View.GONE);
        }*/
    }
    public void getdata(){
//        Toast.makeText(UserPointsActivity.this,"Success",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CouponRedeem>> listCall= apiService.pr_points(user.getMOBILE());
        listCall.enqueue(new Callback<List<CouponRedeem>>() {
            @Override
            public void onResponse(Call<List<CouponRedeem>> call, retrofit2.Response<List<CouponRedeem>> response) {

                MUtil.dismissProgressDialog();
                for(int i =0 ;i<response.body().size();i++){
                    tvTotalPoints.setText(response.body().get(i).getTP());
                    userpt = response.body().get(i).getTP();
                    giftpoint();
                }

            }

            @Override
            public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {
                Toast.makeText(GIFT.this,""+t,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setData() {

        phn=user.getMOBILE();

        if(user.getDEGINATION().equals("Premium Retailer")){
//            premium_retailer_point();
            getdata();
        }else{
            userpoint();
        }
        userName.setText("Welcome "+user.getNAME());

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
              //  tvTotal.setText(userpt);
            }

            @Override
            public void onFailure(Call<List<CurrentPointResponse>> call, Throwable t) {
                Toast.makeText(GIFT.this,"Error...",Toast.LENGTH_SHORT).show();
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


                List<USER> list=response.body();
                for(int i=0;i<list.size();i++){
                    pt=list.get(i).getTP();
                }

                userpt =pt;
                giftpoint();

            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(GIFT.this,"Error...",Toast.LENGTH_SHORT).show();
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
            finalpt = String.valueOf(fin);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();
        }
        else if(giftpt==null){
            int gp=0;
            fin = Integer.parseInt(userpt) - gp;
            finalpt = String.valueOf(fin);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();

        }else {
            fin = Integer.parseInt(userpt) - Integer.parseInt(giftpt);
            finalpt = String.valueOf(fin);
            tvTotalPoints.setText(finalpt);
            MUtil.dismissProgressDialog();
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
        btnCouponGifts=findViewById(R.id.btn_coupon_gifts);
        tvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        //        etGift = (EditText) findViewById(R.id.et_gift);
        //   etPoints = (EditText) findViewById(R.id.et_points);

        etGift = (Spinner) findViewById(R.id.et_gift);
        etPoints = (Spinner) findViewById(R.id.et_points);

        add = (AppCompatButton) findViewById(R.id.btnLogin);
        userName = (TextView) findViewById(R.id.tv_name);
        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        ApiGifts apiGifts=retrofit.create(ApiGifts.class);
        Call<List<GIFTS>>call=apiGifts.getpoint();
        call.enqueue(new Callback<List<GIFTS>>() {
            @Override
            public void onResponse(Call<List<GIFTS>> call, retrofit2.Response<List<GIFTS>> response) {
                List<GIFTS> list=response.body();


String[] arraylist=new String[list.size()];

                //GIFTS gifts=null;
                for(int i=0;i<list.size();i++){
                  //  gifts=new GIFTS();
                    arraylist[i]=list.get(i).getPoints();

                    //gifts.setPoints(points);
                }
                //arraylist.add(gifts);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GIFT.this,
                        android.R.layout.simple_spinner_item, arraylist);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                etPoints.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<GIFTS>> call, Throwable t) {

            }
        });
    etPoints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Retrofit retrofit1=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        ApiGifts apiGifts1=retrofit1.create(ApiGifts.class);
        Call<List<GIFTS>> call1=apiGifts1.getitems(etPoints.getSelectedItem().toString());
        call1.enqueue(new Callback<List<GIFTS>>() {
            @Override
            public void onResponse(Call<List<GIFTS>> call, retrofit2.Response<List<GIFTS>> response) {
                List<GIFTS> list=response.body();
                //  String a="Select Points";

                String[] arraylist=new String[list.size()];

                //GIFTS gifts=null;
                for(int i=0;i<list.size();i++){
                    //  gifts=new GIFTS();
                    arraylist[i]=list.get(i).getGift();

                    //gifts.setPoints(points);
                }
                //arraylist.add(gifts);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GIFT.this,
                        android.R.layout.simple_spinner_item, arraylist);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                etGift.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<GIFTS>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


    }






    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddCoupon) {
         /*   Intent intent = new Intent(this,AddCouponActivity.class);
            intent.putExtra("DATA", user);
            startActivity(intent);
            finish();*/

    if (isValid()) {
        CouponRedeem couponRedeem = new CouponRedeem();
        String edtpt = etPoints.getSelectedItem().toString();
        int edtpoint = Integer.parseInt(edtpt);
        if (fin <= edtpoint) {
            MUtil.showInfoAlertDialog(GIFT.this, "Don't have sufficient points..");
        } else {
            couponRedeem.setPOINT(etPoints.getSelectedItem().toString());
            couponRedeem.setMOBILE(user.getMOBILE());
            couponRedeem.setGIFT(etGift.getSelectedItem().toString());
            couponRedeem.setDATE(MUtil.getCurrentDate());
            couponRedeem.setNAME(user.getNAME());

            addPoints(couponRedeem);
        }
        //  sendMail(user, couponRedeem);

                /*
                MUtil.showProgressDialog(GIFT.this);
                DatabaseReference databaseReferencenew = AppUtil.getGiftReference(GIFT.this).child(user.getMOBILE());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(GIFT.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(GIFT.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(GIFT.this, "Coupon availed try with another coupon");
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
                });*/

    } else {
        MUtil.showInfoAlertDialog(GIFT.this, "Don't have sufficient points..");


}

        } else if (v == add) {
            // Handle clicks for add
/*

            if (isValid()) {
                MUtil.showProgressDialog(GIFT.this);
                DatabaseReference databaseReferencenew = AppUtil.getCouponReference(GIFT.this).child(etCoupon.getText().toString().toUpperCase());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        COUPON coupon = dataSnapshot.getValue(COUPON.class);
                        if (coupon == null) {
                            MUtil.showInfoAlertDialog(GIFT.this, "Coupon invalid try with another coupon");
                            MUtil.dismissProgressDialog();
                        } else {
                            if(isExpired(coupon.getValid_til())){
                                MUtil.showInfoAlertDialog(GIFT.this, "Coupon expired try with another coupon");
                                MUtil.dismissProgressDialog();
                            }else{
                                if(coupon.getIs_availed().equalsIgnoreCase("yes")){
                                    MUtil.showInfoAlertDialog(GIFT.this, "Coupon availed try with another coupon");
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


        }
    }

    private void sendMail( CouponRedeem couponRedeem) {
        this.couponRedeem = couponRedeem;
        new CAsync().execute();

/*
        final String username = "username@gmail.com";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to-email@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/


    }


    class CAsync extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
         //   Looper.prepare();
            Mail m = new Mail("euroebase@gmail.com", "euro@99@");


           // String to = "rajeev0814@gmail.com";
            String to = MUtil.EMAIL_ID;
            String[] toArr = {to};
            m.setTo(toArr);
            m.setFrom("euroebase@gmail.com");

            String subject = ""+user.getNAME() +" Requested for "+couponRedeem.getGIFT();
            m.setSubject(subject);

            String body = "Name: "+user.getNAME()+"\n"+
                    "Mobile No: "+user.getMOBILE()+"\n"+
                    "Email: "+user.getEMAIL()+"\n"+
                    "City: "+user.getCITY()+"\n"+
                    "State: "+user.getSTATE()+"\n"+
                    "Pincode: "+user.getPINCODE()+"\n"+
                    "Total Points: "+finalpt+"\n"+
                    "Requested for gift "+couponRedeem.getGIFT()
                    +" of points "+couponRedeem.getPOINT();
            m.setBody(body);

            try {
                // m.addAttachment("/sdcard/filelocation");
                flagResult = m.send();
                if (flagResult) {
                    Log.e("MailApp", "send email");
                    //// Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (!flagResult) {
                MUtil.showInfoAlertDialog(GIFT.this, "Gift Request failed try again ");
                MUtil.dismissProgressDialog();
            } else {
                MUtil.dismissProgressDialog();
                Toast.makeText(GIFT.this, "Gift Requested successfully.", Toast.LENGTH_SHORT).show();
               // MUtil.showSnakbar(GIFT.this, "Gift Requested successfully.");
                finish();
            }
        }
    }

    private void addPoints(final CouponRedeem coupon) {
        MUtil.showProgressDialog(this);
//        Retrofit retrofit=new Retrofit.Builder().baseUrl(gifturl).addConverterFactory(GsonConverterFactory.create()).build();
//        GetgiftApi getgiftApi=retrofit.create(GetgiftApi.class);
//        Call<String> call=getgiftApi.requestgift();
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(GIFT.this,"Succsess",Toast.LENGTH_SHORT).show();
//                sendMail(coupon);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(GIFT.this,"Failure"+t,Toast.LENGTH_SHORT).show();
//            }
//        });


//        StringRequest stringRequest = new StringRequest(Request.Method.GET, gifturl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(GIFT.this,"Requested successfully",Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(GIFT.this,"Something went wrong!!Please try again!",Toast.LENGTH_SHORT).show();
//            }
//        }){
//
//
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("point",coupon.getPOINT());
//                params.put("name",user.getNAME());
//                params.put("mobile",user.getMOBILE());
//                params.put("gift",coupon.getGIFT());
//                params.put("date",MUtil.getCurrentDate());
//                return params;
//            }
//
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest, "gift");


        StringRequest stringRequest1=new StringRequest(Request.Method.POST, gifturl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //sendMail(coupon);
                MUtil.dismissProgressDialog();
                recreate();
              //  etGift.setText("");
               // etPoints.setText("");
                MUtil.showInfoAlertDialog(GIFT.this, "Gift Requested successfully.");
               // MUtil.showSnakbar(GIFT.this, "Gift Requested successfully.");
             //   sendMail(coupon);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GIFT.this,""+error,Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("point",coupon.getPOINT());
                params.put("name",user.getNAME());
                params.put("mobile",user.getMOBILE());
                params.put("gift",coupon.getGIFT());
                params.put("date",MUtil.getCurrentDate());

                return params;
            }
        };
        MySingleton.getmInstance(GIFT.this).addToRequestque(stringRequest1);

       /* DatabaseReference databaseReference = AppUtil.getGiftReference(GIFT.this);

        databaseReference.child(user.getMOBILE()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    MUtil.showSnakbar(GIFT.this, "Gift Request failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                   // MUtil.dismissProgressDialog();
                   // MUtil.showSnakbar(GIFT.this, "Gift Requested successfully.");
                   // finish();
                    sendMail(coupon);
                }
            }
        });*/

    }

    private void updateCoupon(COUPON coupon) {
        coupon.setIs_availed("yes");
        DatabaseReference databaseReference = AppUtil.getCouponReference(GIFT.this).child(etCoupon.getText().toString().toUpperCase());
        databaseReference.child(etCoupon.getText().toString().toUpperCase()).setValue(coupon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(GIFT.this, "Coupon addition failed try again ");
                } else {
                    MUtil.showSnakbar(GIFT.this, "Coupon added successfully.");
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
        int pointsGift = Integer.parseInt(etPoints.getSelectedItem().toString());
        int pointsTotal = Integer.parseInt(finalpt);
        if(pointsTotal >pointsGift){
            return true;
        }else {
            return false;
        }


    }


}
