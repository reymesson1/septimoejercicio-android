package co.lavapp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.lavapp.main.MainActivity.Companion.globalVar
import kotlinx.android.synthetic.main.activity_fifth.*

class FifthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)


        if(globalVar=="ES"){

            switch1.isChecked = true
        }

        switch1.setOnClickListener{


            Log.i("response", globalVar)

            if(globalVar=="EN"){
                globalVar = "ES"
            }else{
                globalVar = "EN"
            }

        }


    }
}
