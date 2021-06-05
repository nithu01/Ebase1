package ebase.hkgrox.com.ebase.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ebase.hkgrox.com.ebase.ApiService
import ebase.hkgrox.com.ebase.Config
import ebase.hkgrox.com.ebase.R
import ebase.hkgrox.com.ebase.adapter.Prx_points_tally_adapter
import ebase.hkgrox.com.ebase.bean.CouponRedeem
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Pr_points_tally : AppCompatActivity() {

    var recyclerview : RecyclerView ? = null
    lateinit var arrayList : ArrayList<CouponRedeem>
    lateinit var adapter : Prx_points_tally_adapter
    lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pr_points_tally)
        findviews()
        setadapter()
        getdata()
    }

    fun findviews(){
        recyclerview = findViewById(R.id.recyclerview)
        arrayList = ArrayList()
        progressDialog = ProgressDialog(this@Pr_points_tally)
    }
    fun setadapter(){
//        adapter = Prx_points_tally_adapter(arrayList)
//        recyclerview?.layoutManager = LinearLayoutManager(this)
//        recyclerview?.adapter = adapter
    }
    fun getdata(){
        progressDialog.show()
        val client = OkHttpClient.Builder()
                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS).build()
        // read timeout
        //Toast.makeText(this@Pr_points_tally,"Failurwwe",Toast.LENGTH_SHORT).show()
        val retrofit = Retrofit.Builder().baseUrl(Config.ip_url).client(client).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService : ApiService = retrofit.create(ApiService::class.java)
        val call : Call<List<CouponRedeem>> = apiService.pr_tally()
        call.enqueue( object : Callback<List<CouponRedeem>>{
            override fun onFailure(call: Call<List<CouponRedeem>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@Pr_points_tally,""+t,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<CouponRedeem>>, response: retrofit2.Response<List<CouponRedeem>>) {
                progressDialog.dismiss()
            //    Log.d("TAG","DATEBBBBB")
            //Toast.makeText(this@Pr_points_tally,"Success",Toast.LENGTH_SHORT).show()
                var couponRedeem = CouponRedeem()
                for(i in 0 until response.body()!!.size){
                    couponRedeem = CouponRedeem()
                    couponRedeem.name = response.body()?.get(i)?.name
                    couponRedeem.mobile = response.body()?.get(i)?.mobile
                    couponRedeem.apr = response.body()?.get(i)?.apr
                    couponRedeem.bpr = response.body()?.get(i)?.bpr
                    couponRedeem.tp = response.body()?.get(i)?.tp
                    couponRedeem.gp = response.body()?.get(i)?.gp
                    //  Toast.makeText(this@Pr_points_tally,"Success"+response.body()?.get(i)?.gp,Toast.LENGTH_SHORT).show()
//                    Log.d("TAG","DATEBBBBB"+response.body()?.get(i)?.apr)
                    arrayList.add(couponRedeem)
                }
                adapter = Prx_points_tally_adapter(arrayList,applicationContext)
                recyclerview?.layoutManager = LinearLayoutManager(this@Pr_points_tally)
                recyclerview?.adapter = adapter
            }
        })
    }
}