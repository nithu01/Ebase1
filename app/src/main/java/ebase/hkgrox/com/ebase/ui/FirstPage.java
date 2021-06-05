package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
            R.drawable.image19,R.drawable.image20,R.drawable.image21,R.drawable.image22,R.drawable.image39,R.drawable.image40,R.drawable.image41,R.drawable.imag42
    };
    String data="";
    ImageView back_arrow,forward_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        login=findViewById(R.id.login);
        back_arrow=findViewById(R.id.imageview1);
        forward_arrow=findViewById(R.id.imageview2);
       
        viewPager=findViewById(R.id.view_pager);
        forward_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(FirstPage.this, "", Toast.LENGTH_SHORT).show();
                int tab=viewPager.getCurrentItem();

                    tab++;
                    viewPager.setCurrentItem(tab);

            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab=viewPager.getCurrentItem();
                if(tab>0){
                    tab--;
                    viewPager.setCurrentItem(tab);
                }else if(tab==0){
                    viewPager.setCurrentItem(tab);
                }
            }
        });
        data=getIntent().getStringExtra("DATA");
        if(data.equals("user"))
        {
            login.setVisibility(View.GONE);
        }
        PageAdapter adapterView = new PageAdapter(this,image);
        viewPager.setAdapter(adapterView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstPage.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new SliderTimer(), 1000, 3000);
    }
//    private class SliderTimer extends TimerTask {
//
//        @Override
//        public void run() {
//            if (FirstPage.this != null) {
//                FirstPage.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (viewPager.getCurrentItem() < 42 - 1) {
//                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                        } else {
//                            viewPager.setCurrentItem(0);
//                        }
//                    }
//                });
//            }
//        }
//    }
}
