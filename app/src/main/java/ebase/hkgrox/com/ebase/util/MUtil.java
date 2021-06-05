package ebase.hkgrox.com.ebase.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ebase.hkgrox.com.ebase.R;


public class MUtil {
    private static final String NETWORK_ERROR_MESSAGE = "Connection Not Available try again";

    private static final String TAG = "MUtil";
    private static final String ENCRYPTION_KEY = "P1201GDSHDJ1DSJF";
    public static final int SHARE_IMAGE = 000010;
    public static final String UID ="uid" ;
    public static final String LOGIN = "login";
    public static final String NAME = "name";
    public static final String EMAIL ="email" ;
    public static final String IMAGE_URL = "image-url";
    private static final String IS_FIRST_TIME = "first-time";
  public static final String EMAIL_ID = "sales@euroils.com";
  // public static final String EMAIL_ID = "amitrana0402@gmail.com";
    private static Gson gsonObject;
    private static ProgressDialog pd;


    public static DatabaseReference getFirebaseReference(Context activity) {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static void hideKeyBoard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    public static void showKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }


    public static String getDateFrom_ddMMyyyy_To_ddMMMyyyy(String stringDate) {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String reformattedStr = null;
        try {

            reformattedStr = myFormat.format(fromUser.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public static String getDateInMMMddyyyyFormat(String stringDate) {

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMM dd,yyyy");
        String reformattedStr = null;
        try {

            reformattedStr = myFormat.format(fromUser.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public static String getDateInIsoFormat(String stringDate) {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reformattedStr = null;
        try {

            reformattedStr = myFormat.format(fromUser.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }
    public static void showSnakbar(Context context, String message) {
        final Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public static boolean isNetworkAvailable(Context mycontext) {
        NetworkInfo activeNetworkInfo = null;
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return result;
    }

    public static boolean readBoolean(Context context, final String key, final boolean defaultValue) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }


    public static String readString(Context context, final String key,
                                    String defaultValue) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        return pref.getString(key, defaultValue);
    }

    public static void writeString(Context context, final String key, final String value) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void writeBoolean(Context context, final String key, final boolean value) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


   /* public static void setToolBar(Context context, String title, final ActionBar abar) {
        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
    }

    public static void setToolBarNoNavigation(Context context, String title, final ActionBar abar) {
        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);


    }*/


    public static void showSoftKeyboard(Context context, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    public static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentTimeStampInMiliSecond() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }


    public static Gson getGsonObject() {
        if (gsonObject == null) {
            gsonObject = new Gson();
        }
        return gsonObject;
    }

  /*  private static DeviceDetails details = null;

    public static DeviceDetails getDeviceDetails(Context context) {
        details = new DeviceDetails();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        details.setAndroidVersion(android.os.Build.VERSION.RELEASE);
        details.setDeviceId(telephonyManager.getDeviceId());
        details.setDeviceModel(android.os.Build.MODEL);
        details.setSimId(telephonyManager.getSimSerialNumber());
        details.setDeviceMake(android.os.Build.MANUFACTURER);
        return details;
    }*/


    public static String getSHA1(String input) {
        String hexStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return hexStr;
    }


    private static String versionName = "";

    public static String getApplicationVersion(Context context) {

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }


    public static String getRandomID() {
        String id = getCurrentTimeStampInMiliSecond() + String.valueOf(Math.round(Math.random() * 1000));
        return id;
    }


    public static String getStringOfJson(Object object) {
        if (object == null) {
            return "";
        }
        Gson gson = getGsonObject();
        return gson.toJson(object);
    }


    public static String numberToRuppeFormat(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String rupees = formatter.format(amount);

        return rupees;
    }


    public static void killApp(Activity activity) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void killAppNew(Activity activity) {
        activity.finishAffinity();
    }


    public static void makeACall(String number, Context context) {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        // phoneIntent.setData(Uri.parse("tel:91-000-000-0000"));
        phoneIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            context.startActivity(phoneIntent);
            return;
        } else {
            showSnakbar(context, "App does not have 'Call' permission");
        }

    }

    public static void dialANo(String number, Context context) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        // phoneIntent.setData(Uri.parse("tel:91-000-000-0000"));
        phoneIntent.setData(Uri.parse("tel:" + number));
        context.startActivity(phoneIntent);

    }


  /*  public static void setToolBarCenter(Context context, String title, ActionBar abar) {

        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);


    }
*/

    public static void showInfoAlertDialog(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static boolean checkPermissionGranted(Activity activity, String permission){
        if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return  false;
        }

    }
    public static void requestPermission(Activity activity, String permission, String message, int PERMISSIONS_REQUEST) {


        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED)

        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {

                MUtil.showPermissionAlertDialog(activity, message);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        PERMISSIONS_REQUEST);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
    public static void showPermissionAlertDialog(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startInstalledAppDetailsActivity((Activity) context);
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public static void closeApplicationAlert(String message, final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setMessage(message)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity) context).finishAffinity();
                            }
                        });


        alertDialogBuilder
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static void showProgressDialog(Context context) {
        if (pd != null && pd.isShowing())
            pd.dismiss();
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait..");
        pd.setCancelable(true);
        pd.show();
    }

    public static void dismissProgressDialog() {
        if (pd != null && pd.isShowing())
            pd.dismiss();
    }


    /*public static boolean isMobileVAlid(SplashActivity s) {
        return s.matches("[789]\\d{9}");
    }*/


    public static ComponentName activityInForeground(Context context) {
        ComponentName active = null;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> services = activityManager
                    .getRunningTasks(Integer.MAX_VALUE);
            active = services.get(0).topActivity;

        } catch (Exception e) {
            active = null;
        }

        return active;
    }

    public static void showConnectonFailedMessage(Activity Activity) {
        MUtil.showSnakbar(Activity, NETWORK_ERROR_MESSAGE);
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static String getEncryptionKey() {
        return ENCRYPTION_KEY;
    }

    public static void shareImage(Context context, String path, String message) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(path);
        sharingIntent.setType("image/jpeg");

        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        ((Activity) context).startActivityForResult(Intent.createChooser(sharingIntent, "Share image using"), SHARE_IMAGE);
    }

    public static String getImageDrawing(Activity Activity, LinearLayout llParent) {
        String fileName = Environment.getExternalStorageDirectory() + File.separator + "shareImage.jpeg";
        File file = new File(fileName);

        llParent.setDrawingCacheEnabled(true);
        llParent.buildDrawingCache();
        Bitmap bitmap = llParent.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static boolean deleteFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
                return true;
            }
        }
        return false;
    }

    public static void rateAppOnPlaystore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName())));
        }

    }

    public static void shareApp(Context context) {
        String shareBody = "https://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share App via"));

    }

    public static String convertToProperFormat(String dated) {
        String datenew = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date systemDate = c.getTime();
        String CurrentDate = df.format(systemDate);
        Date Date1 = null;
        Date Date2 = null;
        Long diffInSec = 0l;
        try {
            Date1 = df.parse(dated);
            Date2 = df.parse(CurrentDate);
            diffInSec = Date2.getTime() - Date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long secondsBetweenDates = TimeUnit.MILLISECONDS.toSeconds(diffInSec);
        if (secondsBetweenDates >= 0) {
            if (secondsBetweenDates > 60) {
                secondsBetweenDates = secondsBetweenDates / 60;
                if (secondsBetweenDates > 60) {
                    //if time between dates is more than a minute so it can be in hour
                    secondsBetweenDates = secondsBetweenDates / 60;
                    if (secondsBetweenDates > 24) {
                        //if time between dates is now more than 24 so it can be in days
                        secondsBetweenDates = secondsBetweenDates / 24;
                        if (secondsBetweenDates >= 365) {
                            secondsBetweenDates = secondsBetweenDates / 365;
                            //this much week ago
                            if (secondsBetweenDates > 1) {
                                datenew = (long) secondsBetweenDates + " years ago";
                            } else {
                                datenew = (long) secondsBetweenDates + " year ago";
                            }

                        } else if (secondsBetweenDates >= 30) {
                            secondsBetweenDates = secondsBetweenDates / 30;
                            //this much week ago
                            if (secondsBetweenDates > 1) {
                                datenew = (long) secondsBetweenDates + " months ago";
                            } else {
                                datenew = (long) secondsBetweenDates + " month ago";
                            }

                        } else if (secondsBetweenDates >= 7) {
                            secondsBetweenDates = secondsBetweenDates / 7;
                            //this much week ago
                            datenew = (long) secondsBetweenDates + " week ago";

                        } else if (secondsBetweenDates == 1) {
                            //if its is one day ago then we can say yesterday
                            datenew = "Yesterday";
                        } else {
                            //if its more than one day then these days ago
                            datenew = secondsBetweenDates + " days ago";
                        }
                    } else {
                        //this much time hour ago
                        datenew = secondsBetweenDates + " hour ago";
                    }
                } else {
                    //if time between dates is less than a minute
                    datenew = secondsBetweenDates + " min ago";
                }
            } else {
                //this much seconds ago
                datenew = secondsBetweenDates + " sec ago";
            }
        } else {
            datenew = "Unknown";
        }
        return datenew;
    }


    /*public static void downloadImage(final Activity activity, String url, final String fileName) {

        Picasso.with(activity).load(url).into(new Target() {
       // PicassoCache.getPicassoInstance(activity).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                File file = new File(
                        Environment.getExternalStorageDirectory().getPath()
                                + "/DCIM/"+getAppName(activity)+"/"+ fileName + ".jpg");
                File directory = new File(
                        Environment.getExternalStorageDirectory().getPath()
                                + "/DCIM/"+getAppName(activity)+"/");
                try {
                    if(!directory.exists()){
                        directory.mkdir();
                    }
                    file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("onBitmapLoaded", "Image saved in Gallery"+file.getAbsolutePath());
                showSnakbar(activity,"Image saved in Gallery");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("onBitmapFailed", "ERROR");
                showSnakbar(activity,"Image downloading failed.");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public static String getAppName(Context context){
        Resources appR = context.getResources();
        CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                "string", context.getPackageName()));
        return txt.toString();
    }
    public static void startEmailIntent(Activity context, String email) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    public static void loadImagePicasoCacheEnable(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
        //        PicassoCache.getPicassoInstance(context).load(url).into(imageView);
    }
*/
    public static boolean isAppFirstRun(Activity activity) {
        return MUtil.readBoolean(activity,IS_FIRST_TIME,true);
    }

    public static void setFirstRunFalse(Activity activity) {
         MUtil.writeBoolean(activity,IS_FIRST_TIME,false);
    }

    public static int getDaysBefore(String dateUser) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffInSec = 0;
        try {
            Date date = fromUser.parse(dateUser);
            Date dateCurrent = fromUser.parse(getCurrentTimeStamp());
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(dateCurrent.getTime() - date.getTime());
            diffInSec = Math.abs(diffInSec);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (diffInSec /(3600 * 24));
    }

    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }
    public static String getCurrentMonth() {
        return String.valueOf(new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime())).toUpperCase();
    }

    public static String getDayOfMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
/*public static String getValidTill()

{
    SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

return fromUser;
}*/
    public static String getCurrentTime()
    {
        DateFormat dateformat=new SimpleDateFormat("HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        return dateformat.format(calendar.getTime());
    }


    public static void setToolBarNew(Object object, String title,boolean isBack) {
        Context context = null;

        if(object instanceof Activity){
            context = (Activity)object;
        }

        if(object instanceof Fragment){
            context = ((Fragment)object).getContext();
        }

        ActionBar abar = ((AppCompatActivity) context).getSupportActionBar();
        String className = object.getClass().getSimpleName();

        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.center_title_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER | Gravity.CENTER_VERTICAL);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        abar.setLogo(null);
        if (isBack) {
           // abar.setHomeAsUpIndicator(R.drawable.ic_backarrow);
            abar.setDisplayHomeAsUpEnabled(true);
            abar.setHomeButtonEnabled(true);
        }
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);

    }


}
