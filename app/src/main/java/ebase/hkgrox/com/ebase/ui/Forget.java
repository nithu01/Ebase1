package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.MUtil;
import ebase.hkgrox.com.ebase.util.Mail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forget extends AppCompatActivity {

    private AppCompatButton btn_submit;
    private EditText et_mobile;
    private EditText et_email;
    private USER user;
    Config config;

    String login_url2 =config.ip_url;
   // private String login_url2 = "http://192.168.0.101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this, "Forgot", true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        et_mobile = (EditText) findViewById(R.id.et_mobile);


        et_email = (EditText) findViewById(R.id.et_email);

        btn_submit = (AppCompatButton) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    MUtil.showProgressDialog(Forget.this);
                    Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                    Addvender addvender=retrofit.create(Addvender.class);
                    Call<List<USER>> call=addvender.forget(et_mobile.getText().toString(),et_email.getText().toString());
                    call.enqueue(new Callback<List<USER>>() {
                        @Override
                        public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                            List<USER> list=response.body();

                            USER user=null;
                           // Toast.makeText(Forget.this, "Success"+response, Toast.LENGTH_SHORT).show();
                            for(int i=0;i<list.size();i++){
                                user=new USER();
                                String name=list.get(i).getNAME();
                                String mobile=list.get(i).getMOBILE();
                                String email=list.get(i).getEMAIL();
                                String enable=list.get(i).getENABLE();
                               // Toast.makeText(Forget.this, email, Toast.LENGTH_SHORT).show();
                                String password=list.get(i).getPASSWORD();

                                user.setMOBILE(mobile);
                                user.setNAME(name);
                                user.setENABLE(enable);
                                user.setPASSWORD(password);
                                user.setEMAIL(email);
                            }
                            if(user.getNAME()==null) {
                                MUtil.showInfoAlertDialog(Forget.this, "User does not exist with this number try another no or register");
                            MUtil.dismissProgressDialog();
                            } else {
                                if (user.getEMAIL() != null && et_email.getText().toString().equalsIgnoreCase(user.getEMAIL())) {
                                    if (user.getENABLE() == null || user.getENABLE().equalsIgnoreCase("true")) {

                                        Forget.this.user = user;
                                        new CAsync().execute();
                                        return;

                                    } else {
                                        MUtil.showInfoAlertDialog(Forget.this, "You are not allowed to access this application. Please contact with administater");
                                        MUtil.dismissProgressDialog();
                                    }
                                } else {
                                    MUtil.showInfoAlertDialog(Forget.this, "Email Id does not match try again");
                                    MUtil.dismissProgressDialog();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<USER>> call, Throwable t) {
                            Toast.makeText(Forget.this, "Error", Toast.LENGTH_SHORT).show();
                            MUtil.dismissProgressDialog();
                        }
                    });
                   /* DatabaseReference databaseReferencenew = AppUtil.getUserReference(Forget.this).child(et_mobile.getText().toString());
                    databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            USER user = dataSnapshot.getValue(USER.class);
                            if (user == null) {
                                MUtil.showInfoAlertDialog(Forget.this, "User does not exist with this number try another no or register");
                            } else {
                                if (user.getEMAIL() != null && et_email.getText().toString().equalsIgnoreCase(user.getEMAIL())) {
                                    if (user.getENABLE() == null || user.getENABLE().equalsIgnoreCase("true")) {

                                        Forget.this.user = user;
                                        new CAsync().execute();
                                        return;

                                    } else {
                                        MUtil.showInfoAlertDialog(Forget.this, "You are not allowed to access this application. Please contact with administater");
                                    }
                                } else {
                                    MUtil.showInfoAlertDialog(Forget.this, "Email Id does not match try again");
                                }
                            }
                            MUtil.dismissProgressDialog();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            MUtil.dismissProgressDialog();
                        }
                    });*/
                }
            }



    });


}


    private boolean isValid() {
        return true;
    }

class CAsync extends AsyncTask {

    private boolean flagResult;

    @Override
    protected Object doInBackground(Object[] params) {
        //   Looper.prepare();
        Mail m = new Mail("euroebase@gmail.com", "euro@99@");


        // String to = "rajeev0814@gmail.com";
        String to = user.getEMAIL();
        String[] toArr = {to};
        m.setTo(toArr);
        m.setFrom("euroebase@gmail.com");

        String subject = "Password Details";
        m.setSubject(subject);

        String body = "Name " + user.getNAME() + "\n" +
                "Mobile No " + user.getMOBILE() + "\n" +
                "Email " + user.getEMAIL() + "\n" +
                "Your password is " + user.getPASSWORD();
        m.setBody(body);

        try {
            // m.addAttachment("/sdcard/filelocation");
            flagResult = m.send();
            if (flagResult) {
                Log.e("MailApp", "send email");
                //// Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (!flagResult) {
            MUtil.showInfoAlertDialog(Forget.this, "Password Request failed try again ");
            MUtil.dismissProgressDialog();
        } else {
            MUtil.dismissProgressDialog();
           // MUtil.showInfoAlertDialog(Forget.this, "Password mail sent successfully.");
            Toast.makeText(Forget.this, "Password mail sent successfully.", Toast.LENGTH_LONG).show();
            MUtil.dismissProgressDialog();
            finish();
        }
    }

}


    public void showInfoAlertDialog(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
}
