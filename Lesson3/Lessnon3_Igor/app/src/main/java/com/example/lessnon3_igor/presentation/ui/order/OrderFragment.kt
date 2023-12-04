package com.example.lessnon3_igor.presentation.ui.order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.OrderLayoutBinding
import com.example.lessnon3_igor.presentation.MapActivity
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class OrderFragment:Fragment() {
    private lateinit var binding:OrderLayoutBinding
    private var activityMapLauncher: ActivityResultLauncher<Intent>? = null
    private var price:Int?=null
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        Locale.getDefault())

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: OrderFragmentArgs by navArgs()

    private val viewModel by createViewModelLazy(
        OrderViewModel::class,
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
        binding = OrderLayoutBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.smalt)
        activityMapLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK){
                binding.houseText.setText(it.data?.getStringExtra("address") ?: "")
            }
        }
        binding.topBar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        binding.apply {
            productTitle.text =resources.getString(R.string.title,product.size,product.title)
            productDepartment.text = product.department
            Glide.with(imageView)
                .load(product.preview)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(resources
                            .getDimension(R.dimen.corner_radius).toInt())
                    )
                ).into(imageView)
            price = product.price.toInt()
            btnBuy.setText(resources.getString(R.string.buy_order,
                (price!! * viewModel.getQuantity()).toString()))
            quantity.text=viewModel.getQuantity().toString()
        }
        Observer()
        clickListeners()
    }

    private fun Observer(){
        viewModel.orderLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseStates.Success -> {
                        val createdDeliveryDate = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        Snackbar.make(
                            binding.root,
                            resources.getString(
                                R.string.success_snackBar_order,
                                response.data.number.toString(),
                                createdDeliveryDate.format(response.data.createdDelivery)
                            ),
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(resources.getColor(R.color.dark_blue))
                            .show()
                    binding.btnBuy.setStateData()
                    binding.btnBuy.setText(resources.getString(R.string.buy_order,
                        (price!! * viewModel.getQuantity()).toString()))

                    findNavController().popBackStack()
                }
                is ResponseStates.Failure -> {
                    binding.btnBuy.setStateData()
                    binding.btnBuy.setText(resources.getString(R.string.buy_order,
                        (price!! * viewModel.getQuantity()).toString()))
                    Snackbar.make(
                        binding.root,
                        binding.root.resources.getString(R.string.error_snackBar_order),
                        Snackbar.LENGTH_LONG).show()
                }
                is ResponseStates.Loading -> {
                    binding.btnBuy.setStateLoading()
                }
            }
        }

        viewModel.quantityLiveData.observe(viewLifecycleOwner) { quantity ->
            binding.quantity.text = quantity.toString()
            val total = args.product.price.toInt() * quantity
            binding.btnBuy.setText(resources.getString(R.string.buy_order, total.toString()))
        }
            binding.minus.setOnClickListener {
                viewModel.setQuantity(viewModel.getQuantity() - 1)
        }
            binding.plus.setOnClickListener {
                viewModel.setQuantity(viewModel.getQuantity() + 1)
            }
    }


    private fun clickListeners()
    {
        binding.btnBuy.setOnClickListener {
            if (isFieldsValid()) {
                val inputFormatNew = SimpleDateFormat("dd MMMM", Locale("ru", "RU"))
                inputFormatNew.timeZone = TimeZone.getTimeZone("UTC")

                val parsedDate = inputFormatNew.parse(binding.deliveryDateText.text.toString())
                parsedDate?.let { date ->
                    val outputFormat = inputFormat
                    outputFormat.timeZone = TimeZone.getTimeZone("UTC")

                    val formattedDate = outputFormat.format(date)

                    val formattedDateAsDate = outputFormat.parse(formattedDate)

                    viewModel.executeOrder(
                        args.product.id,
                        binding.houseText.text.toString(),
                        binding.apartmentText.text.toString(),
                        formattedDateAsDate,
                        args.product.size,
                        viewModel.getQuantity()
                    )
                }
            }
        }

        binding.deliveryDateLayout.setEndIconOnClickListener {
            binding.deliveryDateText.setText(SimpleDateFormat("d MMMM", Locale("ru", "RU"))
                .format(Date()))
        }

        binding.houseLayout.setEndIconOnClickListener {
            activityMapLauncher!!.launch(MapActivity.createStartIntent(requireActivity()))
        }
    }

    private fun isFieldsValid(): Boolean {
        var isFieldsValid = true
        if (binding.houseText.text.isNullOrEmpty()) {
            binding.houseLayout.isErrorEnabled = true
            binding.houseLayout.error = getString(R.string.error_input)
            isFieldsValid = false
        }
        if (binding.apartmentText.text.isNullOrEmpty()) {
            binding.apartmentLayout.isErrorEnabled = true
            binding.apartmentLayout.error = getString(R.string.error_input)
            isFieldsValid = false
        }
        if (binding.deliveryDateText.text.isNullOrEmpty()) {
            binding.deliveryDateLayout.isErrorEnabled = true
            binding.deliveryDateLayout.error = getString(R.string.error_input)
            isFieldsValid = false
        }
        return isFieldsValid
    }
}