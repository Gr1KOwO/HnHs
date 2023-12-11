package com.example.lessnon3_igor.presentation.ui.product.size

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductSize
import javax.inject.Inject

class SizeAdapter @Inject constructor() : RecyclerView.Adapter<SizeViewHolder>() {
    private var onItemClickListener: ((ResponseProductSize) -> Unit)? = null
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    fun submitList(sizes: List<ResponseProductSize>) {
        differ.submitList(sizes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        return SizeViewHolder.from(parent,onItemClickListener)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setOnItemClickListener(listener: (ResponseProductSize) -> Unit) {
        this.onItemClickListener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseProductSize>() {
            override fun areItemsTheSame(oldItem: ResponseProductSize, newItem: ResponseProductSize): Boolean =
                oldItem.value == newItem.value

            override fun areContentsTheSame(oldItem: ResponseProductSize, newItem: ResponseProductSize): Boolean =
                oldItem == newItem
        }
    }
}