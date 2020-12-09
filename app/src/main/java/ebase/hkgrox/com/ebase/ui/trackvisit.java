package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import ebase.hkgrox.com.ebase.Api.Apireport;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.trackvisitadapter;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class trackvisit extends AppCompatActivity {

    Button startdate, enddate, submit;
    String url = Config.ip_url;
    String endDate, startDate;
    RecyclerView recyclerview;
    trackvisitadapter adapter;
    TextView textview;
    List<Vender> arrylist;
    USER user;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackvisit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Track Visit",true);
        startdate = (Button) findViewById(R.id.from);
        enddate = (Button) findViewById(R.id.to);
        submit = (Button) findViewById(R.id.submit);
        user = (USER) getIntent().getExtras().getSerializable("DATA");
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Refine your search");
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        textview=(TextView)findViewById(R.id.textView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    onSearch(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    onSearch(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report();
            }
        }
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePicker();
            }
        });
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
       if(month>0){
        calold.set((year ), (cal.get(Calendar.MONTH)-12), 1);
         }else{
        calold.set((year-1 ), (11), cal.get(Calendar.DAY_OF_MONTH));
         }
        datePicker.setMinDate(calold);

        datePicker.setCancelable(true);

        datePicker.show(this.getFragmentManager(), "Data Picker");
    }

    private void endDatePicker() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
       /* DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                R.style.AppTheme, pickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));*/


        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                startpickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );


        int year = cal.get(Calendar.YEAR);
        cal.set((year ), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setMaxDate(cal);

        Calendar calold = Calendar.getInstance(TimeZone.getDefault());
        int month = cal.get(Calendar.MONTH);
        if(month>0){
            calold.set((year ), (cal.get(Calendar.MONTH)-12), 1);
        }else{
            calold.set((year-1 ), (11), cal.get(Calendar.DAY_OF_MONTH));
        }
        datePicker.setMinDate(calold);

        datePicker.setCancelable(true);

        datePicker.show(this.getFragmentManager(), "Data Picker");
    }
    int year ;
    int month ;
    int day ;

    int eyear;
    int emonth;
    int eday;
    private DatePickerDialog.OnDateSetListener startpickerListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            eyear=year;
            emonth=monthOfYear;
            eday=dayOfMonth;

            endDate = new StringBuilder().append(eyear)
                    .append("-").append(emonth+1).append("-").append(eday).toString().trim();
            enddate.setText(endDate);
        }
    };

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



            // monthString = new DateFormatSymbols().getMonths()[month].substring(0,3);
            startDate = new StringBuilder().append(year)
                    .append("-").append(month+1).append("-").append(day).toString().trim();


            startdate.setText(startDate);

        }
    };


    public void report()
    {
        MUtil.showProgressDialog(trackvisit.this);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apireport=retrofit.create(Apireport.class);
        Call<List<Vender>> call=apireport.gettrackvisit(startDate,endDate,user.getMOBILE());
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {


                List<Vender> list=response.body();

                List<Vender> arraylist=new ArrayList<Vender>();
                Vender vender=null;
                for (int i=0;i<list.size();i++)
                {
                   vender=new Vender();
                    String phone=list.get(i).getPhone();
                    String checkin=list.get(i).getCheckin();
                    String checkout=list.get(i).getCheckout();
                    String checkintime=list.get(i).getCheckintime();
                    String checkouttime=list.get(i).getTime();
                    String checkindate=list.get(i).getCheckin_date();
                    String checkoutdate=list.get(i).getDate();
                    String party=list.get(i).getParty();
                    String contact_person=list.get(i).getContact_person();
                    String mobile2=list.get(i).getMobile2();
                    String orderdetail=list.get(i).getOrderdetail();
                    String euro_order=list.get(i).getE_order();
                    String remarks=list.get(i).getRemark();
                   // String status=list.get(i).getStatus();
                    String segment=list.get(i).getSegment();
                    String category=list.get(i).getRetailer();
                    String state=list.get(i).getState();
                    String area=list.get(i).getArea();
                    String city=list.get(i).getCity();
                    String email=list.get(i).getEmail();
                    String address=list.get(i).getAddress();
                    String potential_pm=list.get(i).getPotential();

                    vender.setAddress(address);
                    vender.setParty(party);
                    vender.setPhone(phone);
                    vender.setCheckin(checkin);
                    vender.setCheckout(checkout);
                    vender.setCheckintime(checkintime);
                    vender.setTime(checkouttime);
                    vender.setCheckin_date(checkindate);
                    vender.setDate(checkoutdate);
                    vender.setContact_person(contact_person);
                    vender.setMobile2(mobile2);
                    vender.setOrderdetail(orderdetail);
                    vender.setE_order(euro_order);
                    vender.setRemark(remarks);
                    vender.setState(state);
                    vender.setSegment(segment);
                    vender.setRetailer(category);
                    //vender.setStatus(status);
                    vender.setArea(area);
                    vender.setCity(city);
                    vender.setEmail(email);
                    vender.setPotentail(potential_pm);

                    arraylist.add(vender);




                }
                arrylist=arraylist;
                //Toast.makeText(trackvisit.this, ""+area, Toast.LENGTH_SHORT).show();
                adapter=new trackvisitadapter(trackvisit.this,arraylist);
                recyclerview.setAdapter(adapter);
                MUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                Toast.makeText(trackvisit.this, "error", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void onSearch(String query) {

        ArrayList<Vender> contactsBeenLocal = new ArrayList<>();

        contactsBeenLocal.addAll(arrylist);
        ArrayList<Vender> filteredModelList = new ArrayList<>();
        if (contactsBeenLocal != null) {
            if (query == null || query.length() == 0) {
                filteredModelList.addAll(contactsBeenLocal);
            } else {

                query = query.toLowerCase();
                for (Vender model : contactsBeenLocal) {
                    String text = "";
                    if (model.getParty() != null) {
                        text = model.getParty().toLowerCase();
                    }
                    String handler = "";
                    if (model.getPhone() != null) {
                        handler = model.getPhone().toLowerCase();
                    }
                    String status = "";
                    if (model.getStatus() != null) {
                        status = model.getStatus().toLowerCase();
                    }

                    if (text.contains(query) || handler.contains(query)||status.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
            if (adapter != null) {
                adapter.filter(filteredModelList);
            }

        }
    }

}
