package ebase.hkgrox.com.ebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ebase.hkgrox.com.ebase.bean.CouponRedeem;

/**
 * Created by Zooom HKG on 1/12/2018.
 */

class AdminUserGiftAdapter extends RecyclerView.Adapter<AdminUserGiftAdapter.ViewHolder> {
    List<CouponRedeem> mCoupon;
    Context context;

    public AdminUserGiftAdapter(Context context, List<CouponRedeem> arraylist) {
        mCoupon=arraylist;
        this.context=context;
    }

    @Override
    public AdminUserGiftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_gift_detail,parent,false);
        return new AdminUserGiftAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminUserGiftAdapter.ViewHolder holder, int position) {

        CouponRedeem coupon=mCoupon.get(position);
        holder.tvdate.setText(coupon.getDATE());
        holder.tvpoint.setText(coupon.getPOINT());
        holder.tvgift.setText(coupon.getGIFT());
        holder.tvapprove.setText(coupon.getAPPROVE());

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
        private TextView tvdate,tvgift,tvapprove,tvpoint;
        private void findViews(View itemView) {
            tvdate=(TextView)itemView.findViewById(R.id.tvdate);
            tvgift=(TextView)itemView.findViewById(R.id.tvgift);
            tvpoint=(TextView)itemView.findViewById(R.id.tvpoint);
            tvapprove=(TextView)itemView.findViewById(R.id.tvapprove);
        }
    }
}

