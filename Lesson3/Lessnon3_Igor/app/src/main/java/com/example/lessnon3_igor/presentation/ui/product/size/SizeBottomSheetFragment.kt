package com.example.lessnon3_igor.presentation.ui.product.size

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lessnon3_igor.databinding.FragmentBottomSheetBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductSize
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SizeBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: SizeBottomSheetFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: SizeAdapter

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
        val sizeRecyclerView = binding.root
        sizeRecyclerView.adapter = adapter
        val sizeList = mutableListOf<ResponseProductSize>()
        for (i in args.sizes){
            sizeList.add(i)
        }
        adapter.submitList(sizeList)
        adapter.setOnItemClickListener {size ->
            val result = Bundle().apply {
                putString("selectedSize", size.value)
            }
            setFragmentResult("sizeResult", result)
            findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}