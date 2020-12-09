package ebase.hkgrox.com.ebase.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.adapter.CustomPagerAdapter;
import ebase.hkgrox.com.ebase.bean.ProductList;
import ebase.hkgrox.com.ebase.util.MUtil;

public class Products extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<ProductList> productLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MUtil.setToolBarNew(this,"Products",true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

         viewPager = (ViewPager) findViewById(R.id.viewpager);
         productLists = new ArrayList<>();


        ProductList  productList = new ProductList();


        productList.setImage(R.drawable.one);
        productList.setName("Superio E 15W40 CI-4 Plus");
        productLists.add(productList);

        ProductList  productList2 = new ProductList();
        productList2.setImage(R.drawable.two);
        productList2.setName("Primosynth 10W40 SM/CF Semi Synthetic");
        productLists.add(productList2);


        ProductList  productList3 = new ProductList();
        productList3.setImage(R.drawable.three);
        productList3.setName("Euro Gold 4T 20W40 SL");
        productLists.add(productList3);

        ProductList  productList1 = new ProductList();
        productList1.setImage(R.drawable.four);
        productList1.setName("Multigrade Plus 20W40 CF-4");
        productLists.add(productList1);


        ProductList  productList4 = new ProductList();
        productList4.setImage(R.drawable.five);
        productList4.setName("Rotomax Gear 85W140");
        productLists.add(productList4);

        ProductList  productList5 = new ProductList();
        productList5.setImage(R.drawable.six);
        productList5.setName("Euro AP3");
        productLists.add(productList5);



        ProductList  productList6 = new ProductList();
        productList6.setImage(R.drawable.seven);
        productList6.setName("Turbo Cool Coolant");
        productLists.add(productList6);

        ProductList  productList7 = new ProductList();
        productList7.setImage(R.drawable.eight);
        productList7.setName("Hydraulic AW 68, 46, 32");
        productLists.add(productList7);
















        viewPager.setAdapter(new CustomPagerAdapter(this,productLists));
        pageSwitcher(2);
    }




    Timer timer;
    int page = 0;

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {
                    page = viewPager.getCurrentItem();
                    if (page >= productLists.size()-1) { // In my case the number of pages are 5
                        page = 0;
                        viewPager.setCurrentItem(page);
                       // timer.cancel();
                        // Showing a toast for just testing purpose
                     /*   Toast.makeText(getApplicationContext(), "Timer stoped",
                                Toast.LENGTH_LONG).show();*/
                    } else {
                        viewPager.setCurrentItem(++page);
                    }
                }
            });

        }
    }
}

