package com.example.lessnon3_igor.presentation.ui.catalog

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.ProductCardBinding
import com.example.lessnon3_igor.presentation.data.dto.Product
import java.util.Currency

class ProductViewHolder(private val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.textProductTitle.text = product.title
        Glide.with(binding.imageProduct)
            .load(product.preview)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(binding.root.resources.getDimension(
                R.dimen.roundedImg).toInt())))
            .into(binding.imageProduct)
        binding.textProductDepartment.text = product.department
        binding.textProductPrice.text =  String.format(binding.root.resources.getString(R.string.price_format),product.price)
        binding.textButtonBuy.setOnClickListener {
            Toast.makeText(binding.root.context,"Click on buy btn",Toast.LENGTH_LONG).show()
        }
        binding.root.setOnClickListener{
            Toast.makeText(binding.root.context,"Click on product ${product.title}}",Toast.LENGTH_LONG).show()
        }
    }
}