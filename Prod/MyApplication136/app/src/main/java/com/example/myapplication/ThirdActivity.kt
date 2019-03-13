package com.example.myapplication

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.telecom.Call
import android.util.Log
import com.example.myapplication.Model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.layout_add_wallet.view.*
import kotlinx.android.synthetic.main.layout_item.view.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        /***
         *
         *
         * Add Button to Expense
         *
         * */

        button_add_expense.setOnClickListener{

            var alertDialog = AlertDialog.Builder(this)

            var dialogView = layoutInflater.inflate(R.layout.layout_add_wallet,null)

            alertDialog.setView(dialogView)

            alertDialog.setPositiveButton("Save", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    var name = dialogView.name.text.toString()

                    var amount = dialogView.account.text.toString()

                    setWallet(name,amount)

                }

            })

            alertDialog.show()

        }

        doAsync{

            activityUiThread {

                Thread.sleep(2000)

                getExpense()
            }


        }


    }

    fun getExpense(){

        var  retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var expenseService : ExpenseService = retrofit.create(ExpenseService::class.java)

        var call = expenseService.getExpense()

        call.enqueue(object : Callback<List<Wallet>>{
            override fun onFailure(call: retrofit2.Call<List<Wallet>>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: retrofit2.Call<List<Wallet>>, response: Response<List<Wallet>>) {
                Log.i("response", response.toString())

                response.body()!!.forEach {at ->

                    var item = layoutInflater.inflate(R.layout.layout_item, null)

                    item.nameTXT.setText(at.name)

                    item.amountTXT.setText(at.amount.toString())

                    /***
                     *
                     *
                     * Edit Button from Expense
                     *
                     * */

                    item.button_edit.setOnClickListener{ it ->

                        Log.i("test","test")

                        var alertDialog = AlertDialog.Builder(this@ThirdActivity)

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
                     * Remove Button from Expense
                     *
                     * */

                    item.button_remove.setOnClickListener{

                        removeWallet(at.id)

                    }

                    scContentExpense.addView(item)

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
        newWallet.amount = -amount.toInt()
        newWallet.account = "Expense"

        var json = Gson().toJson(newWallet)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var postwalletService: PostWalletService = retrofit.create(PostWalletService::class.java)

        var call = postwalletService.setWallet(JSONObject(json))

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                Log.i("response", response.body().toString())
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
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                Log.i("response", response.body().toString())
            }
        })

    }

    fun editWallet(id: String, name : String, amount : Int){

        Log.i("response", "edit")

        var newWallet = Wallet()

        newWallet.id = id
        newWallet.name = name
        newWallet.amount = -amount
        newWallet.account = "Expense"

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
