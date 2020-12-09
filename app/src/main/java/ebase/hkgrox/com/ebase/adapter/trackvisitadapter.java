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
import ebase.hkgrox.com.ebase.bean.Vender;


public class trackvisitadapter extends RecyclerView.Adapter<trackvisitadapter.Myviewholder>{

    List<Vender> arrayList;
    Context context;

    public trackvisitadapter(Context context , List<Vender> list) {
        this.arrayList = list;
        this.context = context;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trackvisit,parent,false);

        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
            Vender report=arrayList.get(position);
            holder.phone.setText(report.getPhone());
            holder.party.setText(report.getParty());
            holder.contact_person.setText(report.getContact_person());
            holder.euro_order.setText(report.getE_order());
            holder.checkin.setText(report.getCheckin());
            holder.checkout.setText(report.getCheckout());
            holder.mobile2.setText(report.getMobile2());
            holder.orderdetails.setText(report.getOrderdetail());
            holder.checkin_date.setText(report.getCheckin_date());
            holder.checkin_time.setText(report.getCheckintime());
            holder.checkout_time.setText(report.getTime());
            holder.checkout_date.setText(report.getDate());
            holder.remarks.setText(report.getRemark());
          //  holder.status.setText(report.getStatus());
            holder.segment.setText(report.getSegment());
            holder.state.setText(report.getState());
            holder.area.setText(report.getArea());
            holder.city.setText(report.getCity());
            holder.email.setText(report.getEmail());
            holder.potential_pm.setText(report.getPotentail());
            holder.category.setText(report.getRetailer());
            holder.address.setText(report.getAddress());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filter(ArrayList<Vender> filteredModelList) {
        arrayList  = filteredModelList;
        notifyDataSetChanged();
    }


    public static class Myviewholder extends RecyclerView.ViewHolder {
        TextView phone,party,contact_person,checkin,checkout,mobile2,orderdetails,euro_order,checkin_date,checkout_date,checkin_time,checkout_time,remarks,status,segment,category,state,area,address,city,email,potential_pm;

        public Myviewholder(View itemView) {
            super(itemView);
            phone=(TextView)itemView.findViewById(R.id.phone);
            party=(TextView)itemView.findViewById(R.id.party);
            contact_person=(TextView)itemView.findViewById(R.id.contactperson);
            checkin=(TextView)itemView.findViewById(R.id.checkin);
            checkout=(TextView)itemView.findViewById(R.id.checkout);
            mobile2=(TextView)itemView.findViewById(R.id.mobile2);
            orderdetails=(TextView)itemView.findViewById(R.id.orderdetails);
            euro_order=(TextView)itemView.findViewById(R.id.order);
            checkin_date=(TextView)itemView.findViewById(R.id.checkindate);
            checkout_date=(TextView)itemView.findViewById(R.id.checkoutdate);
            checkin_time=(TextView)itemView.findViewById(R.id.checkintime);
            checkout_time=(TextView)itemView.findViewById(R.id.checkouttime);
            remarks=(TextView)itemView.findViewById(R.id.remarks);
         //   status=(TextView)itemView.findViewById(R.id.status);
            segment=(TextView)itemView.findViewById(R.id.segment);
            state=(TextView)itemView.findViewById(R.id.state);
            category=(TextView)itemView.findViewById(R.id.category);
            area=(TextView)itemView.findViewById(R.id.area);
            address=(TextView)itemView.findViewById(R.id.address);
            city=(TextView)itemView.findViewById(R.id.city);
            email=(TextView)itemView.findViewById(R.id.email);
            potential_pm=(TextView)itemView.findViewById(R.id.potential_pm);


        }
    }
}
