package ebase.hkgrox.com.ebase.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.CouponAdminUserDetail;
import ebase.hkgrox.com.ebase.GiftAdminUserDetail;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;


/**
 */
public class AdapterExecutives extends RecyclerView.Adapter<AdapterExecutives.ViewHolder> {

    private List<USER> mValues;
    private final Context context;
    private final String textInvited = "Invited";

    public AdapterExecutives(Context context, List<USER> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adap_executive_admin, parent, false);
        return new ViewHolder(view);
    }


    public void filter(ArrayList<USER> filteredModelList) {
        mValues  = filteredModelList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final USER user = mValues.get(position);
       // holder.month_collection.setVisibility(View.GONE);
        String localtype = user.getDEGINATION();
      /*  if(localtype.toLowerCase().contains("executive")) {
            holder.tvName.setText("" + user.getNAME());
            holder.mobile.setText("" + user.getMOBILE());
            holder.month_sale.setText("SSTG - "+user.getSSTG()+" L");
            holder.degination.setText("Executive");

        }

        if(localtype.toLowerCase().contains("user")) {*/


            holder.tvName.setText("" + user.getNAME());
            holder.mobile.setText("" + user.getMOBILE());
//            Toast.makeText(context,""+user.getDEGINATION(),Toast.LENGTH_SHORT).show();
            if(user.getDEGINATION().equals("Premium Retailer")){
                holder.month_sale.setText("Total Points - "+(Integer.parseInt(user.getTP())+Integer.parseInt(user.getTP())));

            }else{
                holder.month_sale.setText("Total Points - "+user.getTP()+"");

            }
        holder.tvgp.setText("Gift Redeemed - "+user.getGP()+"");
        holder.tvrp.setText("Remaining Points - "+user.getRP()+"");
            holder.degination.setVisibility(View.GONE);
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,""+user.getMOBILE(),Toast.LENGTH_SHORT).show();
                Intent detail=new Intent(context, CouponAdminUserDetail.class);
                detail.putExtra("DATA",user);
                context.startActivity(detail);

            }
        });
        holder.btngift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,""+user.getMOBILE(),Toast.LENGTH_SHORT).show();
                Intent detail=new Intent(context, GiftAdminUserDetail.class);
                detail.putExtra("DATA",user);
                context.startActivity(detail);
            }
        });

       // }
/*
        if(localtype.toLowerCase().contains("manager")) {
            holder.tvName.setText("" + user.getNAME());
            holder.month_sale.setText("MST - " + user.getMONTHALY_SALE() + " ₹");
            holder.month_collection.setText("MCT - " + user.getMONTHALY_COLLECTION() + " L");
            holder.mobile.setText("" + user.getMOBILE());
            holder.month_collection.setVisibility(View.VISIBLE);
            holder.degination.setText("Manager");
        }

        if(user.getENABLE()==null || user.getENABLE().equalsIgnoreCase("true")){
            holder.btn_enable.setText("Disable");
        }else{
            holder.btn_enable.setText("Enable");
        }
*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private AppCompatButton btn_enable;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            findViews(view);
        }

        private Button btndetail,btngift;
        private TextView tvName;
        private TextView mobile;
        private TextView degination;
        private TextView month_sale,tvgp,tvrp;
        private TextView month_collection;
        private AppCompatButton btn_change_password;
        ;

        private LinearLayout ll_list_item_contact;

        // private TextView tvInvite;
        // private View vBottom;
        private void findViews(View mView) {
            tvName = (TextView) mView.findViewById(R.id.tv_name);
            mobile = (TextView) mView.findViewById(R.id.mobile);
            degination = (TextView) mView.findViewById(R.id.degination);
            month_sale = (TextView) mView.findViewById(R.id.tv_month_targets);
          ///  month_collection = (TextView) mView.findViewById(R.id.month_collection);
           // btn_enable = (AppCompatButton) mView.findViewById(R.id.btn_enable);


            tvgp=(TextView)mView.findViewById(R.id.tv_gp);
            tvrp=(TextView)mView.findViewById(R.id.tv_rp);
            btndetail=(Button)mView.findViewById(R.id.btn_detail);
            btngift=(Button)mView.findViewById(R.id.btn_gift);

/*
           // btn_change_password = (AppCompatButton) mView.findViewById(R.id.btn_change_password);
           // btn_change_password.setVisibility(View.GONE);
            btn_change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ChangePasswordActivityVender.class);
                    intent.putExtra("DATA", mValues.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

            btn_enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String value = "";
                        if(btn_enable.getText().toString().equalsIgnoreCase("Enable")){
                            value ="true";
                        }else{
                            value = "false";
                        }

                       upload(getAdapterPosition(),value);


                    }
            });
*/
                


        }


    }

    private void upload(int adapterPosition, String value) {


        USER user = mValues.get(adapterPosition);
        user.setENABLE(value);
        MUtil.showProgressDialog(context);
        DatabaseReference databaseReference = AppUtil.getUserReference((Activity) context);
              databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(context, "User updated failed try again ");
                } else {
                    MUtil.showSnakbar(context, "User updated successfully.");
                    notifyDataSetChanged();
                }
            }
        });

    }
}
