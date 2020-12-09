package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.COUPON;

/**
 * Created by Zooom HKG on 12/29/2017.
 */

public class AdminUserCouponAdapter extends RecyclerView.Adapter<AdminUserCouponAdapter.ViewHolder> {
    List<COUPON> mCoupon;
    Context context;

    public AdminUserCouponAdapter(Context context, List<COUPON> arraylist) {
        mCoupon=arraylist;
        this.context=context;
    }

    @Override
    public AdminUserCouponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_coupon_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminUserCouponAdapter.ViewHolder holder, int position) {

        COUPON coupon=mCoupon.get(position);
        holder.tvdate.setText(coupon.getDate_availed());
        holder.tvpoint.setText(coupon.getPoints());
        holder.tvcoupon.setText(coupon.getCoupon());

    }

    @Override
    public int getItemCount() {
        return mCoupon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            findViews(itemView);
        }
            private TextView tvdate,tvcoupon,tvpoint;
        private void findViews(View itemView) {
            tvdate=(TextView)itemView.findViewById(R.id.tvdate);
            tvcoupon=(TextView)itemView.findViewById(R.id.tvcoupon);
            tvpoint=(TextView)itemView.findViewById(R.id.tvpoint);
        }
    }
}
