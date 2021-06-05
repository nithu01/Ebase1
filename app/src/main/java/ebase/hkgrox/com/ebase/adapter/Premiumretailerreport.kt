package ebase.hkgrox.com.ebase.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ebase.hkgrox.com.ebase.R
import ebase.hkgrox.com.ebase.bean.PremiumRetailer
import ebase.hkgrox.com.ebase.bean.ReportReponse
import ebase.hkgrox.com.ebase.ui.CheckAllMonthPremiumRetailer
import java.util.*

class Premiumretailerreport(val list: MutableList<out ReportReponse>, ctx: Context) : RecyclerView.Adapter<Premiumretailerreport.MyHolder>() , Filterable {

    var i : Int ?= 1
    var arrayList = ArrayList<PremiumRetailer>()
    var context : Context ? =null
    var countryFilterList = ArrayList<ReportReponse>()

    init {
        this.context=ctx
        countryFilterList = list as ArrayList<ReportReponse>
    }

    class MyHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        var id:TextView=itemview.findViewById(R.id.sno)
        var name:TextView=itemview.findViewById(R.id.name)
        var date_of_joining:TextView=itemview.findViewById(R.id.date_of_joing)
        var distributor:TextView=itemview.findViewById(R.id.distributor)
        var points:TextView=itemview.findViewById(R.id.points)
        var mode:TextView=itemview.findViewById(R.id.mode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Premiumretailerreport.MyHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_premium_report,parent,false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    override fun onBindViewHolder(holder: Premiumretailerreport.MyHolder, position: Int) {
       holder.id.text= (holder.adapterPosition+1).toString()
       holder.name.text=countryFilterList.get(position).name
       holder.name.setPaintFlags(holder.name.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
       holder.date_of_joining.text=countryFilterList.get(position).dob

        if(countryFilterList.get(position).mode.equals("yes"))
        {
            holder.mode.text="Upgrade"

        }else{
            holder.mode.text="Registeration"
        }

        holder.name.setOnClickListener {
            context!!.startActivity(Intent(context, CheckAllMonthPremiumRetailer::class.java).putExtra("DATA",list.get(position).mobile))
        }

        holder.distributor.text=countryFilterList.get(position).distributor

        if(list.get(position).point == null)
        {
            holder.points.text="0"
        }else{
           holder.points.text= countryFilterList.get(position).point
        }


       i!!.plus(1)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = list as ArrayList<ReportReponse>
                } else {
                    val resultList = ArrayList<ReportReponse>()
                    for (row in list) {
                        if (row.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<ReportReponse>
                notifyDataSetChanged()
            }
        }
    }
}