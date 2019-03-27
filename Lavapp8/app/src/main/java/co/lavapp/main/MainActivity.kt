package co.lavapp.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        var globalVar = "EN"
    }

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

        setting.setOnClickListener{

            val intent = Intent(this@MainActivity, MapsActivity::class.java )
            startActivity(intent)
        }
    }

    fun getMainWallet(){

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
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

                var peso = totalSum * 50

                if(globalVar=="EN"){

                    total.setText("USD$ $totalSum.00")
                }else{

                    total.setText("RD$ $peso.00")
                }


            }

        })

    }

}
