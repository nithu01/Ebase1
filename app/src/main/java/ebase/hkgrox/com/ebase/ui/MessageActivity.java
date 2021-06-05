package ebase.hkgrox.com.ebase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.ViewPageAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {
    String text="";
    String date="";
    ViewPageAdapter viewPageadapter;
    ViewPager viewPager;
    ArrayList<Message> arrayList=new ArrayList<>();
    String message="";
    int pos;
    Toolbar toolbar;
    ImageView imageView1,imageView2,imageView3,imageView4;
    int i;
    ScrollView scrollView;
    String login= Config.ip_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        scrollView = (ScrollView) findViewById(R.id.scrollview);
        imageView1 = (ImageView) findViewById(R.id.imageview);
        imageView2 = (ImageView) findViewById(R.id.imageview1);
        imageView3 = (ImageView) findViewById(R.id.imageview2);
        imageView4 = (ImageView) findViewById(R.id.imageview3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //Toast.makeText(ExpandMessage.this,"back key is pressed", Toast.LENGTH_SHORT).show();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        message = getIntent().getStringExtra("Expandtext");
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int tab = viewPager.getCurrentItem();
                viewPager.setCurrentItem(0);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // viewPager.setCurrentItem(viewPager.);
                viewPager.setCurrentItem(i);
            }
        });

        //  pos= Integer.parseInt(message);
        //   Toast.makeText(this, ""+pos, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(MessageActivity.this);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(login).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService api = retrofit.create(ApiService.class);
        Call<List<Message>> call = api.viewmessage();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Message messages = null;
                List<Message> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    //  Toast.makeText(ExpandMessage.this, "Success", Toast.LENGTH_SHORT).show();
                    messages = new Message();
                    text = list.get(i).getMessage();

                    String image = list.get(i).getImage();
                    // Toast.makeText(ExpandMessage.this, ""+list.get(i).getDate(), Toast.LENGTH_SHORT).show();
                    messages.setImage(image);
                    messages.setDate(list.get(i).getDate());
                    messages.setMessage(text);
                    arrayList.add(messages);
                }

                viewPageadapter = new ViewPageAdapter(arrayList, getApplicationContext());
                viewPager.setAdapter(viewPageadapter);

                PagerAdapter pg = viewPager.getAdapter();
                i = pg.getCount();
                // Toast.makeText(ExpandMessage.this, ""+i, Toast.LENGTH_SHORT).show();

                viewPager.setVisibility(View.GONE);

                viewPager.setCurrentItem(pos);
//
                viewPager.setVisibility(View.VISIBLE);
//
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            int dragthreshold = 30;
            int downX;
            int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }
}