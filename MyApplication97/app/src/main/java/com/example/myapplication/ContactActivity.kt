package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


                var id = intent.extras.getString("id")
                var date = intent.extras.getString("date")
                var name = intent.extras.getString("name")

                idData.setText(id)
                idDate.setText(date)
                idName.setText(name)

                idData.setOnClickListener {

                    val call = Intent(Intent.ACTION_DIAL)
                    call.setData(Uri.parse("tel:$id"))
                    startActivity(call)
                }

    }
}
