package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.MyAdapter;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etState;
    private EditText etPincode;
    private EditText etAddress;
    Config config;
    Spinner user_type,dis_type;
    String login_url2 =config.ip_url;
    String name,address,mobile,state,city,pincode,password,email;
    List<USER> arraylist=new ArrayList<>();
//    String[] usertype = { "Select type","Distributor", "Retailer", "Premium Retailer", "USER"};
    private ArrayList<USER> day_sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  user_type=findViewById(R.id.user_type);
//        dis_type=findViewById(R.id.dis_type);
        day_sales = new ArrayList<USER>();
        getdistributor();
//        user_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String statename = parent.getItemAtPosition(position).toString();
//                if (statename.equals("Premium Retailer")){
//                    dis_type.setVisibility(View.VISIBLE);
//                    // put distributor Api here
//                }else{
//                    dis_type.setVisibility(View.GONE);
//                }
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
       /* try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {

        }*/
       /*
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Registration",false);

*/
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,usertype);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        user_type.setAdapter(aa);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Registration",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViews();
    }

    public void getdistributor(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender=retrofit.create(Addvender.class);
        Call<List<USER>> call=addvender.getdistributor();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                List<USER> list=response.body();
                USER user=null;
                USER user1 = new USER();
                user1.setNAME(" Select Distributor");
                day_sales.add(user1);
                for(int i=0;i<list.size();i++){
                    user=new USER();
                    String name=list.get(i).getNAME();
//                    String desgnation=list.get(i).getDEGINATION();
//                    String mobile=list.get(i).getMOBILE();
//                    String email=list.get(i).getEMAIL();
//                    String location=list.get(i).getLOCATION();
//                    String address=list.get(i).getADDRESS();
//
//                    String city=list.get(i).getCITY();
//                    String enable=list.get(i).getENABLE();
//                    String point=list.get(i).getPOINTS();
//                    String pincode=list.get(i).getPINCODE();
//                    String password=list.get(i).getPASSWORD();
//                    String state=list.get(i).getSTATE();
//
//                    String current_sstg=list.get(i).getCURRENT_SSTG();
//                    String date=list.get(i).getDATE();
//                    String monthaly_collection=list.get(i).getMONTHALY_COLLECTION();
//                    String monthaly_sale=list.get(i).getMONTHALY_SALE();
//                    String sstg=list.get(i).getSSTG();
//                    String today_sstg=list.get(i).getTODAY_SSTG();
//                    String current_mct=list.get(i).getCURRENT_MCT();
//
//                    String current_mst=list.get(i).getCURRENT_MST();
//                    String today_mct=list.get(i).getTODAY_MCT();
//                    String today_mst=list.get(i).getTODAY_MST();

                      user.setNAME(name);

//                    user.setDEGINATION(desgnation);
//                    user.setMOBILE(mobile);
//                    user.setEMAIL(email);
//                    user.setLOCATION(location);
//                    user.setADDRESS(address);
//
//                    user.setCITY(city);
//                    user.setENABLE(enable);
//                    user.setPOINTS(point);
//                    user.setPINCODE(pincode);
//                    user.setPASSWORD(password);
//                    user.setSTATE(state);
//
//                    user.setCURRENT_SSTG(current_sstg);
//                    user.setDATE(date);
//                    user.setMONTHALY_COLLECTION(monthaly_collection);
//                    user.setMONTHALY_SALE(monthaly_sale);
//                    user.setSSTG(sstg);
//                    user.setTODAY_SSTG(today_sstg);
//
//                    user.setCURRENT_MCT(current_mct);
//                    user.setCURRENT_MST(current_mst);
//                    user.setTODAY_MCT(today_mct);
//                    user.setTODAY_MST(today_mst);

                    day_sales.add(user);


                }
//                dis_type.setAdapter(new MyAdapter(MainActivity.this,day_sales));
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {

            }
        });
    }
    private EditText etName;
    private EditText etMobile;
    private EditText etEmail;
    private EditText etLocation;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private AppCompatButton btnSubmit;
    private TextView tvSignIn;

    private void findViews() {
        etName = (EditText)findViewById( R.id.et_name );
        etMobile = (EditText)findViewById( R.id.et_mobile );
        etEmail = (EditText)findViewById( R.id.et_email );
        etLocation = (EditText)findViewById( R.id.et_location );
        etPassword = (EditText)findViewById( R.id.etPassword );
        etState = (EditText)findViewById( R.id.et_state );
        etPincode = (EditText)findViewById( R.id.et_pincode );
        etAddress = (EditText)findViewById( R.id.et_address );

        etConfirmPassword = (EditText)findViewById( R.id.etConfirmPassword );
        btnSubmit = (AppCompatButton)findViewById( R.id.btnSubmit );
        tvSignIn = (TextView)findViewById( R.id.tvSignIn );



        btnSubmit.setOnClickListener( this );
        tvSignIn.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-02-01 17:53:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnSubmit ) {
            // Handle clicks for btnSubmit
//            Toast.makeText(MainActivity.this,""+user_type.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            if (isValid()) {
                name=etName.getText().toString();
                address=etAddress.getText().toString();
                city=etLocation.getText().toString();
                mobile=etMobile.getText().toString();
                email=etEmail.getText().toString();
                state=etState.getText().toString();
                pincode=etPincode.getText().toString();
                password=etPassword.getText().toString();
                MUtil.showProgressDialog(MainActivity.this);

                Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                Addvender addvender=retrofit.create(Addvender.class);
                Call<List<USER>> call=addvender.inseru(name,city,mobile,address,email,password,pincode,state);
                call.enqueue(new Callback<List<USER>>() {
                    @Override
                    public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                        //USER list=response.body();
                       // Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_SHORT).show();
                        List<USER> list= response.body();
                       // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                        USER user=null;
                        //String code=list.get("Code");
                        for(int i=0;i<list.size();i++)
                        {
                            user=new USER();
                            String name=list.get(i).getNAME();
                            String address=list.get(i).getADDRESS();
                            String city=list.get(i).getCITY();
                            String designtion=list.get(i).getDEGINATION();
                            String mobile=list.get(i).getMOBILE();
                            String enable=list.get(i).getENABLE();
                            String password=list.get(i).getPASSWORD();
                            String pincode=list.get(i).getPINCODE();
                            String state=list.get(i).getSTATE();
                            String email=list.get(i).getEMAIL();

                            user.setNAME(name);
                            user.setADDRESS(address);
                            user.setCITY(city);
                            user.setDEGINATION(designtion);
                            user.setMOBILE(mobile);
                            user.setENABLE(enable);
                            user.setPASSWORD(password);
                            user.setPINCODE(pincode);
                            user.setSTATE(state);
                            user.setEMAIL(email);
                            if(user.getNAME()==null) {
                                Toast.makeText(getApplicationContext(),"Already exist",Toast.LENGTH_SHORT).show();
                            }
                            else {
//                                if(user_type.getSelectedItem().toString().equals("Distributor"))
//                                {
//                                    startHomeActivity(user,"distributor");
//                                }else{
                                    startHomeActivity(user,"user");

//                                }
                            //arraylist.add(user);

                        }
                        }
                        MUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<List<USER>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Data cannot be inserted!Please try again!!",Toast.LENGTH_SHORT).show();
                        MUtil.dismissProgressDialog();
                    }
                });




             /*   DatabaseReference databaseReferencenew = AppUtil.getUserReference(MainActivity.this).child(etMobile.getText().toString());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        USER user = dataSnapshot.getValue(USER.class);
                       if(user==null){
                           registered();
                       }else{
                           MUtil.showInfoAlertDialog(MainActivity.this,"User already exist with this number try another no or login");
                           MUtil.dismissProgressDialog();
                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        MUtil.dismissProgressDialog();
                    }
                });*/

            }
        }

        if ( v == tvSignIn ) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void registered() {

        DatabaseReference databaseReference = AppUtil.getUserReference(MainActivity.this);
        final USER user = new USER();
        user.setMOBILE(etMobile.getText().toString());
        user.setEMAIL(etEmail.getText().toString());
        user.setCITY(etLocation.getText().toString());
        user.setPASSWORD(etPassword.getText().toString());
        user.setNAME(etName.getText().toString());

        user.setSTATE(etState.getText().toString());
        user.setPINCODE(etPincode.getText().toString());
        user.setADDRESS(etAddress.getText().toString());

        user.setDEGINATION("USER");
        user.setENABLE("true");

        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(MainActivity.this, "User creation failed try again ");
                } else {
                    MUtil.showSnakbar(MainActivity.this, "User created successfully.");
//                    startHomeActivity(user,);
                }
            }
        });
    }

    private void startHomeActivity(USER user,String usertype) {
        Intent intent = new Intent(this,UserHome.class);
        intent.putExtra("Usertype",usertype);
        intent.putExtra("DATA",user);
        startActivity(intent);
        finish();
    }

    private boolean isValid() {
        if(etName.getText().toString().length()<2){
            MUtil.showSnakbar(this,"Name Not Valid");
            return false;
        }

        if(etAddress.getText().toString().equals("")){
            etAddress.setError("Please fill credentials");
        }

        if(etLocation.getText().toString().equals("")){
            etLocation.setError("Please fill credentials");
        }


        if(etState.getText().toString().length()<2){
            etState.setError("Please fill credentials");
        }
        if(etPincode.getText().toString().length()<2){
            etPincode.setError("Please fill credentials");
        }


        if(etMobile.getText().toString().length()<10){
            MUtil.showSnakbar(this,"Mobile No Not Valid");
            return false;
        }
        if(etMobile.getText().toString().equals("")){

            etMobile.setError("Please fill credentials");
        }

        if(etPassword.getText().toString().length()<4){
            MUtil.showSnakbar(this,"Password Not Valid");
            return false;
        }
        return true;
    }

    class Backgroundtask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
           // Toast.makeText(getApplicationContext(),method,Toast.LENGTH_SHORT).show();
            if (method.equals("insert")) {
                String name = params[1];
                String address = params[2];
                String email = params[3];
                String mobile = params[4];
                String state = params[5];
                String city = params[6];
                String pincode = params[7];
                String password = params[8];

                try {
                    URL url = new URL(login_url2);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                            URLEncoder.encode("phn","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                            URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+"&"+
                            URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"+
                            URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");


                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line="";
                    while ((line=bufferedReader.readLine())!=null){
                        result+=line;
                    }
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            MUtil.dismissProgressDialog();
            MUtil.showSnakbar(MainActivity.this, "User created successfully.");
            Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
            startActivity(intent);
            finish();

        }
    }

}











