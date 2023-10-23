package com.example.lesson2_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class FragmentSecond:Fragment(R.layout.fragment_second)
{
    private  val args:FragmentSecondArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edit_text = view.findViewById<EditText>(R.id.edit_text)
        edit_text.setText(args.changedText.toString())
        view.findViewById<Button>(R.id.btn_Save).setOnClickListener{
            val action = FragmentSecondDirections.actionFragmentSecondToFragmentFirst(edit_text.text.toString())
            findNavController().navigate(action)
        }
    }
}