package co.lavapp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_fourth.*
import kotlinx.android.synthetic.main.layout_item_history.view.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        doAsync {

            activityUiThread {

                Thread.sleep(2000)

                getWallet()
            }
        }

    }

    fun getWallet(){

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var walletService : WalletService = retrofit.create(WalletService::class.java)

        var call = walletService.getWallet()

        call.enqueue(object : Callback<List<Wallet>>{
            override fun onFailure(call: Call<List<Wallet>>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: Call<List<Wallet>>, response: Response<List<Wallet>>) {
                Log.i("response", response.body().toString())

                response.body()!!.forEach {

                    var item = layoutInflater.inflate(R.layout.layout_item_history, null)

                    item.nameTXTHistory.setText(it.name)

                    item.amountTXTHistory.setText(it.amount.toString())

                    item.accountTXTHistory.setText(it.account)

                    scContentHistory.addView(item)
                }

            }


        })


    }

}
