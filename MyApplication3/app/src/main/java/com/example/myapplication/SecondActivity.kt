package com.example.myapplication

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var text = findViewById<TextView>(R.id.textView)
        var text2 = findViewById<TextView>(R.id.textView2)
        var text3 = findViewById<TextView>(R.id.textView3)
        var text4 = findViewById<TextView>(R.id.textView4)

        val face = Typeface.createFromAsset(assets, "fonts/fontawesome-webfont.ttf")

        text.typeface = face
        text2.typeface = face
        text3.typeface = face
        text4.typeface = face

        text.setText("\uf200")
        text2.setText("\uf044")
        text3.setText("\uf007")
        text4.setText("\uf21d")

        text.setOnClickListener{

            var intent = Intent(this@SecondActivity, MasterActivity::class.java)
            startActivity(intent)

        }





    }
}
