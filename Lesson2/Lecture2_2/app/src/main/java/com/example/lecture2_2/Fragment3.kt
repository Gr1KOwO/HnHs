package com.example.lecture2_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class Fragment3:Fragment(R.layout.fragment3)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.pressed_btn)
        btn.setOnClickListener {
            btn.isPressed != btn.isPressed
        }

        view.findViewById<Button>(R.id.btn_1).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frameLayout,Fragment1()).commit()
        }
        view.findViewById<Button>(R.id.btn_2).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frameLayout,Fragment2()).commit()
        }
        view.findViewById<Button>(R.id.btn_3).setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.frameLayout,Fragment3()).commit()
        }
    }
}