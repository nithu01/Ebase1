package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.Premium_Retailer_Adapter;
import ebase.hkgrox.com.ebase.adapter.Premiumretailerreport;
import ebase.hkgrox.com.ebase.bean.ReportReponse;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PremiumRetailerReport extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    TextView txt_start,txt_end;
    ImageView img_startdate,img_enddate;
    SearchView sv_search;
    int month,year;
    Premiumretailerreport premiumRetailerReport;
    ArrayList<ReportReponse> arrayList=new ArrayList<>();
    int mYear, mMonth, mDay;
    Calendar calendar;
    String date;
    USER user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_retailer_report);
        findview();
        setadapter();
        getdate();
        getpremiumretailer();
        // getdate();
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    premiumRetailerReport.getFilter().filter(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    premiumRetailerReport.getFilter().filter(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        if (id == 999) {
//            return new DatePickerDialog(PremiumRetailerReport.this, myDateListener, year, month);
//        }
//        return null;
//    }
//
//
//    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker arg0,int arg1, int arg2, int arg3) {
//
//                    showDate(arg1, arg2+1);
//                }
//    };

    public void getdate(){
         calendar=Calendar.getInstance();
         month=calendar.get(Calendar.MONTH);
         year=calendar.get(Calendar.YEAR);
         showDate(year, month+1);
       // Toast.makeText(PremiumRetailerReport.this,""+month+year+1,Toast.LENGTH_SHORT).show();
    }

    private void showDate(int year, int month) {
        txt_start.setText(new StringBuilder().append(month).append("/").append(year));
    }

    public void getpremiumretailer(){
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date=sdf.format(calendar.getTime());

        MUtil.showProgressDialog(PremiumRetailerReport.this);
//        Log.d("TAG","DATEeee"+txt_start.getText().toString()+txt_end.getText().to+String());
//        ApiService apiService=new Retrofit.Builder().baseUrl(Config.ip_url).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
//        CompositeDisposable compositeDisposiable=new CompositeDisposable();
//        compositeDisposiable.add(apiService.getReport(String.valueOf(month+1),String.valueOf(year))
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(this::handleResponse,this::handleError));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<ReportReponse>> call=apiService.getReport(date);
        call.enqueue(new Callback<List<ReportReponse>>() {
            @Override
            public void onResponse(Call<List<ReportReponse>> call, Response<List<ReportReponse>> response) {
            //    Toast.makeText(PremiumRetailerReport.this,""+response.body().get(1).getPOINT(),Toast.LENGTH_SHORT).show();

                arrayList.addAll(response.body());
                recyclerView.setAdapter(premiumRetailerReport);
                premiumRetailerReport.notifyDataSetChanged();
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<ReportReponse>> call, Throwable t) {
                MUtil.dismissProgressDialog();
                Toast.makeText(PremiumRetailerReport.this,""+t,Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void handleResponse(List<ReportReponse> reportReponse){

        arrayList.addAll(reportReponse);
        recyclerView.setAdapter(premiumRetailerReport);
        premiumRetailerReport.notifyDataSetChanged();

    }

    public void handleError(Throwable throwable){
        Toast.makeText(this,""+throwable,Toast.LENGTH_SHORT).show();

    }

    public void setadapter(){
        premiumRetailerReport=new Premiumretailerreport(arrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void findview(){
        user =(USER) getIntent().getExtras().getSerializable("DATA");
        sv_search = (SearchView) findViewById(R.id.sv_search);
      //  sv_search.setOnQueryTextListener(this);
        recyclerView=findViewById(R.id.recyclerview);
        txt_start=findViewById(R.id.startdate);
        img_startdate=findViewById(R.id.img_startdate);
        img_startdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==img_startdate){
//            Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
//             @Override
//             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                txt_start.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//              }
//           }, mYear, mMonth, mDay);
//            datePickerDialog.show();

            Calendar mcurrentDate = Calendar.getInstance();
            String myFormat = "MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            DatePickerDialog monthDatePickerDialog = new DatePickerDialog(PremiumRetailerReport.this,
                    AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mcurrentDate.set(Calendar.YEAR, year);
                    mcurrentDate.set(Calendar.MONTH, month);
                    txt_start.setText(sdf.format(mcurrentDate.getTime()));
                    mDay = dayOfMonth;
                    mMonth = month;
                    mYear = year;
                //    getpremiumretailer(month,year);
                }
            }, mYear, mMonth, 0)
            {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                }
            };
            monthDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            monthDatePickerDialog.setTitle("Select month");
            monthDatePickerDialog.show();
        }

//            val datePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener
//            { view, year, monthOfYear, dayOfMonth ->
//                    edittext.setText("" + dayOfMonth + " - " + (monthOfYear+1) + " - " + year)
//            }, year, month, day)
//            datePickerDialog.show()

    }
}
