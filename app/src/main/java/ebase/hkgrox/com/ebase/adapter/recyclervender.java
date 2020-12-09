package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.bean.Vender;
import ebase.hkgrox.com.ebase.ui.Vendordetail;

public class recyclervender extends RecyclerView.Adapter<recyclervender.Myholder> {

    USER user;
    List<Vender> list;
    Context context;

    public recyclervender(Context applicationContext, List<Vender> arraylist, USER user) {
        this.context=applicationContext;
        list=arraylist;
        this.user=user;

    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vender,parent,false);
        return new Myholder(view);
    }
    public void filter(ArrayList<Vender> filteredModelList) {
        list  = filteredModelList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final Myholder holder, final int position) {
        final Vender vender=list.get(position);
        holder.vender.setText(vender.getParty());
        holder.phone.setText(vender.getPhone());
        holder.status.setText(vender.getTime());
        holder.date.setText(vender.getDate());
        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Vendordetail.class);
                intent.putExtra("DATA",vender);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView vender,phone,status,date;
        RelativeLayout linearlayout;
        public Myholder(View itemView) {
            super(itemView);
            vender=(TextView)itemView.findViewById(R.id.vender);
            phone=(TextView)itemView.findViewById(R.id.phone);
            status=(TextView)itemView.findViewById(R.id.status);
            date=(TextView)itemView.findViewById(R.id.date);
            linearlayout=(RelativeLayout)itemView.findViewById(R.id.ll_list_item_contact);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
