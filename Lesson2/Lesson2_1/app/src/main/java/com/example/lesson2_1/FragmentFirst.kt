package com.example.lesson2_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class FragmentFirst: Fragment(R.layout.fragment_first)
{
    private val args:FragmentFirstArgs? by navArgs()
    private var textview:TextView?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview = view.findViewById(R.id.text_v)
        textview?.text = args?.textMain
        view.findViewById<Button>(R.id.btn_Next).setOnClickListener{
            val action = FragmentFirstDirections.actionFragmentFirstToFragmentSecond(textview?.text.toString())
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textview=null
    }
}