package com.example.lecture2_3

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class Fragment_a:Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Fragment_a", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Fragment_a", "onCreate")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment_a", "onCreateView")
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frameLayout = view.findViewById<FrameLayout>(R.id.fram_a_layout)
        val anim: AnimationDrawable? = frameLayout.background as AnimationDrawable?
        anim?.setEnterFadeDuration(2500)
        anim?.setExitFadeDuration(5000)
        anim?.start()

        Log.d("Fragment_a", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Fragment_a", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment_a", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment_a", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragment_a", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment_a", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment_a", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment_a", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Fragment_a", "onDetach")
    }
}