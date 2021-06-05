package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.ui.Message;

public class ViewPageAdapter extends PagerAdapter {

    public List<Message> list;
    LayoutInflater inflater;
    Context context;

    public ViewPageAdapter(List<Message> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView message,date;
        ImageView imageView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.expand_pager_item, container,
                false);

        message = (TextView) itemView.findViewById(R.id.message);
        date=(TextView)itemView.findViewById(R.id.date);
        imageView=(ImageView)itemView.findViewById(R.id.imageview);
        message.setText(list.get(position).getMessage());
        date.setText(list.get(position).getDate());
//        Picasso.get().load("http://shreeshivsewak.com/"+list.get(position).getImage()).into(imageView);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }
}
