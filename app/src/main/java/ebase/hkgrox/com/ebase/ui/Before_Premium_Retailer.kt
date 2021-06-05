package ebase.hkgrox.com.ebase.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ebase.hkgrox.com.ebase.Config
import ebase.hkgrox.com.ebase.R
import ebase.hkgrox.com.ebase.UserCouponApi
import ebase.hkgrox.com.ebase.adapter.AdminUserCouponAdapter
import ebase.hkgrox.com.ebase.bean.COUPON
import ebase.hkgrox.com.ebase.util.MUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Before_Premium_Retailer : AppCompatActivity() {

    var linearLayout: LinearLayout? = null
    private var rv_list: RecyclerView? = null
    private var sv_search: SearchView? = null
    var config: Config? = null
    var txt_curr: TextView? = null
    var txt_effect_point:TextView? = null
    var txt_total:TextView? = null
    var txt_check:TextView? = null
    var login_url2 = Config.ip_url
    var phn : String ? =null
    //private String login_url2 ="http://192.168.0.103";
    private var adapter: AdminUserCouponAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before__premium__retailer)
//        MUtil.setToolBarNew(this, "Coupon Details", true)
        txt_curr = findViewById(R.id.month_point)
        txt_effect_point = findViewById(R.id.effective_point)
        txt_total = findViewById(R.id.total)
        rv_list = findViewById<View>(R.id.rv_list) as RecyclerView
        rv_list?.setLayoutManager(LinearLayoutManager(this))
        sv_search = findViewById<View>(R.id.sv_search) as SearchView
        sv_search?.setVisibility(View.GONE)
        phn=intent.getStringExtra("mobile")
        MUtil.showProgressDialog(this)
        val arraylist: MutableList<COUPON> = ArrayList()
        val retrofit = Retrofit.Builder().baseUrl(login_url2).addConverterFactory(GsonConverterFactory.create()).build()
        val userCouponApi = retrofit.create(UserCouponApi::class.java)
        val call = userCouponApi.oldreport(phn)
        call.enqueue(object : Callback<List<COUPON>> {
            override fun onResponse(call: Call<List<COUPON>>, response: Response<List<COUPON>>) {
                val list = response.body()!!
                var cou: COUPON? = null
                for (i in list.indices) {
                    cou = COUPON()
                    val date = list[i].date_availed
                    val coupon = list[i].coupon
                    val point = list[i].points
                    cou.date_availed = date
                    cou.coupon = coupon
                    cou.points = point
                    arraylist.add(cou)
                }
                adapter = AdminUserCouponAdapter(this@Before_Premium_Retailer, arraylist)
                rv_list!!.adapter = adapter
                MUtil.dismissProgressDialog()
            }

            override fun onFailure(call: Call<List<COUPON>>, t: Throwable) {

            }
        })
    }

}