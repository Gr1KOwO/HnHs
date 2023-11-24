package com.example.lessnon3_igor.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.presentation.data.repository.PreferenceStorage
import com.example.lessnon3_igor.presentation.ui.signin.SignInFragmentDirections
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