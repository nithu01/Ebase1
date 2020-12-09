package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;

public class MyAdapterString extends ArrayAdapter {

    public MyAdapterString(Context context, List<String> objects) {
        super(context, R.layout.support_simple_spinner_dropdown_item, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}