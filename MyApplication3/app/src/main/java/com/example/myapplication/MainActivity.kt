package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var client = OkHttpClient()
    var res : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var email = findViewById<EditText>(R.id.email)

        var password = findViewById<EditText>(R.id.password)

        var button = findViewById<Button>(R.id.button)

        var textView = findViewById<TextView>(R.id.textView4) as TextView

        run("https://api.github.com/users/reymesson1")

        button.setOnClickListener{

            var json = JSONObject(this.res)

            var username = json.getString("login")

            if(username == email.text.toString()) {

                var intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("main_activity_data", email.text.toString())
                startActivity(intent)

            }else{

                textView.text = "Email or Password Invalid"

            }
        }

    }

    fun loading(param: String?){

        this.res = param
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = loading(response.body()?.string())
//            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }



}
