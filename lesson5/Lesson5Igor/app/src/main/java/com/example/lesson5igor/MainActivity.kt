package com.example.lesson5igor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson5igor.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            val customViewState = savedInstanceState.getParcelable<CustomViewState>("customViewState")
            binding.customView.onRestoreInstanceState(customViewState)
        }
        val random = Random()
        val randomValues = mutableListOf<Float>()
        for (i in 0 until 9) {
            val randomValue = random.nextInt(101).toFloat()
            randomValues.add(randomValue)
        }
        binding.customView.setColumnHeights(randomValues)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val customViewState = binding.customView.onSaveInstanceState() as CustomViewState
        outState.putParcelable("customViewState", customViewState)
    }
}