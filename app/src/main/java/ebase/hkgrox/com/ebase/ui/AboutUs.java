package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;
;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.util.MUtil;

public class AboutUs extends AppCompatActivity {

    private TextView tv_about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"About Euroils",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_about_us = (TextView)findViewById(R.id.tv_about_us);
        tv_about_us.setText(Html.fromHtml("<h2><p>EUROiLS has introduced its premium range of lubricants & greases in Indian after market. Known for its cutting-edge technology and high-quality products, EUROILS backed by Rites & Jones' pioneering R&D, extensive blending and distribution network, sustained brand enhancement and new generation packaging is a one-stop shop for complete lubrication solutions in the automotive & industrial segments. Euroils range includesover 100 lubricants and 30 formulations encompassaing literally every lubricant requiremen..</p></h2>"));
    }
}
