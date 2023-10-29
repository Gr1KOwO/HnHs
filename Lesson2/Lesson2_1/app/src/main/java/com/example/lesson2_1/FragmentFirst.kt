package com.example.lesson2_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class FragmentFirst: Fragment(R.layout.fragment_first)
{
    private val args:FragmentFirstArgs? by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textview:TextView = view.findViewById(R.id.text_v)
        textview.text = args?.textMain
        view.findViewById<Button>(R.id.btn_Next).setOnClickListener{
            val action = FragmentFirstDirections.actionFragmentFirstToFragmentSecond(textview.text.toString())
            findNavController().navigate(action)
        }
    }


}