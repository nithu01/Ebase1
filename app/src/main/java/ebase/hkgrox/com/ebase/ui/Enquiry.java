package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import ebase.hkgrox.com.ebase.util.Mail;

public class Enquiry extends AppCompatActivity {

    private AppCompatButton btn_submit;
    private EditText et_title;
    private EditText et_message;
    private USER user;
    private String title;
    private String message;
    private EditText et_name;
    private EditText et_mobile;
    private EditText et_email;
    private LinearLayout ll_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Enquiry",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        ll_name  = (LinearLayout)findViewById(R.id.ll_name);
        et_name  = (EditText)findViewById(R.id.et_name);


        et_mobile  = (EditText)findViewById(R.id.et_mobile);


        et_email  = (EditText)findViewById(R.id.et_email);




        try {
            user = (USER) getIntent().getExtras().getSerializable(AppUtil.DATA);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(user == null){
            ll_name.setVisibility(View.VISIBLE);
            user = new USER();
            user.setNAME(et_name.getText().toString());
            user.setMOBILE(et_mobile.getText().toString());
            user.setEMAIL(et_email.getText().toString());
        }else {
            ll_name.setVisibility(View.GONE);
        }
        btn_submit  = (AppCompatButton)findViewById(R.id.btn_submit);

        et_title  = (EditText)findViewById(R.id.et_title);

        et_message  = (EditText)findViewById(R.id.et_message);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MUtil.showProgressDialog(Enquiry.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
                title = et_title.getText().toString();
                message = et_message.getText().toString();

                if(user == null){
                    MUtil.dismissProgressDialog();
                    showInfoAlertDialog(Enquiry.this, "Please Login First");

                }else{
                   new CAsync().execute();
                }

            }
        });






    }




    class CAsync extends AsyncTask {

        private boolean flagResult;

        @Override
        protected Object doInBackground(Object[] params) {
         //   Looper.prepare();
            Mail m = new Mail("euroebase@gmail.com", "euro@99@");


            // String to = "rajeev0814@gmail.com";
            String to = MUtil.EMAIL_ID;
            String[] toArr = {to};
            m.setTo(toArr);
            m.setFrom("euroebase@gmail.com");

            String subject = ""+user.getNAME() +" Asked for "+title;
            m.setSubject(subject);

            String body = "Name "+user.getNAME()+"\n"+
                    "Mobile No "+user.getMOBILE()+"\n"+
                    "Email "+user.getEMAIL()+"\n"+
                    "City "+user.getCITY()+"\n"+
                    "State "+user.getSTATE()+"\n"+
                    "Pincode "+user.getPINCODE()+"\n"+
                    "Asked for \n "+message;
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
                MUtil.showInfoAlertDialog(Enquiry.this, "Enquiry Request failed try again ");
                MUtil.dismissProgressDialog();
            } else {
                MUtil.dismissProgressDialog();
                MUtil.showSnakbar(Enquiry.this, "Enquiry Message sent successfully.");
                finish();
            }
        }
    }



    public  void showInfoAlertDialog(final Context context, String message) {
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
