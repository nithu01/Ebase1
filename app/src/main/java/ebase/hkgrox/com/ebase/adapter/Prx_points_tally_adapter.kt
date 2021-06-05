package ebase.hkgrox.com.ebase.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ebase.hkgrox.com.ebase.R
import ebase.hkgrox.com.ebase.bean.CouponRedeem
import ebase.hkgrox.com.ebase.ui.Before_Premium_Retailer
import ebase.hkgrox.com.ebase.ui.CheckAllMonthPremiumRetailer
import ebase.hkgrox.com.ebase.ui.Coupondetails

class Prx_points_tally_adapter(var arraylist : ArrayList<CouponRedeem>,var context : Context) : RecyclerView.Adapter<Prx_points_tally_adapter.Viewhlder>() {

    class Viewhlder(view: View) : RecyclerView.ViewHolder(view){
        var tp : TextView = view.findViewById(R.id.tpoint)
        var apr : TextView = view.findViewById(R.id.apr)
        var bpr : TextView = view.findViewById(R.id.bpr)
        var gp : TextView = view.findViewById(R.id.gp)
        var name : TextView = view.findViewById(R.id.name)
        var mobile : TextView = view.findViewById(R.id.mobile)
        var remaining : TextView = view.findViewById(R.id.remaining)
        var btn_retailer_points : TextView = view.findViewById(R.id.btn_rp)
        var btn_pr_retailer : TextView = view.findViewById(R.id.btn_pr_rp)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhlder {
       val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_pr_tally,parent,false)
        return Viewhlder(view)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: Viewhlder, position: Int) {
      //  Log.d("TAG","DATEAAAA"+arraylist.get(position).gp)

        if(arraylist.get(position).gp==null){
            holder.apr.text = "GP:"+"0"
        }else{
            holder.gp.text = "GP:"+arraylist.get(position).gp
        }

        if(arraylist.get(position).tp==null){
            holder.apr.text = "TP:"+"0"
        }else{
            holder.tp.text ="TP:"+ arraylist.get(position).tp
        }

        if(arraylist.get(position).apr==null){
            holder.apr.text = "Pr. Retailer:"+"0"
        }else{
            holder.apr.text ="Pr. Retailer:"+ arraylist.get(position).apr
        }

        if(arraylist.get(position).bpr==null){
            holder.bpr.text = "Old Points:"+"0"
        }else{
            holder.bpr.text ="Old Points:"+ arraylist.get(position).bpr
        }
        if(arraylist.get(position).tp==null||arraylist.get(position).gp==null){
            holder.remaining.text ="Remaining Points:"+ "0"

        }else{
            holder.remaining.text ="Remaining Points:"+ (arraylist.get(position).tp.toInt()-arraylist.get(position).gp.toInt()).toString()
        }
        holder.name.text=arraylist.get(position).name
        holder.mobile.text=arraylist.get(position).mobile

     //   holder.remaining.text = (arraylist.get(position).tp.toInt()-arraylist.get(position).gp.toInt()).toString()

        holder.btn_pr_retailer.setOnClickListener {
            context!!.startActivity(Intent(context, Coupondetails::class.java).putExtra("mobile",arraylist.get(position).mobile).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }

        holder.btn_retailer_points.setOnClickListener {
            context!!.startActivity(Intent(context, Before_Premium_Retailer::class.java).putExtra("mobile",arraylist.get(position).mobile).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }
    }
}