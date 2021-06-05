package ebase.hkgrox.com.ebase.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
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
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.DownloadCoupon;
import ebase.hkgrox.com.ebase.GiftpointApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.Recylerviewgiftadapter;
import ebase.hkgrox.com.ebase.bean.GIFTS;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Giftstatus extends AppCompatActivity {

        String login = Config.ip_url;
        RecyclerView recyclerview;
        List<GIFTS> lists;
        SearchView searchView;
        Recylerviewgiftadapter adapters;
        Button button;
        List<GIFTS> list;
        private static final int REQUEST_CODE_QR_SCAN = 101;
        private static final int MY_CAMERA_REQUEST_CODE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_giftstatus);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            MUtil.showProgressDialog(this);

            button=(Button)findViewById(R.id.download);
            searchView = (SearchView) findViewById(R.id.search_view);
            searchView.setQueryHint("Refine your Search");
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
            button.setOnClickListener(new View.OnClickListener() {
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
            recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            MUtil.setToolBarNew(this, "Gift Status", true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
            GiftpointApi giftpointApi = retrofit.create(GiftpointApi.class);
            Call<List<GIFTS>> call = giftpointApi.giftstatus();
            call.enqueue(new Callback<List<GIFTS>>() {
                @Override
                public void onResponse(Call<List<GIFTS>> call, Response<List<GIFTS>> response) {
                    //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    GIFTS gits = null;
                    List<GIFTS> list = response.body();
                    ArrayList<GIFTS> arraylist = new ArrayList<GIFTS>();
                    for (int i = 0; i < list.size(); i++) {
                        gits = new GIFTS();
                        String approve = list.get(i).getApprove();
                        String date = list.get(i).getDate();
                        String point = list.get(i).getPoints();
                        String name = list.get(i).getName();
                        String gift = list.get(i).getGift();
                        String mobile = list.get(i).getMobile();

                        gits.setMobile(mobile);
                        gits.setApprove(approve);
                        gits.setDate(date);
                        gits.setPoints(point);
                        gits.setName(name);
                        gits.setGift(gift);

                        arraylist.add(gits);
                    }
                    lists = arraylist;
                   // createExcelVendor(arraylist);

                    adapters = new Recylerviewgiftadapter(Giftstatus.this, arraylist);
                    recyclerview.setAdapter(adapters);
                    MUtil.dismissProgressDialog();

                }

                @Override
                public void onFailure(Call<List<GIFTS>> call, Throwable t) {
                    Toast.makeText(Giftstatus.this, "Failure", Toast.LENGTH_SHORT).show();
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
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Giftstatus.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void onSearch(String query) {

            ArrayList<GIFTS> contactsBeenLocal = new ArrayList<>();

            contactsBeenLocal.addAll(lists);
            final ArrayList<GIFTS> newvender = new ArrayList<>();
            final ArrayList<GIFTS> filteredModelList = new ArrayList<>();
            if (contactsBeenLocal != null) {
                if (query == null || query.length() == 0) {
                    filteredModelList.addAll(contactsBeenLocal);
                } else {

                    query = query.toLowerCase();
                    for (GIFTS model : contactsBeenLocal) {
                        String text = "";
                        if (model.getMobile() != null) {
                            text = model.getMobile().toLowerCase();
                        }
                        String handler = "";
                        if (model.getName() != null) {
                            handler = model.getName().toLowerCase();
                        }


                        if (text.contains(query) || handler.contains(query)) {
                            filteredModelList.add(model);
                        }
                    }
                }
                if (adapters != null) {
                    newvender.addAll(filteredModelList);
                    adapters.filter(filteredModelList);
                }

            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createvender(filteredModelList);
                }
            });


        }
    public void createvender(ArrayList<GIFTS> filteredModelList) {


          /*  Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
            GiftpointApi giftpointApi = retrofit.create(GiftpointApi.class);
            Call<List<GIFTS>> call = giftpointApi.giftstatus();
            call.enqueue(new Callback<List<GIFTS>>() {
                @Override
                public void onResponse(Call<List<GIFTS>> call, Response<List<GIFTS>> response) {
                 //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    GIFTS gits = null;
                    List<GIFTS> list = response.body();
                    ArrayList<GIFTS> arraylist = new ArrayList<GIFTS>();
                    for (int i = 0; i < list.size(); i++) {
                        gits = new GIFTS();
                        String approve = list.get(i).getApprove();
                        String date = list.get(i).getDate();
                        String point = list.get(i).getPoints();
                        String name = list.get(i).getName();
                        String gift = list.get(i).getGift();
                        String mobile = list.get(i).getMobile();

                        gits.setMobile(mobile);
                        gits.setApprove(approve);
                        gits.setDate(date);
                        gits.setPoints(point);
                        gits.setName(name);
                        gits.setGift(gift);

                        arraylist.add(gits);
                    }
                    lists = arraylist;
                    createExcelVendor(arraylist);

                    adapters = new Recylerviewgiftadapter(Giftstatus.this, arraylist);
                    recyclerview.setAdapter(adapters);
                }

                @Override
                public void onFailure(Call<List<GIFTS>> call, Throwable t) {
                    Toast.makeText(Giftstatus.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });*/

            createExcelVendor(filteredModelList);

        }
    private void createExcelVendor(List<GIFTS> day_sales) {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Gift status");

            HSSFRow row = sheet.createRow((short) 0);
            row.createCell((short) 0).setCellValue("NAME");
            row.createCell((short) 1).setCellValue("DATE");
            row.createCell((short) 2).setCellValue("GIFT");
            row.createCell((short) 3).setCellValue("MOBILE");

            row.createCell((short) 4).setCellValue("POINT");
            row.createCell((short) 5).setCellValue("APPROVE");



            int i = 1;
            for (GIFTS user : day_sales) {
                HSSFRow row1 = sheet.createRow((short) i);
                row1.createCell((short) 0).setCellValue(user.getName());
                row1.createCell((short) 1).setCellValue(user.getDate());
                row1.createCell((short) 2).setCellValue(user.getGift());
                row1.createCell((short) 3).setCellValue(user.getMobile());

                row1.createCell((short) 4).setCellValue(user.getPoints());
                row1.createCell((short) 5).setCellValue(user.getApprove());

                i++;
            }


            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "giftstatus.xls");

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
                MUtil.dismissProgressDialog();
                MUtil.showInfoAlertDialog(this, "Excel is downloaded in your phone internal memory as the name of Downloadgiftstatus.xls");
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

        public void createFilenewVendor() {
            MUtil.showProgressDialog(this);
            final List<Vender> arraylist = new ArrayList<>();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
            GiftpointApi giftpointApi = retrofit.create(GiftpointApi.class);
            Call<List<GIFTS>> call = giftpointApi.giftstatus();
            call.enqueue(new Callback<List<GIFTS>>() {
                @Override
                public void onResponse(Call<List<GIFTS>> call, Response<List<GIFTS>> response) {
                    //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    GIFTS gits = null;
                    List<GIFTS> list = response.body();
                    ArrayList<GIFTS> arraylist = new ArrayList<GIFTS>();
                    for (int i = 0; i < list.size(); i++) {
                        gits = new GIFTS();
                        String approve = list.get(i).getApprove();
                        String date = list.get(i).getDate();
                        String point = list.get(i).getPoints();
                        String name = list.get(i).getName();
                        String gift = list.get(i).getGift();
                        String mobile = list.get(i).getMobile();

                        gits.setMobile(mobile);
                        gits.setApprove(approve);
                        gits.setDate(date);
                        gits.setPoints(point);
                        gits.setName(name);
                        gits.setGift(gift);

                        arraylist.add(gits);
                    }
                    lists = arraylist;
                    createExcelVendor(arraylist);

                    adapters = new Recylerviewgiftadapter(Giftstatus.this, arraylist);
                    recyclerview.setAdapter(adapters);
                }

                @Override
                public void onFailure(Call<List<GIFTS>> call, Throwable t) {
                    Toast.makeText(Giftstatus.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }
        }
