package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Api.History;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.DAY_SALE;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagersAddSale extends AppCompatActivity implements View.OnClickListener {

    private USER user;
    private List<DAY_SALE> day_sales;
    private TextView tv_name;
    Config config;

    String login_url2 = config.ip_url;
    private String sale,todaysale,collection,todaycollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_sale_add);
        findViews();
        user = (USER)getIntent().getExtras().getSerializable("DATA");
        setData(user);
        getHistory(user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Add Sale",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getHistory(USER user) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historymonth(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
                DAY_SALE daySale=null;
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for(int i=0;i<list.size();i++) {
                    daySale = new DAY_SALE();
                    String a = list.get(i).getSALE();
                    String b=list.get(i).getCOLLECTION();
                    daySale.setSALE(a);
                    daySale.setCOLLECTION(b);
                    day_sales.add(daySale);
                }
               // getHistoryDay(user);
                setValue(day_sales);


            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });

        /*
        MUtil.showProgressDialog(ManagersAddSale.this);
        DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersAddSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    day_sales.add(postSnapshot.getValue(DAY_SALE.class));
                }

                if (day_sales.size() > 0) {
                    setValue(day_sales);
                }
                MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

    }
    private void setValue(List<DAY_SALE> day_sales) {
        this.day_sales =day_sales;
      //  tvSaleToday.setText(day_sales.get(day_sales.size() - 1).getSALE());
        double totalSale = 0;
        for (DAY_SALE day_sale : day_sales) {
            String sale = day_sale.getSALE();
            double saleInt = 0;
            if (sale != null && sale.length() > 0) {
                saleInt = Double.parseDouble(sale);
                totalSale = totalSale + saleInt;
            }
        }
        if(user.getMONTHALY_SALE()!=null && user.getMONTHALY_SALE().length()>0){
            double targetSale = Double.parseDouble(user.getMONTHALY_SALE());
            tvSaleBalance.setText(String.valueOf(Math.round(targetSale-totalSale)));
        }
        tvSaleMonth.setText(String.valueOf(Math.round(totalSale)));


       // tvCollectionToday.setText(day_sales.get(day_sales.size() - 1).getCOLLECTION());
        double totalCollection = 0;
        for (DAY_SALE day_sale : day_sales) {
            String collection = day_sale.getCOLLECTION();
            double collectionInt = 0;
            if (collection != null && collection.length() > 0) {
                collectionInt = Double.parseDouble(collection);
                totalCollection = totalCollection + collectionInt;
            }
        }

        if(user.getMONTHALY_COLLECTION()!=null && user.getMONTHALY_COLLECTION().length()>0){
            double targetCollection = Double.parseDouble(user.getMONTHALY_COLLECTION());
            tvCollectionBalance.setText(String.valueOf(Math.round(targetCollection-totalCollection)));
        }


        tvCollectionMonth.setText(String.valueOf(Math.round(totalCollection)));
    }


    private void setData(USER user) {
        if(user!=null){
            if(user.getMONTHALY_COLLECTION()!=null){
                tvMonthTargetsCollection.setText(user.getMONTHALY_COLLECTION()+"  rupees");
            }
            if(user.getMONTHALY_SALE()!=null){
                tvMonthTargetsSale.setText(user.getMONTHALY_SALE()+"  liters");
            }
        }
        tv_name .setText("Welcome "+user.getNAME());
    }


    private TextView tvMonthTargetsSale;
    private TextView tvMonthTargetsCollection;
    private TextView tvSaleToday;
    private TextView tvSaleMonth;
    private TextView tvSaleBalance;
    private TextView tvCollectionToday;
    private TextView tvCollectionMonth;
    private TextView tvCollectionBalance;
    private AppCompatButton btnAddCoupon;
    private EditText etSale;
    private EditText etCollection;
    private AppCompatButton add;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-01 23:49:40 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvMonthTargetsSale = (TextView) findViewById(R.id.tv_month_targets_sale);
        tvMonthTargetsCollection = (TextView) findViewById(R.id.tv_month_targets_collection);
        tvSaleToday = (TextView) findViewById(R.id.tv_sale_today);
        tvSaleMonth = (TextView) findViewById(R.id.tv_sale_month);
        tvSaleBalance = (TextView) findViewById(R.id.tv_sale_balance);
        tvCollectionToday = (TextView) findViewById(R.id.tv_collection_today);
        tvCollectionMonth = (TextView) findViewById(R.id.tv_collection_month);
        tvCollectionBalance = (TextView) findViewById(R.id.tv_collection_balance);
        btnAddCoupon = (AppCompatButton) findViewById(R.id.btn_add_coupon);
        etSale = (EditText) findViewById(R.id.et_sale);
        etCollection = (EditText) findViewById(R.id.et_collection);
        add = (AppCompatButton) findViewById(R.id.btnLogin);

        btnAddCoupon.setOnClickListener(this);
        add.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
    }

    /**
     * Handle button click events<br />
     * <br />
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddCoupon) {

         /*   add.setVisibility(View.VISIBLE);
            etSale.setVisibility(View.VISIBLE);
            etSale.setText("");
            etCollection.setVisibility(View.VISIBLE);
            etCollection.setText("");
            btnAddCoupon.setVisibility(View.GONE);
            ((NestedScrollView)findViewById(R.id.nsv_main)).fullScroll(View.FOCUS_DOWN);*/
            openDatePicker();
            // Handle clicks for btnAddCoupon
        } else if (v == add) {
            // Handle clicks for add
            if(isValid()){
                addTarget(user);
            }
        }
    }

    private void openDatePicker() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
       /* DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                R.style.AppTheme, pickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));*/


        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                pickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );

        int year = cal.get(Calendar.YEAR);
        cal.set((year ), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setMaxDate(cal);

        Calendar calold = Calendar.getInstance(TimeZone.getDefault());
        int month = cal.get(Calendar.MONTH);
        //if(month>0){
            calold.set((year ), (cal.get(Calendar.MONTH)), 1);
       // }else{
           // calold.set((year+1 ), (11), cal.get(Calendar.DAY_OF_MONTH));
       // }
        datePicker.setMinDate(calold);

        datePicker.setCancelable(true);

        datePicker.show(this.getFragmentManager(), "Data Picker");
    }
    int year ;
    int month ;
    int day ;
    String monthString;
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerDialog view, int selectedYear,
                              int selectedMonth, int selectedDay) {

       /* }

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

*/
             year = selectedYear;
             month = selectedMonth;
             day = selectedDay;
            monthString = new DateFormatSymbols().getMonths()[month].substring(0,3);
            String appointDate = new StringBuilder().append(day)
                    .append("-").append(monthString).append("-").append(year).toString().trim();
            btnAddCoupon.setText(appointDate);
        }
    };

    private boolean isValid()
    {
        if(year==0){
            MUtil.showSnakbar(this,"Please select date");
            return false;
        }
        return true;
    }


    private void addTarget(final USER user) {
        final String monthString = new DateFormatSymbols().getMonths()[month].substring(0,3);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.addsalemanager(user.getMOBILE(),String.valueOf(year),monthString.toUpperCase(),String.valueOf(day),etSale.getText().toString(),etCollection.getText().toString());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                Toast.makeText(ManagersAddSale.this, "Success", Toast.LENGTH_SHORT).show();
                getHistory1(user);
            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

                Toast.makeText(ManagersAddSale.this, "You Can't Update Previous Sale and Collection", Toast.LENGTH_SHORT).show();
            }
        });


/*
        MUtil.showProgressDialog(ManagersAddSale.this);
        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersAddSale.this).child(user.getMOBILE()).child(String.valueOf(year)).child(String.valueOf(monthString).toUpperCase()).child(String.valueOf(day));
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             /*   DAY_SALE day_sale = dataSnapshot.getValue(DAY_SALE.class);
                addTarget(user, day_sale);
                DAY_SALE day_sale = dataSnapshot.getValue(DAY_SALE.class);

                if (day_sale != null) {
                    if (monthString.equalsIgnoreCase(MUtil.getCurrentMonth()) && String.valueOf(day).equalsIgnoreCase(MUtil.getDayOfMonth())) {
                        addTarget(user, day_sale);
                    } else {
                        MUtil.showInfoAlertDialog(ManagersAddSale.this, "You cant update privious sale");
                        MUtil.dismissProgressDialog();
                    }


                } else {
                    addTarget(user, day_sale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/
    }



   /* private void addTarget(final USER user) {
        MUtil.showProgressDialog(ExecutivesSaleAdd.this);
        final String monthString = new DateFormatSymbols().getMonths()[month].substring(0,3);
        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSaleAdd.this).child(user.getMOBILE()).child(String.valueOf(year)).child(monthString.toUpperCase()).child(String.valueOf(day));

        //   final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ExecutivesSaleAdd.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DAY_SALE day_sale = dataSnapshot.getValue(DAY_SALE.class);

                if (day_sale != null) {
                    if (monthString.equalsIgnoreCase(MUtil.getCurrentMonth()) && String.valueOf(day).equalsIgnoreCase(MUtil.getDayOfMonth())) {
                        addTarget(user, day_sale);
                    } else {
                        MUtil.showInfoAlertDialog(ExecutivesSaleAdd.this, "You cant update privious sale");
                        MUtil.dismissProgressDialog();
                    }


                } else {
                    addTarget(user, day_sale);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });

    }*/





    private void addTarget(final USER user, DAY_SALE day_sale) {
        day_sale = new DAY_SALE();

        if (day_sale.getSALE() == null || day_sale.getSALE().length() < 1) {
            day_sale = new DAY_SALE();
            day_sale.setSALE(etSale.getText().toString());
        } else {
            int totalSale = Integer.parseInt(etSale.getText().toString());
            String sale = day_sale.getSALE();
            int saleInt = 0;
            try {
                saleInt = Integer.valueOf(sale);
            } catch (Exception e) {

            }
            totalSale = totalSale + saleInt;
            day_sale.setSALE(String.valueOf(totalSale));
        }


        if (day_sale.getCOLLECTION() == null || day_sale.getCOLLECTION().length() < 1) {
            day_sale.setCOLLECTION(etCollection.getText().toString());
        } else {
            int totalCollection = Integer.parseInt(etCollection.getText().toString());
            String collection = day_sale.getCOLLECTION();
            int collectionInt = 0;
            try {
                collectionInt = Integer.valueOf(collection);
            } catch (Exception e) {

            }
            totalCollection = totalCollection + collectionInt;
            day_sale.setCOLLECTION(String.valueOf(totalCollection));
        }


        String monthString = new DateFormatSymbols().getMonths()[month].substring(0,3);



        final DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersAddSale.this).child(user.getMOBILE()).child(String.valueOf(year)).child(monthString.toUpperCase())
                ;
        final DAY_SALE finalDay_sale = day_sale;
        databaseReferencenew.child(String.valueOf(day)).setValue(day_sale, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {
                    MUtil.showSnakbar(ManagersAddSale.this, "Sale addition failed try again ");
                    MUtil.dismissProgressDialog();
                } else {
                    if(day_sales!=null && day_sales.size()>0){
                        day_sales.remove(day_sales.size()-1);
                        day_sales.add(finalDay_sale);
                        setValue(day_sales);

                    }
                    getHistory1(user);
                   /* add.setVisibility(View.GONE);
                    etSale.setVisibility(View.GONE);
                    etCollection.setVisibility(View.GONE);
                    btnAddCoupon.setVisibility(View.VISIBLE);*/

                }
            }
        });
    }

























    private void getHistory1(USER user) {


        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historymonth(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
                DAY_SALE daySale=null;
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for(int i=0;i<list.size();i++) {
                    daySale = new DAY_SALE();
                    String a = list.get(i).getSALE();
                    String b=list.get(i).getCOLLECTION();
                    daySale.setSALE(a);
                    daySale.setCOLLECTION(b);
                    day_sales.add(daySale);
                }
               // getHistoryDay(user);
                setValue1(day_sales);


            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });
/*
        DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersAddSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    day_sales.add(postSnapshot.getValue(DAY_SALE.class));
                }


                setValue1(day_sales);

                // MUtil.dismissProgressDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
        */


    }


    private void setValue1(List<DAY_SALE> day_sales) {

        this.day_sales =day_sales;
        tvSaleToday.setText(day_sales.get(day_sales.size() - 1).getSALE());
        double totalSale = 0;
        for (DAY_SALE day_sale : day_sales) {
            String sale = day_sale.getSALE();
            double saleInt = 0;
            if (sale != null && sale.length() > 0) {
                saleInt = Double.parseDouble(sale);
                totalSale = totalSale + saleInt;
            }
        }
        if(user.getMONTHALY_SALE()!=null && user.getMONTHALY_SALE().length()>0){
            double targetSale = Double.parseDouble(user.getMONTHALY_SALE());
            tvSaleBalance.setText(String.valueOf(Math.round(targetSale-totalSale)));
        }
        tvSaleMonth.setText(String.valueOf(Math.round(totalSale)));


        tvCollectionToday.setText(day_sales.get(day_sales.size() - 1).getCOLLECTION());
        double totalCollection = 0;
        for (DAY_SALE day_sale : day_sales) {
            String collection = day_sale.getCOLLECTION();
            double collectionInt = 0;
            if (collection != null && collection.length() > 0) {
                collectionInt = Double.parseDouble(collection);
                totalCollection = totalCollection + collectionInt;
            }
        }

        if(user.getMONTHALY_COLLECTION()!=null && user.getMONTHALY_COLLECTION().length()>0){
            double targetCollection = Double.parseDouble(user.getMONTHALY_COLLECTION());
            tvCollectionBalance.setText(String.valueOf(Math.round(targetCollection-totalCollection)));
        }


        tvCollectionMonth.setText(String.valueOf(Math.round(totalCollection)));


        user.setCURRENT_MST(String.valueOf(totalSale));
        user.setCURRENT_MCT(String.valueOf(totalCollection));
        getHistoryDay(user);
    }






    private void getHistoryDay(final USER user) {
        //  MUtil.showProgressDialog(ExecutivesSale.this);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        History history=retrofit.create(History.class);
        Call<List<DAY_SALE>> call=history.historytoday(user.getMOBILE(),MUtil.getCurrentYear(),MUtil.getCurrentMonth(),MUtil.getDayOfMonth());
        call.enqueue(new Callback<List<DAY_SALE>>() {
            @Override
            public void onResponse(Call<List<DAY_SALE>> call, Response<List<DAY_SALE>> response) {
                List<DAY_SALE> list=response.body();
                DAY_SALE daySale=null;
                String a;
                List<DAY_SALE> day_sales = new ArrayList<DAY_SALE>();
                for(int i=0;i<list.size();i++) {
                    // daySale = new DAY_SALE();
                    sale = list.get(i).getSALE();
                    collection=list.get(i).getCOLLECTION();

                    //daySale.setSALE(a);
                }
                // day_sales.add(daySale);
                todaycollection=collection;
                todaysale=sale;
                if(todaysale!=null){
                    try {

                        tvSaleToday.setText(todaysale + "  liters");
                        tvCollectionToday.setText(todaycollection+" rupees");
                        // tvSaleToday.setText(day_sales.getSALE() + "  liters");
                        //tvCollectionToday.setText(day_sales.getCOLLECTION()+" rupees");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{
                    tvSaleToday.setText("0");
                    tvCollectionToday.setText("0");
                }
                String cal=String.valueOf(year)+"-"+month+1+"-"+String.valueOf(day).toUpperCase().trim();
                String today=MUtil.getTodayDate().toUpperCase().trim();
                if(cal.equals(today)){
                    //Toast.makeText(ExecutivesSaleAdd.this,String.valueOf(year)+"-"+month+1+"-"+String.valueOf(day).toUpperCase()+",,,,"+MUtil.getTodayDate(), Toast.LENGTH_SHORT).show();
                    upload(user);
                }
                else {
                    /*Toast.makeText(ManagersAddSale.this, "Sale Added Successfully...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ManagersAddSale.this,ManagersSale.class);
                    intent.putExtra("DATA",user);
                    startActivity(intent);
                    finish();*/

                    update(user);
                }
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<DAY_SALE>> call, Throwable t) {

            }
        });

        /*
        DatabaseReference databaseReferencenew = AppUtil.getHistoryReference(ManagersAddSale.this).child(user.getMOBILE()).child(MUtil.getCurrentYear()).child(MUtil.getCurrentMonth().toUpperCase()).child(MUtil.getDayOfMonth());
        databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DAY_SALE day_sales = dataSnapshot.getValue(DAY_SALE.class);
                if(day_sales!=null){
                    try {
                        // tvSaleToday.setText(day_sales.getSALE() + "  liters");
                        user.setTODAY_MST(String.valueOf(day_sales.getSALE()));
                        user.setTODAY_MCT(String.valueOf(day_sales.getCOLLECTION()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    user.setTODAY_MST("");
                    user.setTODAY_MCT("");
                }
                //  MUtil.dismissProgressDialog();
                user.setDATE(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth());
                upload(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MUtil.dismissProgressDialog();
            }
        });
*/

    }

    private void upload(final USER user) {
        final Context context = this;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender = retrofit.create(Addvender.class);
        Call<List<USER>> call = addvender.updatemanager(user.getMOBILE(), String.valueOf(year) + "-" + monthString + "-" + String.valueOf(day).toUpperCase(), tvSaleMonth.getText().toString(), tvCollectionMonth.getText().toString(), tvSaleToday.getText().toString(), tvCollectionToday.getText().toString());
        // Call<List<USER>> call=addvender.updateexecutive("7678426568","2018-JAN-8","1000","1");
        //Toast.makeText(context,user.getMOBILE()+""+String.valueOf(year)+"-"+monthString+"-"+String.valueOf(day)+tvSaleMonth.getText().toString()+tvSaleToday.getText().toString(),Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                Toast.makeText(ManagersAddSale.this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagersAddSale.this, ManagersSale.class);
                intent.putExtra("DATA", user);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(ManagersAddSale.this, "Failure", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void update(final USER user) {
        final Context context = this;
        Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender=retrofit.create(Addvender.class);
        Call<List<USER>> call=addvender.updateman(user.getMOBILE(),tvSaleMonth.getText().toString(),tvCollectionMonth.getText().toString());
        // Call<List<USER>> call=addvender.updateexecutive("7678426568","2018-JAN-8","1000","1");
        //Toast.makeText(context,user.getMOBILE()+""+String.valueOf(year)+"-"+monthString+"-"+String.valueOf(day)+tvSaleMonth.getText().toString()+tvSaleToday.getText().toString(),Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                Toast.makeText(ManagersAddSale.this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagersAddSale.this,ManagersSale.class);
                intent.putExtra("DATA",user);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                Toast.makeText(ManagersAddSale.this, "Failure", Toast.LENGTH_SHORT).show();

            }
        });


/*
        DatabaseReference databaseReference = AppUtil.getUserReference((Activity) context);
        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(context, "Target addition failed try again ");
                } else {
                    MUtil.showSnakbar(context, "Target addition successfully");

                    Intent intent = new Intent(ManagersAddSale.this,ManagersSale.class);
                    intent.putExtra("DATA", user);
                    startActivity(intent);
                    finish();
                }
            }
        });*/

    }


}
