package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.GIFTS;
import ebase.hkgrox.com.ebase.ui.Giftstatus;

public class Recylerviewgiftadapter extends RecyclerView.Adapter<Recylerviewgiftadapter.MyViewHolder> {

    List<GIFTS> list;
    Context context;

    public Recylerviewgiftadapter(Context context , ArrayList<GIFTS> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GIFTS gifts=list.get(position);
        holder.approve.setText(gifts.getApprove());
        holder.date.setText(gifts.getDate());
        holder.gift.setText(gifts.getGift());
        holder.mobile.setText(gifts.getMobile());
        holder.name.setText(gifts.getName());
        holder.point.setText(gifts.getPoints());
    }
    public void filter(List<GIFTS> filteredModelList) {
        list  = filteredModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView approve,date,gift,mobile,name,point;
        public MyViewHolder(View itemView) {
            super(itemView);
            approve=(TextView)itemView.findViewById(R.id.approve);
            date=(TextView)itemView.findViewById(R.id.date);
            gift=(TextView)itemView.findViewById(R.id.gift);
            mobile=(TextView)itemView.findViewById(R.id.mobile);
            name=(TextView)itemView.findViewById(R.id.name);
            point=(TextView)itemView.findViewById(R.id.point);
        }
    }
}
