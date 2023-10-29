package com.example.lessnon3_igor.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.presentation.ui.signin.SignInFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fitContentView()

        supportFragmentManager.commit {
            add<SignInFragment>(R.id.container)
        }
    }


    private fun fitContentView()
    {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}