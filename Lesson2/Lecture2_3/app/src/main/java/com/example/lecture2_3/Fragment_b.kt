package com.example.lecture2_3

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Fragment_b:Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Fragment_b", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Fragment_b", "onCreate")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment_b", "onCreateView")
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counterText: TextView = view.findViewById(R.id.counter_text)

        view.findViewById<Button>(R.id.plus_button).setOnClickListener {
            var counterValue = counterText.text.toString().toInt()
            counterText.text = (++counterValue).toString()
        }

        view.findViewById<Button>(R.id.minus_button).setOnClickListener {
            var counterValue = counterText.text.toString().toInt()
            if (counterValue > 0) counterText.text = (--counterValue).toString()
        }

        Log.d("Fragment_b", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Fragment_b", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment_b", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment_b", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragment_b", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment_b", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment_b", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment_b", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Fragment_b", "onDetach")
    }
}