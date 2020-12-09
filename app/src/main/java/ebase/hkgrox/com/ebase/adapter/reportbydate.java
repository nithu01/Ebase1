package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.Vender;

public class reportbydate extends RecyclerView.Adapter<reportbydate.Myholder> {

    List<Vender> list;
    Context context;

    public reportbydate(Context context, List<Vender> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reportdate,parent,false);

        return new Myholder(view);
    }
    public void filter(ArrayList<Vender> filteredModelList) {
        list  = filteredModelList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        Vender report=list.get(position);
        holder.party.setText(report.getParty());
        holder.ret.setText(report.getRetailer());
        holder.seg.setText(report.getSegment());
        holder.contp.setText(report.getContact_person());
        holder.phone.setText(report.getVender_phone());
        holder.potpm.setText(report.getPotentail());
        holder.order.setText(report.getE_order());
        holder.date.setText(report.getDate());
     //   holder.status.setText(report.getStatus());
        holder.address.setText(report.getAddress());
        holder.area.setText(report.getArea());
        holder.city.setText(report.getCity());
        holder.pi.setText(report.getPincode());
        holder.remark.setText(report.getRemark());
        holder.time.setText(report.getTime());
        holder.checkin.setText(report.getCheckin());
        holder.checkout.setText(report.getCheckout());
        holder.mobile2.setText(report.getMobile2());
        holder.email.setText(report.getEmail());
        holder.orderdt.setText(report.getOrderdetail());
        holder.checkintime.setText(report.getCheckintime());
        holder.ename.setText(report.getE_name());
        holder.ephone.setText(report.getE_phone());
        holder.checkindate.setText(report.getCheckin_date());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView party,ret,seg,contp,phone,potpm,order,status,address,area,city,pi,remark,date,time,checkintime,checkout,mobile2,email,checkin,checkindate,ename,ephone,orderdt;
        public Myholder(View itemView) {
            super(itemView);

            checkindate=(TextView)itemView.findViewById(R.id.checkindate);
            ename=(TextView)itemView.findViewById(R.id.ename);
            ephone=(TextView)itemView.findViewById(R.id.ephone);
            checkintime=(TextView)itemView.findViewById(R.id.checkintime);
            date=(TextView)itemView.findViewById(R.id.date);
            party=(TextView)itemView.findViewById(R.id.party);
            ret=(TextView)itemView.findViewById(R.id.retwrk);
            seg=(TextView)itemView.findViewById(R.id.segment);
            contp=(TextView)itemView.findViewById(R.id.contactperson);
            phone=(TextView)itemView.findViewById(R.id.phone);
            potpm=(TextView)itemView.findViewById(R.id.pot_pm);
            order=(TextView)itemView.findViewById(R.id.e_order);
         //   status=(TextView)itemView.findViewById(R.id.status);
            address=(TextView)itemView.findViewById(R.id.address);
            area=(TextView)itemView.findViewById(R.id.area);
            time=(TextView)itemView.findViewById(R.id.time);
            mobile2=(TextView)itemView.findViewById(R.id.mobile2);
            checkin=(TextView)itemView.findViewById(R.id.checkin);
            checkout = (TextView) itemView.findViewById(R.id.checkout);
            email=(TextView)itemView.findViewById(R.id.email);
            orderdt=(TextView)itemView.findViewById(R.id.orderd);
            city=(TextView)itemView.findViewById(R.id.city);
            pi=(TextView)itemView.findViewById(R.id.pincode);
            remark=(TextView)itemView.findViewById(R.id.remarks);


            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout);
        }
    }
}
