package ebase.hkgrox.com.ebase.adapter;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;


/**
 */
public class Compiled extends RecyclerView.Adapter<Compiled.ViewHolder> {

    private List<USER> mValues;
    private final Context context;
    private final String textInvited = "Invited";

    public Compiled(Context context, List<USER> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adap_compiled, parent, false);
        return new ViewHolder(view);
    }


    public void filter(ArrayList<USER> filteredModelList) {
        mValues  = filteredModelList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        USER user = mValues.get(position);

        holder.tvName.setText(user.getNAME());
        holder.ssm.setText(user.getCURRENT_SSTG());

        holder.sstg.setText(user.getSSTG());

       if(user.getDATE()!=null && user.getDATE().equalsIgnoreCase(""+MUtil.getCurrentYear()+"-" +MUtil.getCurrentMonth()+"-"+MUtil.getDayOfMonth())){
           holder.sst.setText(user.getTODAY_SSTG());
       }else{
           holder.sst.setText("");
       }
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


        private TextView tvName;
        private TextView sstg;
        private TextView ssm;
        private TextView sst;


        private LinearLayout ll_list_item_contact;

        // private TextView tvInvite;
        // private View vBottom;
        private void findViews(View mView) {
            tvName = (TextView) mView.findViewById(R.id.name);
            sstg = (TextView) mView.findViewById(R.id.sstg);
            ssm = (TextView) mView.findViewById(R.id.ssm);
            sst = (TextView) mView.findViewById(R.id.sst);

                


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
