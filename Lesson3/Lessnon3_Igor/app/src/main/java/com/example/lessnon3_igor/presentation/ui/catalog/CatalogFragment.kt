package com.example.lessnon3_igor.presentation.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lessnon3_igor.databinding.FragmentCatalogBinding

class CatalogFragment: Fragment()
{
    lateinit var binding: FragmentCatalogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCatalogBinding.inflate(inflater, container, false)
        return  binding.root
    }
}