package ebase.hkgrox.com.ebase;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import ebase.hkgrox.com.ebase.bean.COUPON;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DownloadCoupon extends AppCompatActivity {

    private EditText edtfrom, edtto;
    private Button btndownload;
    Config config;
    String login_url2 = config.ip_url;
    //private String login_url2 = "http://192.168.0.103";
    String page;
    String from;
    String to;
    public  static final int RequestPermissionCode  = 1 ;
    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_coupon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String page = getIntent().getExtras().getString("PAGE");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtfrom = (EditText) findViewById(R.id.edtfrom);
        edtto = (EditText) findViewById(R.id.edtto);
        btndownload = (AppCompatButton) findViewById(R.id.btndownload);
        from = edtfrom.getText().toString();
        to = edtto.getText().toString();

        if (page.equalsIgnoreCase("download")) {
            MUtil.setToolBarNew(this, "Download Coupons Detail", true);
        }

        btndownload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!checkPermission()) {
                    requestPermission();
                  //  createFile();
                 //  Toast.makeText(DownloadCoupon.this,"",Toast.LENGTH_SHORT).show();
                } else {

                    createFile();
                }
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
                        createFile();
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

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                //  dialog.dismiss();
                createFile();
            }
            // Toast.makeText(DownloadCoupon.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(DownloadCoupon.this);
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
    }

    private void EnableRuntimePermissionToAccessCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DownloadCoupon.this,
                WRITE_EXTERNAL_STORAGE))
        {

            // Printing toast message after enabling runtime permission.
//

        } else {
            //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(DownloadCoupon.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);

        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DownloadCoupon.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void createFile() {
        from = edtfrom.getText().toString();
        to = edtto.getText().toString();
        int s=Integer.parseInt(from);
        int e=Integer.parseInt(to);
        if((e-s)>40000){
            Toast.makeText(DownloadCoupon.this,"Maximum Record Exceeds",Toast.LENGTH_SHORT).show();
        }
        else {
            MUtil.showProgressDialog(this);
            final List<COUPON> day_sales = new ArrayList<COUPON>();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
            ApiService service = retrofit.create(ApiService.class);
            Call<List<COUPON>> call = service.listRespo(from, to);
            call.enqueue(new Callback<List<COUPON>>() {
                @Override
                public void onResponse(Call<List<COUPON>> call, Response<List<COUPON>> response) {

                    List<COUPON> list = response.body();
                    COUPON cou = null;

                    for (int i = 0; i < list.size(); i++) {
                      //  Toast.makeText(DownloadCoupon.this,"Download successful"+list.get(0+).getAvailed_by_mobile(),Toast.LENGTH_SHORT).show();

                        cou = new COUPON();
                        String sno = list.get(i).getSno();
                        String availed_by_email = list.get(i).getAvailed_by_email();
                        String availed_by_mobile = list.get(i).getAvailed_by_mobile();
                        String availed_by_name = list.get(i).getAvailed_by_name();
                        String coupon = list.get(i).getCoupon();
                        String date_availed = list.get(i).getDate_availed();
                        String date_upload = list.get(i).getDate_upload();
                        String is_availed = list.get(i).getIs_availed();
                        String points = list.get(i).getPoints();
                        String valid_til = list.get(i).getValid_til();

                        cou.setSno(sno);
                        cou.setAvailed_by_email(availed_by_email);
                        cou.setAvailed_by_mobile(availed_by_mobile);
                        cou.setAvailed_by_name(availed_by_name);
                        cou.setCoupon(coupon);
                        cou.setDate_availed(date_availed);
                        cou.setDate_upload(date_upload);
                        cou.setIs_availed(is_availed);
                        cou.setPoints(points);
                        cou.setValid_til(valid_til);

                        day_sales.add(cou);
                    }
                    try {
                        createExcel(day_sales);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<List<COUPON>> call, Throwable t) {
                Toast.makeText(DownloadCoupon.this,""+t,Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void createExcel(List<COUPON> day_sales) {
      //  Toast.makeText(DownloadCoupon.this,"Download",Toast.LENGTH_SHORT).show();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("coupons");

        HSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("SNO");
        row.createCell((short) 1).setCellValue("COUPON");
        row.createCell((short) 2).setCellValue("IS_AVAILED");
        row.createCell((short) 3).setCellValue("VALID_TILL");
        row.createCell((short) 4).setCellValue("POINTS");

        row.createCell((short) 5).setCellValue("Date_Availed");
        row.createCell((short) 6).setCellValue("Availed_by_name");
        row.createCell((short) 7).setCellValue("Availed_by_mobile");
        row.createCell((short) 8).setCellValue("Availed_by_email");



        int count = 1;

        int i = 0;
        for (COUPON coupon : day_sales) {
            if(i<0){
                return;
            }

            //  if(day_sales.get(i).getIs_availed() !=null && day_sales.get(i).getIs_availed().equalsIgnoreCase("YES")) {
            HSSFRow row1 = sheet.createRow(count);
            row1.createCell((short) 0).setCellValue(coupon.getSno());
            row1.createCell((short) 1).setCellValue(coupon.getCoupon());
            row1.createCell((short) 2).setCellValue(coupon.getIs_availed());
            row1.createCell((short) 3).setCellValue(coupon.getValid_til());
            row1.createCell((short) 4).setCellValue(coupon.getPoints());

            row1.createCell((short) 5).setCellValue(coupon.getDate_availed());
            row1.createCell((short) 6).setCellValue(coupon.getAvailed_by_name());
            row1.createCell((short) 7).setCellValue(coupon.getAvailed_by_mobile());
            row1.createCell((short) 8).setCellValue(coupon.getAvailed_by_email());

            count++;

            // }

            i++;
        }

        MUtil.dismissProgressDialog();
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "coupon.xls");

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

            MUtil.showInfoAlertDialog(this, "Excel is downloaded in your phone internal memory as the name of Downloadcoupon.xls");
        } catch (Exception e) {
            Toast.makeText(DownloadCoupon.this,"Download"+e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}
