package co.lavapp.main

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.layout_add_wallet.view.*
import kotlinx.android.synthetic.main.layout_item.view.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SecondActivity : AppCompatActivity() {

    override fun onResume() {

        doAsync {

            activityUiThread {

                Thread.sleep(2000)
                getWallet()
            }
        }


        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        /***
         *
         *
         * Create Income Button
         *
         * */

        button_add.setOnClickListener{

            var alertDialog = AlertDialog.Builder(this)

            var dialogView = layoutInflater.inflate(R.layout.layout_add_wallet,null)

            val stringList = ArrayList<String>()
            stringList.add("Camisa")
            stringList.add("Pantalon")
            stringList.add("Blusa")
            stringList.add("Bata")
            stringList.add("Pantalon corto")
            stringList.add("Polo")
            stringList.add("Cortinas")

            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line, stringList
            )

            dialogView.name.setAdapter(adapter)

            alertDialog.setView(dialogView)

            alertDialog.setPositiveButton("Save", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    var name = dialogView.name.text.toString()
                    var amount = dialogView.account.text.toString()

                    setWallet(name,amount)

                }

            })

            alertDialog.show()



        }


    }

    fun getWallet(){

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var incomeWalletService : IncomeWalletService = retrofit.create(IncomeWalletService::class.java)

        var call = incomeWalletService.getIncome()

        call.enqueue(object : Callback<List<Wallet>> {
            override fun onFailure(call: Call<List<Wallet>>, t: Throwable) {
                Log.i("error",t.toString())
            }

            override fun onResponse(call: Call<List<Wallet>>, response: Response<List<Wallet>>) {
                Log.i("response", response.body().toString())

                response.body()!!.forEach { at ->

                    var item = layoutInflater.inflate(R.layout.layout_item, null)

                    item.nameTXT.setText(at.name)

                    item.amountTXT.setText(at.amount.toString())

                    /***
                     *
                     *
                     * Edit Income Button
                     *
                     * */

                    item.button_edit.setOnClickListener{ it ->

                        var alertDialog = AlertDialog.Builder(this@SecondActivity)

                        var dialogView = layoutInflater.inflate(R.layout.layout_add_wallet,null)

                        dialogView.name.setText(item.nameTXT.text.toString())
                        dialogView.account.setText(item.amountTXT.text.toString())

                        alertDialog.setPositiveButton("Save", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                                var name = dialogView.name.text.toString()

                                var amount = dialogView.account.text.toString()

                                editWallet(at.id, name, amount.toInt())

                            }

                        })

                        alertDialog.setView(dialogView)

                        alertDialog.show()

                    }

                    /***
                     *
                     *
                     * Remove Income Button
                     *
                     * */

                    item.button_remove.setOnClickListener{

                        removeWallet(at.id)

                    }

                    scContent.addView(item)

                }


            }


        })


    }


    fun setWallet(name: String, amount : String) {

        var newWallet = Wallet()

        var date = Date()

        newWallet.id = date.time.toString()
        newWallet.date = date.toString()
        newWallet.name = name
        newWallet.amount = amount.toInt()
        newWallet.account = "Income"

        var json = Gson().toJson(newWallet)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var postwalletService: PostWalletService = retrofit.create(PostWalletService::class.java)

        var call = postwalletService.setWallet(JSONObject(json))

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("response", response.body().toString())
                recreate()
            }
        })
    }

    fun removeWallet(id: String) {

        var newWallet = Wallet()

        newWallet.id = id

        var json = Gson().toJson(newWallet)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var removewalletService: RemoveWalletService = retrofit.create(RemoveWalletService::class.java)

        var call = removewalletService.removeWallet(JSONObject(json))

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("response", response.body().toString())
            }
        })

    }

    fun editWallet(id: String, name : String, amount : Int){

        Log.i("response", "edit")

        var newWallet = Wallet()

        newWallet.id = id
        newWallet.name = name
        newWallet.amount = amount
        newWallet.account = "Income"

        var json = Gson().toJson(newWallet)


        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var editWalletService : EditWalletService = retrofit.create(EditWalletService::class.java)

        var call = editWalletService.editWalletService(JSONObject(json))

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                Log.i("response", response.body().toString())
            }


        })

    }
}
