package com.example.lessnon3_igor.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lessnon3_igor.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class VariableBottomSheetFragment: BottomSheetDialogFragment() {
    private var _binding:FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: VariableBottomSheetFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: Adapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val variableRecyclerView = binding.root
        variableRecyclerView.adapter = adapter
        val list = mutableListOf<String>()
        for (i in args.variable){
            list.add(i)
        }
        adapter.submitList(list)
        adapter.setOnItemClickListener {variant ->
            val result = Bundle().apply {
                putString(ProfileSettingsFragment.selectedVariant, variant)
                putString(ProfileSettingsFragment.keyAction,args.action)
            }
            setFragmentResult(ProfileSettingsFragment.requestKey, result)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}