package com.example.lessnon3_igor.presentation.ui.product.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.lessnon3_igor.databinding.ProductImagePreviewItemBinding

class ProductPreviewImageCollectionAdapter(private val onClick: (index: Int) -> Unit) :
    ListAdapter<String, ProductPreviewImageCollectionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPreviewImageCollectionViewHolder =
        ProductPreviewImageCollectionViewHolder(
            ProductImagePreviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    override fun onBindViewHolder(holder: ProductPreviewImageCollectionViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick(position) }
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = currentList.size
    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}