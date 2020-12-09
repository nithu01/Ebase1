package ebase.hkgrox.com.ebase.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.MyAdapterString;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffForm extends AppCompatActivity implements View.OnClickListener {

    //String  form_url="http://192.168.0.101";
    Config config;

    String login_url2 = config.ip_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_form);
//        Toast.makeText(StaffForm.this,"Staff form",Toast.LENGTH_SHORT).show();
       /* try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {

        }*/

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Create Staff",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViews();
        try {
            setData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setData() {

        USER user = (USER) getIntent().getExtras().getSerializable("DATA");
        MUtil.setToolBarNew(this,"Edit Staff",true);
        String page =    getIntent().getExtras().getString("FROM");
        if(page!=null && page.equalsIgnoreCase("staffedit")){
            etName.setText(user.getNAME());
            etMobile.setText(user.getMOBILE());
            etEmail.setText(user.getEMAIL());
            etLocation.setText(user.getLOCATION());
            etSaleCollectionMgr.setVisibility(View.GONE);
            etSaleTagetMgr.setVisibility(View.GONE);
            etsaleTargetExecutuives.setVisibility(View.GONE);
            etPassword.setText(user.getPASSWORD());
            if(user.getDEGINATION().toLowerCase().contains("manager") || user.getDEGINATION().equalsIgnoreCase("Manager")){
                etPosition.setSelection(1);
                etSaleCollectionMgr.setVisibility(View.VISIBLE);
                etSaleTagetMgr.setVisibility(View.VISIBLE);

                if(user.getMONTHALY_COLLECTION()!=null){
                    etSaleCollectionMgr.setText(user.getMONTHALY_COLLECTION());
                }

                if(user.getMONTHALY_SALE()!=null){
                    etSaleTagetMgr.setText(user.getMONTHALY_SALE());
                }
            }else{

                etPosition.setSelection(0);

                etsaleTargetExecutuives.setVisibility(View.VISIBLE);

                if(user.getSSTG()!=null){
                    etsaleTargetExecutuives.setText(user.getSSTG());
                }
            }

            if(user.getENABLE()==null || user.getENABLE().toLowerCase().contains("true")){
               // Toast.makeText(this, ""+user.getENABLE(), Toast.LENGTH_SHORT).show();
                etDisableOrEnable.setSelection(0);
            }else{
                etDisableOrEnable.setSelection(1);
            }



        }
    }


    private AppBarLayout appbar;
    private Toolbar toolbar;
    private EditText etName;
    private EditText etMobile;
    private EditText etEmail;
    private EditText etLocation;
    private EditText etPassword;
    private Spinner etPosition;
    private EditText etsaleTargetExecutuives;
    private EditText etSaleTagetMgr;
    private EditText etSaleCollectionMgr;
    private Spinner etDisableOrEnable;
    private AppCompatButton btnSubmit;
    private TextView tvSignIn;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-12 21:09:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        appbar = (AppBarLayout)findViewById( R.id.appbar );
        toolbar = (Toolbar)findViewById( R.id.toolbar );
        etName = (EditText)findViewById( R.id.et_name );
        etMobile = (EditText)findViewById( R.id.et_mobile );
        etEmail = (EditText)findViewById( R.id.et_email );
        etLocation = (EditText)findViewById( R.id.et_location );
        etPassword = (EditText)findViewById( R.id.etPassword );
        etPosition = (Spinner)findViewById( R.id.et_spinner );
        etDisableOrEnable = (Spinner)findViewById( R.id.et_disable_or_enable );
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Executive");
        strings.add("Manager");
        etPosition.setAdapter(new MyAdapterString(this,strings));

        ArrayList<String> string = new ArrayList<>();
        string.add("Enable");
        string.add("Disable");
        etDisableOrEnable.setAdapter(new MyAdapterString(this,string));

        etsaleTargetExecutuives = (EditText)findViewById( R.id.etsale_target_executuives );
        etSaleTagetMgr = (EditText)findViewById( R.id.et_sale_taget_mgr );
        etSaleCollectionMgr = (EditText)findViewById( R.id.et_sale_collection_mgr );

        btnSubmit = (AppCompatButton)findViewById( R.id.btnSubmit );
        tvSignIn = (TextView)findViewById( R.id.tvSignIn );



        etPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etsaleTargetExecutuives.setVisibility(View.GONE);
                etSaleTagetMgr.setVisibility(View.GONE);
                etSaleCollectionMgr.setVisibility(View.GONE);
                if(position==0){
                    etsaleTargetExecutuives.setVisibility(View.VISIBLE);
                }else {
                    etSaleTagetMgr.setVisibility(View.VISIBLE);
                    etSaleCollectionMgr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnSubmit.setOnClickListener( this );
    }


    /*new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etsaleTargetExecutuives.setVisibility(View.GONE);
                etSaleTagetMgr.setVisibility(View.GONE);
                etSaleCollectionMgr.setVisibility(View.GONE);
                if(position==0){
                    etsaleTargetExecutuives.setVisibility(View.VISIBLE);
                }else {
                    etSaleTagetMgr.setVisibility(View.VISIBLE);
                    etSaleCollectionMgr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }*/
   


    @Override
    public void onClick(View v) {
        if ( v == btnSubmit ) {
            // Handle clicks for btnSubmit
            if (isValid()) {
                MUtil.showProgressDialog(StaffForm.this);
                String value="";
                String enable="";
                if(etPosition.getSelectedItemPosition()==0)
                {
                    value="Executive";
                }
                else if(etPosition.getSelectedItemPosition()==1){
                    value="Manager";
                }else{

                }
                if(etDisableOrEnable.getSelectedItemPosition()==0)
                {
                    enable="TRUE";
                }
                else if(etDisableOrEnable.getSelectedItemPosition()==1)
                {
                    enable="FALSE";
                }



                USER user = (USER) getIntent().getExtras().getSerializable("DATA");
                String page =    getIntent().getExtras().getString("FROM");
                Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
                Addvender addvender=retrofit.create(Addvender.class);
                if(page!=null && page.equalsIgnoreCase("staffedit")){
                    if (user != null) {
                       /* etMobile.setEnabled(false);
                        etMobile.setOnKeyListener(null);
                        etMobile.setInputType(InputType.TYPE_NULL);
                        etMobile.setTextIsSelectable(false);
                        etMobile.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                return true;
                            }
                        });*/
                       if(etMobile.getText().toString().equals(user.getMOBILE())) {
                           MUtil.showProgressDialog(this);
                           Call<List<USER>> call = addvender.staffupdate(etName.getText().toString(), etMobile.getText().toString(), etEmail.getText().toString(), etLocation.getText().toString(), etPassword.getText().toString(), value, etsaleTargetExecutuives.getText().toString(), etSaleTagetMgr.getText().toString(), etSaleCollectionMgr.getText().toString(), enable);
                           call.enqueue(new Callback<List<USER>>() {
                               @Override
                               public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                                   //  Toast.makeText(getApplicationContext(),"response"+response,Toast.LENGTH_SHORT).show();

                                   /*MUtil.dismissProgressDialog();
                                   showInfoAlertDialog(StaffForm.this, "Staff updated successfully.");
                                   finish();*/
                                   AlertDialog.Builder builder1 = new AlertDialog.Builder(StaffForm.this);
                                   builder1.setMessage("Record inserted");
                                   builder1.setCancelable(true);

                                   builder1.setPositiveButton(
                                           "ok",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog, int id) {
                                                   finish();
                                               }
                                           });



                                   AlertDialog alert11 = builder1.create();
                                   alert11.show();
                               }

                               @Override
                               public void onFailure(Call<List<USER>> call, Throwable t) {
                                   Toast.makeText(getApplicationContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                  MUtil.dismissProgressDialog();
                               }
                           });
                       }

                       else {
                           MUtil.showInfoAlertDialog(StaffForm.this, "You can't change the mobile no of Staff.");
                           MUtil.dismissProgressDialog();
                       }

                    } else {
                        MUtil.showInfoAlertDialog(StaffForm.this, "You can't change the mobile no of Staff.");
                        MUtil.dismissProgressDialog();
                    }
                }else {
                  //  if (user == null) {
                    MUtil.showProgressDialog(this);
                        Call<List<USER>> call=addvender.staffform(etName.getText().toString(),etMobile.getText().toString(),etEmail.getText().toString(),etLocation.getText().toString(),etPassword.getText().toString(),value,etsaleTargetExecutuives.getText().toString(),etSaleTagetMgr.getText().toString(),etSaleCollectionMgr.getText().toString(),enable);
                        call.enqueue(new Callback<List<USER>>(){
                            @Override
                            public void onResponse(Call<List<USER>> call, Response<List<USER>>response) {
                               // Toast.makeText(getApplicationContext(),"response"+response,Toast.LENGTH_SHORT).show();
                               /* showInfoAlertDialog(StaffForm.this,"Staff created successfully.");
                                MUtil.dismissProgressDialog();
                                finish();*/
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(StaffForm.this);
                                builder1.setMessage("Staff created successfully.");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });



                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }

                            @Override
                            public void onFailure(Call<List<USER>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"User already exist",Toast.LENGTH_SHORT).show();
                                MUtil.dismissProgressDialog();
                            }
                        });
                   /* } else {
                        MUtil.showInfoAlertDialog(StaffForm.this, "Staff already exist with this number try another no or login");
                        MUtil.dismissProgressDialog();
                    }
                */}

                /*
                DatabaseReference databaseReferencenew = AppUtil.getUserReference(StaffForm.this).child(etMobile.getText().toString());
                databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        USER user = dataSnapshot.getValue(USER.class);
                        String page =    getIntent().getExtras().getString("FROM");
                        if(page!=null && page.equalsIgnoreCase("staffedit")){
                            if (user != null) {
                                registered();
                            } else {
                                MUtil.showInfoAlertDialog(StaffForm.this, "You can't change the mobile no of Staff.");
                                MUtil.dismissProgressDialog();
                            }
                        }else {
                            if (user == null) {
                                registered();
                            } else {
                                MUtil.showInfoAlertDialog(StaffForm.this, "Staff already exist with this number try another no or login");
                                MUtil.dismissProgressDialog();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        MUtil.dismissProgressDialog();
                    }
                });
                */

            }
        }

        if ( v == tvSignIn ) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void registered() {

        DatabaseReference databaseReference = AppUtil.getUserReference(StaffForm.this);
        final USER user = new USER();
        user.setMOBILE(etMobile.getText().toString());
        user.setEMAIL(etEmail.getText().toString());
        user.setLOCATION(etLocation.getText().toString());
        user.setPASSWORD(etPassword.getText().toString());
        user.setNAME(etName.getText().toString());

        if(etPosition.getSelectedItemPosition()==0){
            user.setDEGINATION("Executive");
        }
        if(etPosition.getSelectedItemPosition()==1){
            user.setDEGINATION("Manager");
        }

        user.setSSTG(etsaleTargetExecutuives.getText().toString());
        user.setMONTHALY_SALE(etSaleTagetMgr.getText().toString());
        user.setMONTHALY_COLLECTION(etSaleCollectionMgr.getText().toString());
        if(etDisableOrEnable.getSelectedItemPosition()==0){
            user.setENABLE("true");
        }
        if(etDisableOrEnable.getSelectedItemPosition()==1){
            user.setENABLE("false");
        }

        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                String page =    getIntent().getExtras().getString("FROM");
                if(page!=null && page.equalsIgnoreCase("staffedit")){
                    if (databaseError != null) {
                        MUtil.showSnakbar(StaffForm.this, "Staff updation failed try again ");
                    } else {
                        MUtil.showSnakbar(StaffForm.this, "Staff updated successfully.");
                        startHomeActivity(user);
                    }
                }else {
                    if (databaseError != null) {
                        MUtil.showSnakbar(StaffForm.this, "Staff creation failed try again ");
                    } else {
                        MUtil.showSnakbar(StaffForm.this, "Staff created successfully.");
                        startHomeActivity(user);
                    }
                }
            }
        });


    }
    private void startHomeActivity(USER user) {
       /* Intent intent = new Intent(this,UserHome.class);
        intent.putExtra("DATA",user);
        startActivity(intent);*/
     //  showInfoAlertDialog(StaffForm.this,"Staff created successfully.");


    }

    public  void showInfoAlertDialog(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                StaffForm.this.finish();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private boolean isValid()
    {
        if(etName.getText().toString().length()<2){
            MUtil.showSnakbar(this,"Name Not Valid");
            return false;
        }

        if(etMobile.getText().toString().length()<10){
            MUtil.showSnakbar(this,"Mobile No Not Valid");
            return false;
        }


        if(etPassword.getText().toString().length()<4){
            MUtil.showSnakbar(this,"Password Not Valid");
            return false;
        }

        return true;
    }
}











