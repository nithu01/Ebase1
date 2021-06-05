package ebase.hkgrox.com.ebase.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.ui.UserAddress;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> implements Filterable {

    private List<USER> mValues=new ArrayList<>();;
    Context context;
    private List<USER> mValueslist;

    public AddressAdapter(Context context, List<USER> items) {
        this.context=context;
        mValues = items;
        mValueslist=new ArrayList<>(mValues);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(mValues.get(position).getNAME());
        holder.address.setText(mValues.get(position).getADDRESS());
        holder.city.setText(mValues.get(position).getCITY());
        holder.state.setText(mValues.get(position).getSTATE());
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+"91"+mValues.get(position).getMOBILE()+"&text="+"Hello,your gift has been dispatched"+""));
                context.startActivity(intent);
                holder.send.setVisibility(View.GONE);
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

    public void filter(ArrayList<USER> filteredModelList) {
        mValues = filteredModelList;
        notifyDataSetChanged();
    }

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

//    private Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                List<USER> filteredList = new ArrayList<>();
//                if (charSequence == null || charSequence.length() == 0) {
//                    filteredList.addAll(mValueslist);
//
//                } else {
//                    Log.d("TAG","filterdata"+mValues.get(0).getNAME());
//
//                    for (USER row : mValues) {
//
//                        String filterPattern = charSequence.toString().toLowerCase().trim();
//                        Log.d("TAG","filterdata"+row.getNAME().toLowerCase()+"\n"+filterPattern);
//                        if (row.getNAME().toLowerCase().contains(filterPattern)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    mValueslist = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mValueslist.addAll((Collection<? extends USER>) filterResults.values);
//                notifyDataSetChanged();
//            }
//
//    };

//    public void filter(ArrayList<USER> filteredModelList) {
//        mValues = filteredModelList;
//        notifyDataSetChanged();
//    }
//
//    private Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<USER> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(mValueslist);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (USER item : mValueslist) {
//                    if (item.getNAME().toLowerCase().contains(filterPattern)) {
//
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mValues.addAll ((List) results.values);
//            notifyDataSetChanged();
//        }
//    };


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,city,state;
        Button send;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_name);
            address=itemView.findViewById(R.id.address);
            city=itemView.findViewById(R.id.city);
            state=itemView.findViewById(R.id.state);
            send=itemView.findViewById(R.id.btn_enable);
        }
    }

}
