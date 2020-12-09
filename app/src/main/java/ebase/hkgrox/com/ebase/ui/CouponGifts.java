package ebase.hkgrox.com.ebase.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.util.MUtil;

public class CouponGifts extends AppCompatActivity {

    private TextView tv_about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_gifts);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Coupon ApiGifts",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        WebView myWebView = (WebView) findViewById(R.id.wb_main);
        myWebView.loadUrl("http://euroils.com/gift.jpg ");
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
    }
}
