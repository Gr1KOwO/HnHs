package com.example.lessnon3_igor.presentation.ui.product.image

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.ProductImagePreviewItemBinding
class ProductPreviewImageCollectionViewHolder (private val binding: ProductImagePreviewItemBinding) :
RecyclerView.ViewHolder(binding.root){
fun bind(imageUrl: String) {
    val cornerRadius = this.itemView.resources.getDimension(R.dimen.product_item_preview_item_corner_size).toInt()
    Glide.with(binding.imageProductPreview)
        .load(imageUrl)
        .transform(
            MultiTransformation(
                CenterCrop(),
                RoundedCorners(cornerRadius)
            )
        )
        .placeholder(R.drawable.standart_product_image_item)
        .into(binding.imageProductPreview)
    }
}