package co.lavapp.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{

            if(editUsername.text.toString()==("admin")){

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{

                Toast.makeText(this, "Username and Password incorrect", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
