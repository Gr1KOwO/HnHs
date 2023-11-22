package com.example.lessnon3_igor.presentation.ui.product.image

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.ProductImageItemBinding
class ProductImageCollectionViewHolder(private val binding: ProductImageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUrl: String) {
        val cornerRadius =
            this.itemView.resources.getDimension(R.dimen.product_image_item_corner_radius)
                .toInt()
        Glide.with(binding.imageProduct)
            .load(imageUrl)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(cornerRadius)
                )
            )
            .placeholder(R.drawable.standart_product_image_item)
            .into(binding.imageProduct)
    }
}