package ebase.hkgrox.com.ebase.adapter;
import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import ebase.hkgrox.com.ebase.ApiService;
import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.ui.AdminHome;
import ebase.hkgrox.com.ebase.ui.UserAddress;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class upgrade_adpater extends RecyclerView.Adapter<upgrade_adpater.MyViewHolder> implements Filterable {

    private List<USER> mValues=new ArrayList<>();;
    Context context;
    private List<USER> mValueslist;
    String datee;

    public upgrade_adpater(Context context, List<USER> items) {
        this.context=context;
        mValues = items;
        mValueslist=new ArrayList<>(mValues);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_upgrade,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(mValues.get(position).getNAME());
        holder.mobile.setText(mValues.get(position).getMOBILE());
//      holder.points.setText(mValues.get(position).getPOINTS());
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                datee= formatter.format(date);
//                Toast.makeText()
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.ip_url).addConverterFactory(GsonConverterFactory.create()).build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<List<USER>> call = apiService.upgrade_user(holder.mobile.getText().toString(),datee);
                call.enqueue(new Callback<List<USER>>() {
                    @Override
                    public void onResponse(Call<List<USER>> call, Response<List<USER>> response) {
                        Toast.makeText(context, "Successfully upgraded", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, AdminHome.class));
                    }

                    @Override
                    public void onFailure(Call<List<USER>> call, Throwable t) {
                        Toast.makeText(context, "Failure"+t, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

//    public void filter(ArrayList<USER> filteredModelList) {
//        mValues = filteredModelList;
//        notifyDataSetChanged();
//    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<USER> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mValueslist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (USER item : mValueslist) {
                    if (item.getNAME().toLowerCase().contains(filterPattern)) {

                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues.clear();
            mValues.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,mobile,points;
        Button send;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_name);
            mobile=itemView.findViewById(R.id.tv_mobile);
            points=itemView.findViewById(R.id.tv_Points);
            send=itemView.findViewById(R.id.btn_gift);
        }
    }

}
