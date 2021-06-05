package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Premium_Retailer;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;

public class Premium_Retailer_Adapter extends RecyclerView.Adapter<Premium_Retailer_Adapter.MyHolder> {
    Context context;
    List<USER> arraylist=new ArrayList<>();

    public Premium_Retailer_Adapter(Premium_Retailer premium_retailer, List<USER> arraylist) {
        this.context=context;
        this.arraylist=arraylist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_premium_retailer,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arraylist.get(position).getNAME());
        holder.points.setText(arraylist.get(position).getPOINTS());
        holder.mobile.setText(arraylist.get(position).getMOBILE());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,points,mobile;
        Button send;
        public MyHolder(View view) {
            super(view);
            name=itemView.findViewById(R.id.tv_name);
            points=itemView.findViewById(R.id.point);
            mobile=itemView.findViewById(R.id.mobile);
        }
    }
}
