package ebase.hkgrox.com.ebase.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.ui.ExecutivesSale;
import ebase.hkgrox.com.ebase.ui.ManagersSale;


/**
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final String type;
    private List<USER> mValues;
    private final Context context;
    private final String textInvited = "Invited";

    public Adapter(Context context, List<USER> items,String type) {
        this.type = type;
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adap, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        USER user = mValues.get(position);
        holder.targe_months.setVisibility(View.GONE);
        if(type.equalsIgnoreCase("user")) {
                holder.tvName.setText("Name - " + user.getNAME());
                holder.tvNumber.setText("Mobile - " + user.getMOBILE());
                holder.tvPoints.setText("Points - "+user.getPOINTS());

        }

        if(type.equalsIgnoreCase("executive")) {
            holder.tvName.setText("Name - " + user.getNAME());
            holder.tvNumber.setText("Mobile - " + user.getMOBILE());
            holder.tvPoints.setText("SSTG - "+user.getSSTG()+" L");

        }
        if(type.equalsIgnoreCase("manager")) {
            holder.tvName.setText("Name - " + user.getNAME());
            holder.tvPoints.setText("MST - " + user.getMONTHALY_SALE() + " ₹");
            holder.targe_months.setText("MCT - " + user.getMONTHALY_COLLECTION() + " L");
            holder.tvNumber.setText("Mobile - " + user.getMOBILE());
            holder.targe_months.setVisibility(View.VISIBLE);
        }



        if(type.equalsIgnoreCase("all")) {
            String localtype = user.getDEGINATION();
            if(localtype.equalsIgnoreCase("user")) {
                holder.tvName.setText("Name - " + user.getNAME());
                holder.tvNumber.setText("Mobile - " + user.getMOBILE());
                holder.tvPoints.setText("Points - "+user.getPOINTS());

            }

            if(localtype.toLowerCase().contains("executive")) {
                holder.tvName.setText("Name - " + user.getNAME());
                holder.tvNumber.setText("Mobile - " + user.getMOBILE());
                holder.tvPoints.setText("SSTG - "+user.getSSTG()+" L");

            }
            if(localtype.toLowerCase().contains("manager")) {
                holder.tvName.setText("Name - " + user.getNAME());
                holder.tvPoints.setText("MST - " + user.getMONTHALY_SALE() + " ₹");
                holder.targe_months.setText("MCT - " + user.getMONTHALY_COLLECTION() + " L");
                holder.tvNumber.setText("Mobile - " + user.getMOBILE());
                holder.targe_months.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView targe_months;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            findViews(view);
        }


        private TextView tvName;
        private TextView tvPoints;
        private TextView tvNumber;
        ;

        private LinearLayout ll_list_item_contact;

        // private TextView tvInvite;
        // private View vBottom;
        private void findViews(View mView) {
            tvName = (TextView) mView.findViewById(R.id.tv_name);
            tvNumber = (TextView) mView.findViewById(R.id.tv_mobile);
            tvPoints = (TextView) mView.findViewById(R.id.tv_points);
            targe_months= (TextView) mView.findViewById(R.id.targe_months);

                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.equalsIgnoreCase("executive")) {
                            Intent intent = new Intent(context, ExecutivesSale.class);
                            intent.putExtra("DATA", mValues.get(getAdapterPosition()));
                            intent.putExtra("SALE", "false");
                            context.startActivity(intent);
                        }
                        if(type.equalsIgnoreCase("manager")) {
                            Intent intent = new Intent(context, ManagersSale.class);
                            intent.putExtra("DATA", mValues.get(getAdapterPosition()));
                            intent.putExtra("SALE", "false");
                            context.startActivity(intent);
                        }
                    }
                });


        }


    }
}
