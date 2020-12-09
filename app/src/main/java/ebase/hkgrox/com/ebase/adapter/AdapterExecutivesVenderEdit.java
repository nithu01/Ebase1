package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;


import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Api.Addvender;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.ui.ChangePasswordActivityVender;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Zooom HKG on 1/8/2018.
 */

public class AdapterExecutivesVenderEdit extends RecyclerView.Adapter<AdapterExecutivesVenderEdit.ViewHolder> {

    private List<USER> mValues;
    private final Context context;
    private final String textInvited = "Invited";
    Config config;

    String login_url2 =config.ip_url;

    public AdapterExecutivesVenderEdit(Context context, List<USER> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adap_executives, parent, false);
        return new ViewHolder(view);
    }


    public void filter(ArrayList<USER> filteredModelList) {
        mValues  = filteredModelList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final USER user = mValues.get(position);
        // holder.month_collection.setVisibility(View.GONE);
        String localtype = user.getDEGINATION();
      /*  if(localtype.toLowerCase().contains("executive")) {
            holder.tvName.setText("" + user.getNAME());
            holder.mobile.setText("" + user.getMOBILE());
            holder.month_sale.setText("SSTG - "+user.getSSTG()+" L");
            holder.degination.setText("Executive");

        }

        if(localtype.toLowerCase().contains("user")) {*/

        holder.month_collection.setVisibility(View.GONE);
        holder.tvName.setText("" + user.getNAME());
        holder.mobile.setText("" + user.getMOBILE());
        holder.month_sale.setText("Points - "+user.getRP()+"");
       // holder.tvgp.setText("Gift Redeemed - "+user.getGP()+"");
       // holder.tvrp.setText("Remaining Points - "+user.getRP()+"");
        holder.degination.setVisibility(View.GONE);
        if(user.getENABLE()==null || user.getENABLE().equalsIgnoreCase("true")){
            holder.btn_enable.setText("Disable");
        }else{
            holder.btn_enable.setText("Enable");
        }

        // }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private AppCompatButton btn_enable;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            findViews(view);
        }


        private TextView tvName;
        private TextView mobile;
        private TextView degination;
        private TextView month_sale;
        private TextView month_collection;
        private AppCompatButton btn_change_password;
        ;

        private LinearLayout ll_list_item_contact;

        // private TextView tvInvite;
        // private View vBottom;
        private void findViews(View mView) {
            tvName = (TextView) mView.findViewById(R.id.tv_name);
            mobile = (TextView) mView.findViewById(R.id.mobile);
            degination = (TextView) mView.findViewById(R.id.degination);
            month_sale = (TextView) mView.findViewById(R.id.tv_month_targets);
             month_collection = (TextView) mView.findViewById(R.id.month_collection);
            btn_enable = (AppCompatButton) mView.findViewById(R.id.btn_enable);




           btn_change_password = (AppCompatButton) mView.findViewById(R.id.btn_change_password);
            btn_change_password.setVisibility(View.VISIBLE);
            btn_change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ChangePasswordActivityVender.class);
                    intent.putExtra("DATA", mValues.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

            btn_enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String value = "";
                        if(btn_enable.getText().toString().equalsIgnoreCase("Enable")){
                            value ="TRUE";
                        }else{
                            value = "FALSE";
                        }

                       upload(getAdapterPosition(),value);


                    }
            });




        }


    }

    private void upload(int adapterPosition, String value) {


        USER user = mValues.get(adapterPosition);
        user.setENABLE(value);
        MUtil.showProgressDialog(context);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
        Addvender addvender = retrofit.create(Addvender.class);
        Call<List<USER>> call = addvender.updateuser(user.getMOBILE(), value);
        call.enqueue(new Callback<List<USER>>() {
            @Override
            public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                // Toast.makeText(getApplicationContext(), "response" + response, Toast.LENGTH_SHORT).show();
                MUtil.dismissProgressDialog();
                MUtil.showSnakbar(context, "User updated successfully.");
                notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<USER>> call, Throwable t) {
                MUtil.showSnakbar(context, "User updated failed try again ");
            }
        });
/*
        DatabaseReference databaseReference = AppUtil.getUserReference((Activity) context);
        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(context, "User updated failed try again ");
                } else {
                    MUtil.showSnakbar(context, "User updated successfully.");
                    notifyDataSetChanged();
                }
            }
        });
        */

    }
}
