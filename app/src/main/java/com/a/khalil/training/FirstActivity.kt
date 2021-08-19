package com.a.khalil.training

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_activity)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username: EditText = findViewById(R.id.tv_username)
            val password: EditText = findViewById(R.id.tv_password)
            if (username.text.toString().equals("admin") && password.text.toString()
                    .equals("admin")
            ) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
}