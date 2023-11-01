package com.example.lesson4_igor

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson4_igor.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btn.setOnClickListener{
            val text = binding.editText.text.toString()
            viewModel.setText(text)
        }

        viewModel.textLiveData.observe(this){newText->
            binding.textView.text = newText
        }
    }
}