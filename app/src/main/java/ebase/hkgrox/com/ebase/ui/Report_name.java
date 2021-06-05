package ebase.hkgrox.com.ebase.ui;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Apireport;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.DownloadCoupon;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.dailyreportbyname;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Report_name extends AppCompatActivity {

    Config config;
    String url = config.ip_url;
    EditText name;
    List<Vender> arrylist;
    RecyclerView recyclerView;
    SearchView searchView;
    dailyreportbyname adapter;
    String login_url2 =config.ip_url;
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Today Report",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        download=(Button)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                    //  createFile();
                    //  Toast.makeText(DownloadCoupon.this,"",Toast.LENGTH_SHORT).show();
                } else {

                    createFilenewVendor();
                }

            }
        });
        //name = (EditText) findViewById(R.id.name);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Refine your search");
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

        MUtil.showProgressDialog(Report_name.this);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                Apireport apireport = retrofit.create(Apireport.class);
                Call<List<Vender>> call = apireport.byname(MUtil.getTodayDate().toString());
                call.enqueue(new Callback<List<Vender>>() {
                    @Override
                    public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                        List<Vender> list=response.body();
                        List<Vender> arraylist=new ArrayList<Vender>();

                        Vender vender=null;
                        for(int i=0;i<list.size();i++) {
                            //  arraylist[i]=list.get(i).getParty()+":"+list.get(i).getPhone();
                            vender = new Vender();
                            String party = list.get(i).getParty();
                            String retailer = list.get(i).getRetailer();
                            String segment = list.get(i).getSegment();
                            String cperson=list.get(i).getContact_person();
                            String phone = list.get(i).getPhone();
                            String potential = list.get(i).getPotentail();
                            String order=list.get(i).getE_order();
                           // String status=list.get(i).getStatus();
                            String address = list.get(i).getAddress();
                            String area = list.get(i).getArea();
                            String city = list.get(i).getCity();
                            String pincode = list.get(i).getPincode();
                            String remarks=list.get(i).getRemark();
                            String mobile2=list.get(i).getMobile2();
                            String orderdetail=list.get(i).getOrderdetail();
                            String checkin=list.get(i).getCheckin();
                            String checkout=list.get(i).getCheckout();
                            String email=list.get(i).getEmail();
                            String time=list.get(i).getTime();
                            String checkin_time=list.get(i).getCheckintime();
                            String ename=list.get(i).getE_name();
                            String ephone=list.get(i).getE_phone();
                            String checkin_date=list.get(i).getCheckin_date();
                            String checkout_date=list.get(i).getDate();

                            vender.setE_name(ename);
                            vender.setE_phone(ephone);
                            vender.setCheckin_date(checkin_date);
                            vender.setDate(checkout_date);
                            vender.setCheckintime(checkin_time);
                            vender.setParty(party);
                            vender.setRetailer(retailer);
                            vender.setSegment(segment);
                            vender.setMobile2(mobile2);
                            vender.setOrderdetail(orderdetail);
                            vender.setCheckin(checkin);
                            vender.setCheckout(checkout);
                            vender.setContact_person(cperson);
                            vender.setPincode(pincode);
                            vender.setCity(city);
                            vender.setArea(area);
                            vender.setAddress(address);
                            vender.setPotentail(potential);
                            vender.setE_order(order);
                            vender.setEmail(email);
                            vender.setTime(time);

                            //if(status==null){
                            //    vender.setStatus("open");
                           // }else {
                           //     vender.setStatus(status);
                          //  }
                            vender.setRemark(remarks);
                            vender.setPhone(phone);
                            arraylist.add(vender);

                        }
                        arrylist=arraylist;
                        adapter=new dailyreportbyname(Report_name.this,arraylist);
                        recyclerView.setAdapter(adapter);
                        MUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<List<Vender>> call, Throwable t) {
                        Toast.makeText(Report_name.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Report_name.this)
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
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && cameraAccepted) {
                        createFilenewVendor();
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

    public void createFilenewVendor()
    {
        MUtil.showProgressDialog(this);
        final List<Vender> arraylist = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apireport = retrofit.create(Apireport.class);
        Call<List<Vender>> call = apireport.byname(MUtil.getTodayDate().toString());
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                List<Vender> list = response.body();

                // List<USER> day_sales = new ArrayList<USER>();


                Vender vender=null;
                String type = "";

                for (int i = 0; i < list.size(); i++) {

                    vender = new Vender();
                    String party = list.get(i).getParty();
                    String retailer = list.get(i).getRetailer();
                    String segment = list.get(i).getSegment();
                    String cperson=list.get(i).getContact_person();
                    String phone = list.get(i).getPhone();
                    String potential = list.get(i).getPotentail();
                    String order=list.get(i).getE_order();
                    String status=list.get(i).getStatus();
                    String address = list.get(i).getAddress();
                    String area = list.get(i).getArea();
                    String city = list.get(i).getCity();
                    String pincode = list.get(i).getPincode();
                    String remarks=list.get(i).getRemark();
                    String mobile2=list.get(i).getMobile2();
                    String orderdetail=list.get(i).getOrderdetail();
                    String checkin=list.get(i).getCheckin();
                    String checkout=list.get(i).getCheckout();
                    String email=list.get(i).getEmail();
                    String time=list.get(i).getTime();
                    String checkin_time=list.get(i).getCheckintime();
                    String ename=list.get(i).getE_name();
                    String ephone=list.get(i).getE_phone();
                    String checkin_date=list.get(i).getCheckin_date();
                    String checkout_date=list.get(i).getDate();

                    vender.setE_name(ename);
                    vender.setE_phone(ephone);
                    vender.setCheckin_date(checkin_date);
                    vender.setDate(checkout_date);
                    vender.setCheckintime(checkin_time);
                    vender.setParty(party);
                    vender.setRetailer(retailer);
                    vender.setSegment(segment);
                    vender.setMobile2(mobile2);
                    vender.setOrderdetail(orderdetail);
                    vender.setCheckin(checkin);
                    vender.setCheckout(checkout);
                    vender.setContact_person(cperson);
                    vender.setPincode(pincode);
                    vender.setCity(city);
                    vender.setArea(area);
                    vender.setAddress(address);
                    vender.setPotentail(potential);
                    vender.setE_order(order);
                    vender.setEmail(email);
                    vender.setTime(time);


                    vender.setRemark(remarks);
                    vender.setPhone(phone);
                    arraylist.add(vender);
                }


                createExcelVendor(arraylist);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                MUtil.dismissProgressDialog();
            }
        });
    }

    public void onSearch(String query) {

        ArrayList<Vender> contactsBeenLocal = new ArrayList<>();
        ArrayList<Vender> newvender=new ArrayList<>();
        contactsBeenLocal.addAll(arrylist);
        final ArrayList<Vender> filteredModelList = new ArrayList<>();
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
                    if (model.getE_phone() != null) {
                        handler = model.getE_phone().toLowerCase();
                    }
                    String vendor_phone = "";
                    if (model.getPhone() != null) {
                        vendor_phone = model.getPhone().toLowerCase();
                    }


                    if (text.contains(query) || handler.contains(query)||vendor_phone.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
            if (adapter != null) {
                newvender.addAll(filteredModelList);
                adapter.filter(filteredModelList);
            }

        }
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFileVendor(filteredModelList);
            }
        });
    }

    private void createFileVendor(ArrayList<Vender> filteredModelList) {

     /*   MUtil.showProgressDialog(this);
        final List<Vender> arraylist = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Apireport apireport = retrofit.create(Apireport.class);
        Call<List<Vender>> call = apireport.byname(MUtil.getTodayDate().toString());
        call.enqueue(new Callback<List<Vender>>() {
            @Override
            public void onResponse(Call<List<Vender>> call, Response<List<Vender>> response) {
                List<Vender> list = response.body();

                // List<USER> day_sales = new ArrayList<USER>();


                Vender vender=null;
                String type = "";

                for (int i = 0; i < list.size(); i++) {

                    vender = new Vender();
                    String party = list.get(i).getParty();
                    String retailer = list.get(i).getRetailer();
                    String segment = list.get(i).getSegment();
                    String cperson=list.get(i).getContact_person();
                    String phone = list.get(i).getPhone();
                    String potential = list.get(i).getPotentail();
                    String order=list.get(i).getE_order();
                    String status=list.get(i).getStatus();
                    String address = list.get(i).getAddress();
                    String area = list.get(i).getArea();
                    String city = list.get(i).getCity();
                    String pincode = list.get(i).getPincode();
                    String remarks=list.get(i).getRemark();
                    String mobile2=list.get(i).getMobile2();
                    String orderdetail=list.get(i).getOrderdetail();
                    String checkin=list.get(i).getCheckin();
                    String checkout=list.get(i).getCheckout();
                    String email=list.get(i).getEmail();
                    String time=list.get(i).getTime();
                    String checkin_time=list.get(i).getCheckintime();
                    String ename=list.get(i).getE_name();
                    String ephone=list.get(i).getE_phone();
                    String checkin_date=list.get(i).getCheckin_date();
                    String checkout_date=list.get(i).getDate();

                    vender.setE_name(ename);
                    vender.setE_phone(ephone);
                    vender.setCheckin_date(checkin_date);
                    vender.setDate(checkout_date);
                    vender.setCheckintime(checkin_time);
                    vender.setParty(party);
                    vender.setRetailer(retailer);
                    vender.setSegment(segment);
                    vender.setMobile2(mobile2);
                    vender.setOrderdetail(orderdetail);
                    vender.setCheckin(checkin);
                    vender.setCheckout(checkout);
                    vender.setContact_person(cperson);
                    vender.setPincode(pincode);
                    vender.setCity(city);
                    vender.setArea(area);
                    vender.setAddress(address);
                    vender.setPotentail(potential);
                    vender.setE_order(order);
                    vender.setEmail(email);
                    vender.setTime(time);

                    if(status==null){
                        vender.setStatus("open");
                    }else {
                        vender.setStatus(status);
                    }
                    vender.setRemark(remarks);
                    vender.setPhone(phone);
                    arraylist.add(vender);
                    }


                createExcelVendor(arraylist);
                MUtil.dismissProgressDialog();
            }


            @Override
            public void onFailure(Call<List<Vender>> call, Throwable t) {
                MUtil.dismissProgressDialog();
            }
        });*/
        createExcelVendor(filteredModelList);
    }

    private void createExcelVendor(List<Vender> day_sales) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("coupon");

        HSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("PARTY");
        row.createCell((short) 1).setCellValue("RETAILER");
        row.createCell((short) 2).setCellValue("EMPLOYEE_NAME");
        row.createCell((short) 3).setCellValue("EMPLOYEE_PHONE");

        row.createCell((short) 4).setCellValue("CHECK_IN");
        row.createCell((short) 5).setCellValue("CHECK_OUT");
        row.createCell((short) 6).setCellValue("CHECKIN_DATE");
        row.createCell((short) 7).setCellValue("CHECKOUT_DATE");
        row.createCell((short) 8).setCellValue("CHECKIN_TIME");
        row.createCell((short) 9).setCellValue("CHECKOUT_TIME");
        row.createCell((short) 10).setCellValue("SEGMENT");
        row.createCell((short) 11).setCellValue("CONTACT_PERSON");
        row.createCell((short) 12).setCellValue("PHONE");
        row.createCell((short) 13).setCellValue("MOBILE2");
        row.createCell((short) 14).setCellValue("EMAIL");
        row.createCell((short) 15).setCellValue("POTENTIAL_PM");
        row.createCell((short) 16).setCellValue("TODAY_ORDER");
        row.createCell((short) 17).setCellValue("ORDER_DETAIL");
        row.createCell((short) 18).setCellValue("STATUS");
        row.createCell((short) 19).setCellValue("ADDRESS");
        row.createCell((short) 20).setCellValue("AREA");
        row.createCell((short) 21).setCellValue("CITY");
        row.createCell((short) 22).setCellValue("PINCODE");
        row.createCell((short) 23).setCellValue("REMARKS");





        int i = 1;
        for (Vender user : day_sales) {
            HSSFRow row1 = sheet.createRow((short) i);
            row1.createCell((short) 0).setCellValue(user.getParty());
            row1.createCell((short) 1).setCellValue(user.getRetailer());
            row1.createCell((short) 2).setCellValue(user.getE_name());
            row1.createCell((short) 3).setCellValue(user.getE_phone());

            row1.createCell((short) 4).setCellValue(user.getCheckin());
            row1.createCell((short) 5).setCellValue(user.getCheckout());
            row1.createCell((short) 6).setCellValue(user.getCheckin_date());
            row1.createCell((short) 7).setCellValue(user.getDate());
            row1.createCell((short) 8).setCellValue(user.getCheckintime());
            row1.createCell((short) 9).setCellValue(user.getTime());
            row1.createCell((short) 10).setCellValue(user.getSegment());
            row1.createCell((short) 11).setCellValue(user.getContact_person());
            row1.createCell((short) 12).setCellValue(user.getPhone());
            row1.createCell((short) 13).setCellValue(user.getMobile2());
            row1.createCell((short) 14).setCellValue(user.getEmail());
            row1.createCell((short) 15).setCellValue(user.getPotentail());
            row1.createCell((short) 16).setCellValue(user.getE_order());
            row1.createCell((short) 17).setCellValue(user.getOrderdetail());
            row1.createCell((short) 18).setCellValue(user.getStatus());
            row1.createCell((short) 19).setCellValue(user.getAddress());
            row1.createCell((short) 20).setCellValue(user.getArea());
            row1.createCell((short) 21).setCellValue(user.getCity());
            row1.createCell((short) 22).setCellValue(user.getPincode());
            row1.createCell((short) 23).setCellValue(user.getRemark());

            i++;
        }


        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "todayreport.xls");

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

            MUtil.showInfoAlertDialog(this, "Excel is downloaded in your phone internal memory as the name of Downloadtodayreport.xls");
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
}
