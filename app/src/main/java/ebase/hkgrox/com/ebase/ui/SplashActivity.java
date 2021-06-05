package ebase.hkgrox.com.ebase.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.widget.TextView;

import java.util.ArrayList;

import ebase.hkgrox.com.ebase.R;


public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSIONREQUEST = 11;
    private ArrayList<String> permissionList;
    private String message;
    private TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvAppVersion = (TextView) findViewById(R.id.tv_appVersion);
        tvAppVersion.setText("V : "+getAppVersion(this));



     /* <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS" />
        <uses-permission android:name="android.permission.WRITE_CONTACTS" />
        <uses-permission android:name="android.permission.READ_CONTACTS" />
        <uses-permission android:name="android.permission.MANAGE_ACCOUNTS" />
        <uses-permission android:name="android.permission.WRITE_SYNC_SETTINGS" />
        <uses-permission android:name="android.permission.READ_SYNC_SETTINGS" />
        <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
        <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
        <uses-permission android:name="android.permission.READ_CALENDAR" />
        <uses-permission android:name="android.permission.WRITE_CALENDAR" />*/



//        permissionList = new ArrayList<>();
     

//        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
       


     //  message = "Without this permission the app is unable to proceed Are you sure you want to deny this permission?";
       // setPermission(permissionList.get(ZERO), true);
        //PermissionsHelper.show(this);

        Intent intent = new Intent(SplashActivity.this, FirstPage.class);
        intent.putExtra("DATA", "");
        startActivity(intent);
    }

    private final int SLEEP_TIME = 1000;

    private class StartIntent extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            /*if (RootUtil.isDeviceRooted()) {
                message = getResources().getString(R.string.rooted_device_msg);
                deleteAllData();
                closeApplicationAlertForRootedDevice(message);
            } else {
                if (MUtil.isMobileVAlid(SplashActivity.this)) {
                    Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else {
                Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                startActivity(intent);
                 }*/

            Intent intent = new Intent(SplashActivity.this, FirstPage.class);
            intent.putExtra("DATA", "");
            startActivity(intent);


                finish();
            }

        }
   // }

    private void deleteAllData() {

        // TO DO delete all local data
    }


    private final int ZERO = 0;

    private void setPermission(String permission, boolean shouldShowDialog) {


        if (ActivityCompat.checkSelfPermission(SplashActivity.this,
                String.valueOf(permission)) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{String.valueOf(permission)}, PERMISSIONREQUEST);
        } else {
            permissionList.remove(ZERO);
            if (permissionList.size() > ZERO) {
                setPermission(permissionList.get(ZERO), true);
            } else {
                new StartIntent().execute();
            }
        }
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionList.size() > ZERO) {
            if (grantResults.length > ZERO
                    && grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {


                permissionList.remove(ZERO);
                if (permissionList.size() > ZERO) {
                    setPermission(permissionList.get(ZERO), true);
                } else {
                    new StartIntent().execute();
                }

            } else {
                if (permissionList.size() > ZERO) {

                    boolean showRationale = shouldShowRequestPermissionRationale(permissionList.get(ZERO));
                    if (!showRationale) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setTitle("PERMISSION NEDDED");
                        message = "Without this permission the app is unable to proceed Are you sure you want to deny this permission?";
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Intent i = new Intent();
                                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + SplashActivity.this.getPackageName()));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                SplashActivity.this.startActivityForResult(i, PERMISSIONREQUEST);
                            }
                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setTitle("PERMISSION");
                        builder.setMessage(message);
                        builder.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                        setPermission(permissionList.get(ZERO), false);
                                if (permissionList.size() > ZERO) {
                                    setPermission(permissionList.get(ZERO), true);
                                } else {
                                    new StartIntent().execute();
                                }
                            }
                        });
                        builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }else {
                    new StartIntent().execute();
                }

            }
        }
    }



    public void closeApplicationAlertForRootedDevice(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PERMISSIONREQUEST) {
                if (permissionList.size() > 0) {
                    setPermission(permissionList.get(ZERO), true);
                }
            }

    }

    public static String getAppVersion(Context context) {
        String appVersion = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String verCode = pInfo.versionName;
             appVersion="Ver P: "+String.valueOf(verCode);
            appVersion = verCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }



}


