package ebase.hkgrox.com.ebase.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ebase.hkgrox.com.ebase.Api.Apiorder;
import ebase.hkgrox.com.ebase.Api.Apireport;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DailyVisitReport extends AppCompatActivity implements LocationListener {

    EditText contctperson, order, remark, orderdetail, mobil, vphone, vemail, vcategory, vsegment, vaddress, vcity, vpm;

    String status[] = {"open", "closed"};
    Button submit, btncheckin;
    USER user;
    Config config;
    String url = config.ip_url;
    Vender vender;
    LinearLayout linearLayout1, linearLayout2;
    LocationManager locationmanager;
    String checkin, checkout, checkintime;
    AutoCompleteTextView state, area, party;
    String vparty, phone, potential_pm, city, address, segment, category, email;
    String checkin_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_visit_report);
        vender = (Vender) getIntent().getExtras().getSerializable("Venders");

        state = (AutoCompleteTextView) findViewById(R.id.state);
        area = (AutoCompleteTextView) findViewById(R.id.area);
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        party = (AutoCompleteTextView) findViewById(R.id.party);
        vphone = (EditText) findViewById(R.id.phone);
        vemail = (EditText) findViewById(R.id.email);
        vcategory = (EditText) findViewById(R.id.category);
        vsegment = (EditText) findViewById(R.id.segment);
        vaddress = (EditText) findViewById(R.id.address);
        vcity = (EditText) findViewById(R.id.city);
        vpm = (EditText) findViewById(R.id.pm);

        orderdetail = (EditText) findViewById(R.id.orderdetails);
        mobil = (EditText) findViewById(R.id.phn);
        btncheckin = (Button) findViewById(R.id.checkin);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);


        user = (USER) getIntent().getExtras().getSerializable("DATA");
        //textView = (TextView) findViewById(R.id.vender_name);
        //textView.setText(vender.getParty());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Today Entry", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        contctperson = (EditText) findViewById(R.id.contactperson);
        order = (EditText) findViewById(R.id.euroilsorder);
        remark = (EditText) findViewById(R.id.remarks);
        // spinner=(Spinner)findViewById(R.id.status);
        submit = (Button) findViewById(R.id.submitbtn);

        ArrayAdapter arrayAdapter = new ArrayAdapter(DailyVisitReport.this, android.R.layout.simple_spinner_item, status);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //spinner.setAdapter(arrayAdapter);
        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fetcharea(parent.getItemAtPosition(position));
            }
        });
        area.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    area.showDropDown();

            }
        });

        area.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                area.showDropDown();
                return false;
            }
        });

        area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fetchparty(parent.getItemAtPosition(position));
            }
        });

        party.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    party.showDropDown();

            }
        });

        party.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                party.showDropDown();
                return false;
            }
        });
        party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                linearLayout2.setVisibility(View.VISIBLE);

                fetchdata(parent.getItemAtPosition(position));
            }
        });

        //.
        // Toast.makeText(this, ""+MUtil.getTodayDate(), Toast.LENGTH_SHORT).show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MUtil.showProgressDialog(DailyVisitReport.this);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                Apiorder apiorder = retrofit.create(Apiorder.class);

                // Call<List<Vender>> call=apiorder.order(user.getNAME(),user.getMOBILE(),vender.getParty(),vender.getPhone(),contctperson.getText().toString(),order.getText().toString(),MUtil.getCurrentYear(),MUtil.getCurrentMonth(),MUtil.getDayOfMonth(),MUtil.getCurrentTime(),remark.getText().toString(),status.getText().toString());
                Call<List<Vender>> call = apiorder.order(checkin, checkout, user.getNAME(), user.getMOBILE(), vparty, phone, order.getText().toString(), orderdetail.getText().toString(), checkin_date, MUtil.getTodayDate(), MUtil.getCurrentTime(), checkintime, remark.getText().toString(), "closed");

                call.enqueue(new Callback<List<Vender>>() {
                    @Override
                    public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {

                        // MUtil.showInfoAlertDialog(DailyVisitReport.this, "Record inserted...");
                        MUtil.dismissProgressDialog();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DailyVisitReport.this);
                        builder1.setMessage("Record inserted");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }

                    @Override
                    public void onFailure(Call<List<Vender>> call, Throwable t) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DailyVisitReport.this);
                        builder1.setMessage("Already inserted");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                });

            }
        });

        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(View.VISIBLE);
                checkin_date = MUtil.getTodayDate();

                checkintime = MUtil.getCurrentTime();
                if (!locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showalertoff();
                    // Toast.makeText(DailyVisitReport.this,"Latitude:1" ,Toast.LENGTH_SHORT).show();

                } else if (locationmanager.isProviderEnabled(locationmanager.GPS_PROVIDER)) {
                    //getlocation();
                    //Toast.makeText(DailyVisitReport.this,"Latitude:2",Toast.LENGTH_SHORT).show();
                    if (!checkPermission()) {
                        requestPermission();
                        //  createFile();
                        //  Toast.makeText(DownloadCoupon.this,"",Toast.LENGTH_SHORT).show();
                    } else {

                        Location location = locationmanager.getLastKnownLocation(locationmanager.NETWORK_PROVIDER);
                        onLocationChanged(location);
                    }

                }
                getdata();


            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DailyVisitReport.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }else{
                            Location location = locationmanager.getLastKnownLocation(locationmanager.NETWORK_PROVIDER);
                            onLocationChanged(location);
                        }

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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void showInfoAlertDialog(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(getApplicationContext(),SelectVender.class);
                                startActivity(intent);
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    /*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:{
                if(grantResults.length>=0&&grantResults[0]==PackageManager.PERMISSION_GRANTED);
                {
                    if(ContextCompat.checkSelfPermission(DailyVisitReport.this, Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        LocationManager locatiomanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        Location location=locatiomanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try
                        {
                            if(location==null)
                            {
                                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                checkin=herelocation(location.getLatitude(),location.getLongitude());
                                //textview.setText(herelocation(location.getLatitude(), location.getLongitude()));
                                checkout=herelocation(location.getLatitude(),location.getLongitude());
                            }
                        }catch(Exception e) {

                            e.printStackTrace();
                            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }*/

    public String herelocation(double lat, double lon)
    {


        String city="";
        String area="";
        String address="";
        String location="";
        Geocoder geocoder=new Geocoder(DailyVisitReport.this, Locale.getDefault());
        List<Address> addressList;
        try
        {
            addressList=geocoder.getFromLocation(lat,lon,1);
            if(addressList.size()>0)
            {
                //city=addressList.get(0).getLocality();
                //area=addressList.get(0).getAdminArea();
                address=addressList.get(0).getAddressLine(0);

                location=address;


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  location;
    }

    public void showalertoff()
    {
        final android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setMessage("Please turn on your gps connection")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No",new Dialog.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

        final android.app.AlertDialog alert=builder.create();
        alert.show();



    }


    @Override
    public void onLocationChanged(Location location) {
      //  Toast.makeText(DailyVisitReport.this,"Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(),Toast.LENGTH_SHORT).show();

        checkin=herelocation(location.getLatitude(),location.getLongitude());
        checkout=herelocation(location.getLatitude(),location.getLongitude());

        Toast.makeText(DailyVisitReport.this,"Latitude:" + checkin + ", Longitude:" + checkout,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void getdata()
    {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apiinterface=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apiinterface.getdata();
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                //  Toast.makeText(MainActivity.this, "Success"+response, Toast.LENGTH_SHORT).show();
                List<String> arraylist=new ArrayList<String>();



                List<Vender> list=response.body();
                for(int i=0;i<list.size();i++)
                {
                    Vender user=new Vender();

                    String state=list.get(i).getState();


                    user.setState(state);


                    arraylist.add(state);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (DailyVisitReport.this, android.R.layout.select_dialog_item, arraylist);
                state.setThreshold(1);
                state.setAdapter(adapter);


            /*

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                        (DailyVisitReport.this, android.R.layout.select_dialog_item, arraylist2);
                party.setThreshold(1);
                party.setAdapter(adapter2);*/


            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(DailyVisitReport.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetchdata(Object itemAtPosition)
    {


        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apireport=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apireport.getdetails((String) itemAtPosition);
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {

               // Toast.makeText(DailyVisitReport.this, "success", Toast.LENGTH_SHORT).show();
                Vender vender=null;
                List<Vender> list= response.body();

                for(int i=0;i<list.size();i++)
                {
                    phone=list.get(i).getPhone();
                    email=list.get(i).getEmail();
                    category=list.get(i).getRetailer();
                    segment=list.get(i).getSegment();
                    address=list.get(i).getAddress();
                    city=list.get(i).getCity();
                    potential_pm=list.get(i).getPotential();
                    vparty=list.get(i).getParty();

                    vphone.setText(phone);
                    vemail.setText(email);
                    vcategory.setText(category);
                    vsegment.setText(segment);
                    vaddress.setText(address);
                    vcity.setText(city);
                    vpm.setText(potential_pm);
                }

            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(DailyVisitReport.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetcharea(Object itemAtPosition)
    {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apiinterface=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apiinterface.getarea((String) itemAtPosition);
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                //  Toast.makeText(MainActivity.this, "Success"+response, Toast.LENGTH_SHORT).show();
                final List<String> arraylist1=new ArrayList<String>();



                List<Vender> list=response.body();
                for(int i=0;i<list.size();i++)
                {
                    Vender user=new Vender();

                    String area=list.get(i).getArea();


                    user.setState(area);


                    arraylist1.add(area);

                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (DailyVisitReport.this, android.R.layout.select_dialog_item, arraylist1);
                //
                area.setThreshold(1);
                area.setAdapter(adapter);
                /*area.setOnTouchListener(new View.OnTouchListener(){


                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {

                        area.showDropDown();
                        area.requestFocus();
                        return false;
                    }
                });*/
                /*area.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                        if (arraylist1.size() > 0) {
                            // show all suggestions
                            if (!area.getText().toString().equals(""))
                                adapter.getFilter().filter(null);
                            area.showDropDown();
                        }
                        return false;
                    }
                });*/


            /*

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                        (DailyVisitReport.this, android.R.layout.select_dialog_item, arraylist2);
                party.setThreshold(1);
                party.setAdapter(adapter2);*/


            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(DailyVisitReport.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchparty(Object itemAtPosition)
    {

        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apiinterface=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apiinterface.getparty((String) itemAtPosition);
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                //  Toast.makeText(MainActivity.this, "Success"+response, Toast.LENGTH_SHORT).show();
                List<String> arraylist2=new ArrayList<String>();



                List<Vender> list=response.body();
                for(int i=0;i<list.size();i++)
                {
                    Vender user=new Vender();

                    String party=list.get(i).getParty();


                    user.setParty(party);


                    arraylist2.add(party);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (DailyVisitReport.this, android.R.layout.select_dialog_item, arraylist2);
                party.setThreshold(1);
                party.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(DailyVisitReport.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
