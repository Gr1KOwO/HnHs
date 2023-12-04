package com.example.lessnon3_igor.presentation.ui.product

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.get
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.FragmentProductBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductSize
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.exception.getError
import com.example.lessnon3_igor.presentation.ui.order.OrderProduct
import com.example.lessnon3_igor.presentation.ui.product.details.ProductDetailsAdapter
import com.example.lessnon3_igor.presentation.ui.product.image.ProductImageCollectionAdapter
import com.example.lessnon3_igor.presentation.ui.product.image.ProductPreviewImageCollectionAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductFragment: Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val args: ProductFragmentArgs by navArgs()

    private lateinit var productImageCollectionAdapter: ProductImageCollectionAdapter
    private lateinit var productPreviewImageCollectionAdapter: ProductPreviewImageCollectionAdapter
    private lateinit var productDetailsAdapter: ProductDetailsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ProductViewModel::class,
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
        binding = FragmentProductBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.silver_chalice)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadProducts()
        back()
        setupProductImagePager()
        setupProductImagePreviews()
        setupProductDetails()
        setupScrollListener()
    }

    private fun onLoadProducts()
    {
        viewModel.productState.observe(viewLifecycleOwner){ response ->
            when (response) {
                is ResponseStates.Success -> {
                    binding.progressContainerProduct.succsess()
                    binding.buttonBuy.isVisible=true
                    product(response.data)
                }
                is ResponseStates.Loading -> {
                    binding.progressContainerProduct.replay()
                }
                is ResponseStates.Failure -> {
                    binding.progressContainerProduct.error(response.e.getError().toString())
                    binding.buttonBuy.isVisible=false
                    binding.layoutProductDetails.root.isVisible = false
                }
            }
        }
        viewModel.getProduct(args.id)
    }

    private fun product(product:ResponseProductDetails)
    {
        binding.topBar.title = product.title
        binding.buttonBuy.setText(binding.root.resources.getText(R.string.buy_now).toString())
        binding.buttonBuy.isVisible = true
        with(binding.layoutProductDetails) {
            textProductDescription.text = product.description
            textProductTitle.text = product.title
            textProductCategory.text = product.department
            textBadge.text = product.badge[0].value
            textPrice.text = String.format(binding.root.resources.getString(R.string.price_format),product.price)
            textBadge.backgroundTintList = ColorStateList.valueOf(
                Color
                .parseColor(product.badge[0].color))
            layoutProductSize.setEndIconOnClickListener {

                    selectSize(product.sizes)
            }

            setFragmentResultListener("sizeResult") { _, bundle ->
                val selectedSize = bundle.getString("selectedSize")
                textProductSize.setText(selectedSize)
            }
        }

        binding.buttonBuy.setOnClickListener {
            if (binding.layoutProductDetails.textProductSize.text.isNullOrEmpty()){
                 Snackbar.make(
                    requireView(), resources.getString(R.string.no_size),
                    Snackbar.LENGTH_SHORT)
                .show()
            }
            else buyClick(product)
        }

        setupAdapters(product)
    }

    private val onViewPagerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            try {
                with(binding.layoutProductDetails.recyclerProductPreviews) {
                    smoothScrollToPosition(position)
                    get(viewModel.selectedImageItemIndex.value).isSelected = false
                    get(position).isSelected = true
                }
                viewModel.selectImageItem(position)
            } catch (_: Exception) {
            }
        }
    }

    private fun setupScrollListener() {
        val nestedScrollView = binding.nestedScrollView
        val materialToolbar = binding.topBar
        val titleTextView = binding.layoutProductDetails.textProductTitle
        val titleTextViewObserver = titleTextView.viewTreeObserver
        titleTextViewObserver.addOnPreDrawListener {
            val titleRect = Rect()
            titleTextView.getGlobalVisibleRect(titleRect)

            if (titleRect.bottom <= 0 || titleRect.top >= nestedScrollView.height) {
                // Если titleTextView не виден внутри NestedScrollView, отображаем его в toolbar
                materialToolbar.title = titleTextView.text

            } else {
                materialToolbar.title = ""
            }
            true
        }
    }
    private fun setupProductImagePager() {
        productImageCollectionAdapter = ProductImageCollectionAdapter()
        binding.layoutProductDetails.pagerProductImage.registerOnPageChangeCallback(
            onViewPagerPageChangeCallback
        )
        binding.layoutProductDetails.pagerProductImage.adapter = productImageCollectionAdapter
    }

    private fun setupProductImagePreviews() {
        productPreviewImageCollectionAdapter = ProductPreviewImageCollectionAdapter(
            onClick = { position ->
                try {
                    with(binding.layoutProductDetails) {
                        pagerProductImage.currentItem = position
                        recyclerProductPreviews[viewModel.selectedImageItemIndex.value].isSelected =
                            false
                        recyclerProductPreviews[position].isSelected =
                            true
                    }
                    viewModel.selectImageItem(position)
                } catch (_: Exception) {
                }
            }
        )
        with(binding.layoutProductDetails.recyclerProductPreviews) {
            adapter = productPreviewImageCollectionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupProductDetails() {
        productDetailsAdapter = ProductDetailsAdapter()
        with(binding.layoutProductDetails.recyclerProductDetails) {
            adapter = productDetailsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }

    private fun back()
    {
        binding.topBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapters(productDetails: ResponseProductDetails) {
        val imagesList = mutableListOf<String>()
            .apply {
                addAll(productDetails.images)
                repeat(3 - productDetails.images.size) {
                    add("")
                }
            }
        productImageCollectionAdapter.submitList(imagesList)
        productDetailsAdapter.submitList(productDetails.details)
        productPreviewImageCollectionAdapter.submitList(imagesList)
    }

    private fun selectSize(sizes: List<ResponseProductSize>){
        val sizesArray = sizes.toTypedArray()
        val action = ProductFragmentDirections.actionFragmentProductToSizeBottomSheetFragment(sizesArray)
        findNavController().navigate(action)
    }
    private fun buyClick(product: ResponseProductDetails)
    {
        val productToOrder = OrderProduct(
            product.id,
            product.title,
            product.department,
            product.price.toString(),
            product.preview,
            binding.layoutProductDetails.textProductSize.text.toString()
        )
        val action = ProductFragmentDirections.actionFragmentProductToFragmentOrder(productToOrder)
        findNavController().navigate(action)
    }
}