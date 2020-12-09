package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.PageAdapter;

public class FirstPage extends AppCompatActivity {
    ViewPager viewPager;
    Button login;
    int image[]={ R.drawable.image1, R.drawable.image2, R.drawable.image3,R.drawable.image4, R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10,R.drawable.image11,R.drawable.image12,R.drawable.image13,R.drawable.image62,R.drawable.image63,R.drawable.image14,R.drawable.image15,R.drawable.image16,R.drawable.image17,R.drawable.image18,
            R.drawable.image19,R.drawable.image20,R.drawable.image21,R.drawable.image22,R.drawable.image39,R.drawable.image40,R.drawable.image41
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        login=findViewById(R.id.login);
        viewPager=findViewById(R.id.view_pager);

        PageAdapter adapterView = new PageAdapter(this,image);
        viewPager.setAdapter(adapterView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstPage.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 1000, 3000);
    }
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (FirstPage.this != null) {
                FirstPage.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < 3 - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}
