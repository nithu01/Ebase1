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
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.PremiumRetailer;

public class CheckAllPremiumRetailerAdapter extends RecyclerView.Adapter<CheckAllPremiumRetailerAdapter.MyViewHolder> {

    private List<PremiumRetailer> mValues=new ArrayList<>();;
    Context context;

    public CheckAllPremiumRetailerAdapter(Context context, ArrayList<PremiumRetailer> arrayList) {

    this.context=context;
    this.mValues=arrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_points,parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,eff_point;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.month);
            address=itemView.findViewById(R.id.point);
            eff_point=itemView.findViewById(R.id.effective_point);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mValues.get(position).getMonth().substring(0,7));
        holder.address.setText(mValues.get(position).getCurrent_Point());

        if(mValues.get(position).getEffective_Point().equals(""))
        {
            holder.eff_point.setText("0");
        }else{
            holder.eff_point.setText(mValues.get(position).getEffective_Point());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
