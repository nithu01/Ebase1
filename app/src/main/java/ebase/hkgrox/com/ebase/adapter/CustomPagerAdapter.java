package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.ProductList;

public class CustomPagerAdapter extends PagerAdapter {

    private final List<ProductList> productList;
    private Context mContext;

    public CustomPagerAdapter(Context context,List<ProductList> productList) {
        mContext = context;
        this.productList= productList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.product_item, collection, false);

        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
        imageView.setImageResource(productList.get(position).getImage());

        TextView name = (TextView) layout.findViewById(R.id.name);
        name.setText(productList.get(position).getName());



        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}