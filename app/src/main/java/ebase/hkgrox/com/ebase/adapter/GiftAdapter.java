package ebase.hkgrox.com.ebase.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ebase.hkgrox.com.ebase.Config;
import ebase.hkgrox.com.ebase.DeleteGiftApi;
import ebase.hkgrox.com.ebase.R;
import ebase.hkgrox.com.ebase.UpdateGiftApi;
import ebase.hkgrox.com.ebase.bean.CouponRedeem;
import ebase.hkgrox.com.ebase.bean.USER;
import ebase.hkgrox.com.ebase.util.AppUtil;
import ebase.hkgrox.com.ebase.util.MUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 */
public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> implements Filterable {

    private List<CouponRedeem> mValues;
    private final List<CouponRedeem> mValueslist;
    private final Context context;
    private final String textInvited = "Invited";
    Config config;
    String login_url2 =config.ip_url;
    String srn;

    public GiftAdapter(Context context, List<CouponRedeem> items) {
        this.context = context;
        mValues = items;
        mValueslist=new ArrayList<>(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adap_gifts, parent, false);
        return new ViewHolder(view);
    }


    public void filter(ArrayList<CouponRedeem> filteredModelList) {
        mValues = filteredModelList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CouponRedeem user = mValues.get(position);
        srn=user.getSrn();

        if (user.getAPPROVE() != null) {
            if (user.getAPPROVE().equalsIgnoreCase("yes") || user.getAPPROVE().equalsIgnoreCase("no")) {
                holder.btn_enable.setVisibility(View.GONE);
                holder.btn_disable.setVisibility(View.GONE);
            }else{
                holder.btn_enable.setVisibility(View.VISIBLE);
                holder.btn_disable.setVisibility(View.VISIBLE);
            }
        } else {
            holder.btn_enable.setVisibility(View.VISIBLE);
            holder.btn_disable.setVisibility(View.VISIBLE);
        }

        holder.btn_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getAPPROVE().equalsIgnoreCase("yes")){
                    dispatch(holder.getAdapterPosition(), "no");
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://api.whatsapp.com/send?phone="+"91"+user.getMOBILE()+"&text="+"Hello,your gift has been dispatched"+""));
                    context.startActivity(intent);
                    holder.btn_dispatch.setVisibility(View.GONE);
                }else{
                    Toast.makeText(context,"Gift is not approved",Toast.LENGTH_SHORT).show();
                }
//                try {
//                    String headerReceiver = "";// Replace with your message.
//                    String bodyMessageFormal = "";// Replace with your message.
//                    String whatsappContain = headerReceiver + bodyMessageFormal;
//
//                    Intent intent = new Intent ( Intent.ACTION_VIEW );
//                    intent.setData ( Uri.parse ( "https://wa.me/" + user.getMOBILE() + "Hello,You has been dispatched" + "" ) );
//                    context.startActivity ( intent );
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }

            }
        });
        holder.month_collection.setText("Gift - " + user.getGIFT());
        holder.tvName.setText("" + user.getNAME());
        holder.mobile.setText("" + user.getMOBILE());
        holder.date.setText(""+ user.getDATE());
        holder.month_sale.setText("Points - " + user.getPOINT() + "");
        holder.degination.setVisibility(View.GONE);

    }

    private void dispatch(final int adapterPosition, String value) {

        final CouponRedeem couponRedeem = mValues.get(adapterPosition);

        srn=couponRedeem.getSrn();

            //couponRedeem.setAPPROVE("NO");

            MUtil.showProgressDialog(context);
            mValues.remove(couponRedeem);
            Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
            DeleteGiftApi deleteGiftApi=retrofit.create(DeleteGiftApi.class);
            Call<List<CouponRedeem>> call=deleteGiftApi.dispatchgift(srn);

            call.enqueue(new Callback<List<CouponRedeem>>() {
                @Override
                public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
                    // MUtil.dismissProgressDialog();
//                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
//                    MUtil.showSnakbar(context, "Gift declined successfully.");

                }

                @Override
                public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {
                    //  Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
                    MUtil.showSnakbar(context, "Something went wrong.Please try again!");
                    MUtil.dismissProgressDialog();
                    notifyDataSetChanged();
                }

            });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CouponRedeem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mValueslist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CouponRedeem item : mValueslist) {
                    Log.d("TAG","filterdata"+item.getNAME().toLowerCase()+"\n"+filterPattern);
                    if (item.getMOBILE().toLowerCase().contains(filterPattern) || item.getNAME().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues.clear();
            mValues.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private AppCompatButton btn_enable;
        private AppCompatButton btn_disable;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            findViews(view);
        }

        private Button btn_dispatch;
        private TextView tvName;
        private TextView mobile;
        private TextView degination;
        private TextView month_sale;
        private TextView month_collection;
        private TextView date;
        private LinearLayout ll_list_item_contact;

        // private TextView tvInvite;
        // private View vBottom;
        private void findViews(View mView) {
            btn_dispatch=mView.findViewById(R.id.btn_dispatch);
            tvName = (TextView) mView.findViewById(R.id.tv_name);
            date=(TextView)mView.findViewById(R.id.textView5);
            mobile = (TextView) mView.findViewById(R.id.mobile);
            degination = (TextView) mView.findViewById(R.id.degination);
            month_sale = (TextView) mView.findViewById(R.id.tv_month_targets);
            month_collection = (TextView) mView.findViewById(R.id.month_collection);
            btn_enable = (AppCompatButton) mView.findViewById(R.id.btn_enable);


            btn_disable = (AppCompatButton) mView.findViewById(R.id.btn_disable);
            btn_disable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upload(getAdapterPosition(), "no");
                }
            });
            btn_enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = "yes";
                    upload(getAdapterPosition(), value);
                }
            });


        }


    }

    private void upload(final int adapterPosition, String value) {

        final CouponRedeem couponRedeem = mValues.get(adapterPosition);

        srn=couponRedeem.getSrn();
        if (value.equalsIgnoreCase("no")) {
            //couponRedeem.setAPPROVE("NO");

            MUtil.showProgressDialog(context);
            mValues.remove(couponRedeem);
            Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
            DeleteGiftApi deleteGiftApi=retrofit.create(DeleteGiftApi.class);
            Call<List<CouponRedeem>> call=deleteGiftApi.deletegift(srn);

            call.enqueue(new Callback<List<CouponRedeem>>() {
                             @Override
                             public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
                               // MUtil.dismissProgressDialog();
                                 Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
                                 MUtil.showSnakbar(context, "Gift declined successfully.");




                             }

                             @Override
                             public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {
                               //  Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
                                 MUtil.showSnakbar(context, "Gift declined successfully.");
                                 MUtil.dismissProgressDialog();
                                 notifyDataSetChanged();


                             }

                         });


                 /*   DatabaseReference databaseReference = AppUtil.getGiftReference((Activity) context);
            databaseReference.child(couponRedeem.getMOBILE()).setValue(couponRedeem, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    MUtil.dismissProgressDialog();
                    if (databaseError != null) {
                        MUtil.showSnakbar(context, "Gift decline failed try again ");
                    } else {
                        MUtil.showSnakbar(context, "Gift declined successfully.");
                        mValues.get(adapterPosition).setAPPROVE("no");
                        notifyDataSetChanged();
                    }
                }
            });
            */
        } else {

            //couponRedeem.setAPPROVE("YES");

          MUtil.showProgressDialog(context);
           mValues.remove(couponRedeem);

           Retrofit retrofit=new Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build();
           UpdateGiftApi updateGiftApi=retrofit.create(UpdateGiftApi.class);
           Call<List<CouponRedeem>> call=updateGiftApi.acceptgift(srn);
           call.enqueue(new Callback<List<CouponRedeem>>() {
               @Override
               public void onResponse(Call<List<CouponRedeem>> call, Response<List<CouponRedeem>> response) {
                   MUtil.dismissProgressDialog();
                 //  MUtil.showSnakbar(context, "Gift Accepted successfully.");
                  //

               }

               @Override
               public void onFailure(Call<List<CouponRedeem>> call, Throwable t) {
                   MUtil.dismissProgressDialog();
                   MUtil.showSnakbar(context, "Gift Accepted successfully.");
                   notifyDataSetChanged();




               }

           });

            /*DatabaseReference databaseReferencenew = AppUtil.getUserReference((Activity) context).child(couponRedeem.getMOBILE());
            databaseReferencenew.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    USER user = dataSnapshot.getValue(USER.class);
                    String points = user.getPOINTS();
                    String pointsGift = couponRedeem.getPOINT();
                    if (points != null && points.length() > 0) {
                        try {
                            int pointsInt = Integer.parseInt(points);
                            int pointsGiftInt = Integer.parseInt(pointsGift);
                            if (pointsInt > pointsGiftInt) {
                                int leftPoints = pointsInt - pointsGiftInt;
                                user.setPOINTS(String.valueOf(leftPoints));
                                updateUser(user, couponRedeem);
                            } else {
                                MUtil.showInfoAlertDialog(context, "User does not have much point");
                                MUtil.dismissProgressDialog();
                            }
                        } catch (Exception e) {
                            MUtil.showSnakbar(context, "Gift approve failed try again ");
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    MUtil.dismissProgressDialog();
                    MUtil.showSnakbar(context, "Gift approve failed try again ");
                }
            });
*/

        }

    }

    private void updateUser(USER user, final CouponRedeem couponRedeem) {

        // MUtil.showProgressDialog(context);
        DatabaseReference databaseReference = AppUtil.getUserReference((Activity) context);
        databaseReference.child(user.getMOBILE()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                // MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(context, "Gift approve failed try again ");
                } else {
                    // MUtil.showSnakbar(context, "Gift approved successfully.");
                    updateUser(couponRedeem);
                    // notifyDataSetChanged();
                }
            }
        });
    }


    private void updateUser(CouponRedeem couponRedeem) {

        // MUtil.showProgressDialog(context);
        DatabaseReference databaseReference = AppUtil.getGiftReference((Activity) context);
        databaseReference.child(couponRedeem.getMOBILE()).setValue(couponRedeem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                MUtil.dismissProgressDialog();
                if (databaseError != null) {
                    MUtil.showSnakbar(context, "Gift approve failed try again ");
                } else {
                    MUtil.showSnakbar(context, "Gift approved successfully.");
                    notifyDataSetChanged();
                }
            }
        });
    }
}
