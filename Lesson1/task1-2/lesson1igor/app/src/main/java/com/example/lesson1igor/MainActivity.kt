package com.example.lesson1igor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.openSecondAct).setOnClickListener {
            try{startActivity(SecondActivity.createIntent(this))}
            catch (e:Exception){Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()}
        }

        findViewById<Button>(R.id.openThirdAct).setOnClickListener {
            startActivity(ThirdActivity.createIntent(this))
        }
    }
}