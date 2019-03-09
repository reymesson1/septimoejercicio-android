package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Model.MainWalletService
import com.example.myapplication.Model.WalletType
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onResume() {

        doAsync {

            activityUiThread {

                Thread.sleep(2000)

                getMainWallet()

            }
        }

        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addtowallet.setOnClickListener{

            val intent = Intent(this@MainActivity,SecondActivity::class.java)
            startActivity(intent)
        }

        addexpense.setOnClickListener{

            val intent = Intent(this@MainActivity, ThirdActivity::class.java)
            startActivity(intent)
        }

        transaction_history.setOnClickListener{

            val intent = Intent(this@MainActivity, FourthActivity::class.java)
            startActivity(intent)
        }


    }

    fun getMainWallet(){

        var retrofit = Retrofit.Builder()
            .baseUrl("http://190.94.2.105:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var mainWalletService : MainWalletService = retrofit.create(MainWalletService::class.java)

        var call = mainWalletService.getMainWallet()

        call.enqueue(object : Callback<List<WalletType>> {
            override fun onFailure(call: Call<List<WalletType>>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: Call<List<WalletType>>, response: Response<List<WalletType>>) {

                Log.i("response", response.body().toString())

                var totalSum = 0

                response.body()!!.forEach {


                    Log.i("response", it.total.toString())

                    totalSum += it.total.toInt()


                }

                total.setText("USD$ $totalSum.00")

            }

        })

    }

}