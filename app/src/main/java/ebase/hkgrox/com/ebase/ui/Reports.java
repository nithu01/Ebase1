package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.util.MUtil;

public class Reports extends AppCompatActivity {
    Button report_name,report_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Report",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        report_name=(Button)findViewById(R.id.report_name);
        report_date=(Button)findViewById(R.id.report_date);


        report_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Report_name.class);
                startActivity(intent);
            }
        });
        report_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Reportbydate.class);
                startActivity(intent);
            }
        });

    }
}
