package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.ui.FirstPage;

import static java.security.AccessController.getContext;

public class PageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] images;

    public PageAdapter(Context context,int[] images) {
        this.mContext = context;
        this.images=images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);

        imageView.setImageResource(images[position]);
        if(imageView.getParent() != null) {
            ((ViewGroup)imageView.getParent()).removeView(imageView); // <- fix
        }
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

}
