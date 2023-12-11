package com.example.lessnon3_igor.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.lessnon3_igor.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fitContentView()
    }
    private fun fitContentView()
    {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}