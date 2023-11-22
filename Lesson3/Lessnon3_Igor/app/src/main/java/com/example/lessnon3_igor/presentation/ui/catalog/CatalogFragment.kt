package com.example.lessnon3_igor.presentation.ui.catalog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.FragmentCatalogBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.exception.getError
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
class CatalogFragment: Fragment()
{
    private lateinit var binding: FragmentCatalogBinding
    private val productAdapter = ProductAdapter(onClick=::onProductClick)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        CatalogViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.smalt)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onLoadProducts()
    }

    private fun error(error:String)
    {
        binding.errorView.isVisible = true
        binding.errorView.error(error)
        binding.errorView.setOnRefreshClickListener {
            onLoadProducts()
            binding.errorView.replay()
        }
    }

    private fun onLoadProducts()
    {
        binding.errorView.isVisible = false
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.recyclerView.adapter = productAdapter

        viewModel.exampleLiveData.observe(viewLifecycleOwner){ response ->
            when (response) {
                is ResponseStates.Success -> {
                    binding.errorView.succsess()
                    binding.recyclerView.isVisible = true
                    productAdapter.submitList(response.data)
                }
                is ResponseStates.Loading -> {
                    binding.errorView.replay()
                }
                is ResponseStates.Failure -> {
                    error(response.e.getError().toString())
                }
            }
        }
        viewModel.getProducts(20, 0)
    }

    private fun onProductClick(productId:String)
    {
        val action = CatalogFragmentDirections.actionFragmentCatalogToFragmentProduct(productId)
        findNavController().navigate(action)
    }
}