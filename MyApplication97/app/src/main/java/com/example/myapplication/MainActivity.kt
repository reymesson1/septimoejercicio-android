package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.Model.Post
import com.example.myapplication.Model.PostService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_item.view.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var list : ArrayList<Post> =  ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync{

            activityUiThread {

                Thread.sleep(2000)

                getData()

                for(i in list){




                }

            }


        }


    }

    fun getData(){

        var retrofit = Retrofit.Builder()
            .baseUrl("http://190.94.2.105:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var postService : PostService = retrofit.create(PostService::class.java)
        var call = postService.response()

        call.enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.i("error",t.toString())
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {


                var json = Gson().toJson(response.body())

                Log.i("response", json)

//                var filtered = response.body()!!.filter{ it.name == "Juan Perez" }

//                filtered.forEach{

                response.body()!!.forEach { at ->

                    var item : View = layoutInflater.inflate(R.layout.layout_item, null)

                    item.tvId.setText(at.id)
                    item.tvDate.setText(at.date)
                    item.tvName.setText(at.name)

                    item.setOnClickListener{et ->

                        var intent = Intent(this@MainActivity, ContactActivity::class.java)
                        intent.putExtra("id",(at.id))
                        intent.putExtra("date",(at.date))
                        intent.putExtra("name",(at.name))
                        startActivity(intent)
                    }

                    scContent.addView(item)
                }

            }

        })

    }
}
