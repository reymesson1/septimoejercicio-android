package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import org.json.JSONStringer
import org.json.JSONTokener
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

//        run("https://api.github.com/users/reymesson1")
        run("http://localhost:8082/masterandroid")

        button.setOnClickListener{

            val strLeft  = '"'
            val strRight = '"'
//            var strRes = strLeft + "" + strLeft + "" + strLeft + this.res.toString() + strRight + "" + strRight + "" + strRight
//            var strRes = strLeft + "" + strLeft + "" + strLeft + "" + '{"test":"test"}' +"" + strRight + "" + strRight + "" + strRight
//            val json = JSONObject(strRes)
//            var json = JSONObject("""{_id":"5c38c844952f9b0648f98ed0","id":"1547225156520","date":"2019-01-11","name":"Fernando Garcia}""")
//            val json = JSONObject("""{"item":[{"id":1547225154475,"firstname":"Fernando Garcia","telefono":"8092223660","item":"BLUSA","itemDetail":[],"development":"Lavar y Prensa","quantity":"1","project":100}],"comments":[],"_id":"5c38c844952f9b0648f98ed0","id":"1547225156520","date":"2019-01-11","name":"Fernando Garcia","project":100,"agregado":0,"desc":0,"itbis":18,"grandTotal":118,"fechaentrega":"Lunes 14/01/2019","horaentrega":"06:00 PM","balance":118,"pending":-382,"current":500,"tipopago":"tarjeta","ncf":"A00000000000001","status":"Pagado","__v":0}""")
//              val json = JSONObject(strLeft+""+strLeft+""+strLeft+{"current":"500","tipopago":"tarjeta","ncf":"A00000000000001","status":"Pagado","__v":0}+strRight+""+strRight+""+strRight)


//            val json = JSONObject(strLeft+""+strLeft+""+strLeft+{"test":"test"}+strLeft+""+strLeft+""+strLeft+)
//            val json = JSONObject(strLeft + " test " + strRight)
//            val json = JSONObject( this.res.toString() )

//            var username = json.getString("id")

//            textView.setText(json.toString())

//            if(username == email.text.toString()) {
//
//                var intent = Intent(this@MainActivity, SecondActivity::class.java)
//                intent.putExtra("main_activity_data", email.text.toString())
//                startActivity(intent)
//
//            }else{
//
//                textView.text = "Email or Password Invalid"
//
//            }
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
