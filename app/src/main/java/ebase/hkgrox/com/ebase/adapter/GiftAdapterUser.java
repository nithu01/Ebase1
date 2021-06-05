package ebase.hkgrox.com.ebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.ui.AvailedGiftUser;

/**
 * Created by Zooom HKG on 12/18/2017.
 */

public class GiftAdapterUser extends RecyclerView.Adapter<GiftAdapterUser.ViewHolder>{

    private List<CouponRedeem> mValues;
    private final Context context;
    Config config;
    String login_url2 =config.ip_url;
    String srn;

    public GiftAdapterUser(Context context, List<CouponRedeem> mValues) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public GiftAdapterUser.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_gift_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GiftAdapterUser.ViewHolder holder, int position) {
        CouponRedeem user = mValues.get(position);
        srn=user.getSrn();
        holder.tvDate.setText(user.getDATE());
        holder.tvPoint.setText(user.getPOINT());
        holder.tvGift.setText(user.getGIFT());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            findViews(view);
        }
        TextView tvDate,tvPoint,tvGift;
        private void findViews(View mView) {
            tvDate=(TextView)mView.findViewById(R.id.tv_date);
            tvPoint=(TextView)mView.findViewById(R.id.tv_point);
            tvGift=(TextView)mView.findViewById(R.id.tv_gift);
        }
    }

}
