package com.example.lessnon3_igor.presentation.ui.catalog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessnon3_igor.databinding.FragmentCatalogBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
class CatalogFragment: Fragment()
{
    private lateinit var binding: FragmentCatalogBinding
    private val productAdapter = ProductAdapter()

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
        // Используем View Binding для получения объекта привязки
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.recyclerView.adapter = productAdapter

        // Наблюдение за изменениями в LiveData
        viewModel.exampleLiveData.observe(viewLifecycleOwner){ response ->
            when (response) {
                is ResponseStates.Success -> {
                    binding.recyclerView.isVisible = true
                    binding.loading.isVisible = false
                    productAdapter.submitList(response.data.data)
                }
                is ResponseStates.Loading -> {
                    // Show loading state, hide RecyclerView
                    binding.recyclerView.isVisible = false
                    binding.loading.isVisible = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.loading.isVisible = false
                    }, 4000)
                }
                is ResponseStates.Failure -> {
                    Log.e("CatalogFragment", "Error: ${response.e.message}")
                    Toast.makeText(requireContext(), "Error: ${response.e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Вызываем метод загрузки продуктов
        viewModel.getProducts("20", "0")
    }
}