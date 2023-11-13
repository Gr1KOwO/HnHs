package com.example.lessnon3_igor.presentation.ui.catalog

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lessnon3_igor.databinding.ProductCardBinding
import com.example.lessnon3_igor.presentation.data.dto.Product

class ProductViewHolder(private val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.textProductTitle.text = product.title
        Glide.with(binding.imageProduct)
            .load(product.preview)
            .into(binding.imageProduct)
        binding.textProductDepartment.text = product.department
        binding.textProductPrice.text = product.price.toString()
        binding.textButtonBuy.setOnClickListener {

        }
        binding.root.setOnClickListener{

            Toast.makeText(binding.root.context,"Click on product ${product.id}}",Toast.LENGTH_LONG).show()
        }
    }
}