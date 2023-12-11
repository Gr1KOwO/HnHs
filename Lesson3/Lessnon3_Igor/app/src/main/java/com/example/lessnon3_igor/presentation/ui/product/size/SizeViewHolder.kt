package com.example.lessnon3_igor.presentation.ui.product.size

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.SizeItemBinding
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductSize

class SizeViewHolder private constructor(
    private val binding: SizeItemBinding,
    private val onItemClickListener: ((ResponseProductSize) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResponseProductSize) {
        if (item.isAvailable) {
            binding.root.text = item.value
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        } else {
            binding.root.alpha = 0.5F
            binding.root.hint = item.value
            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.color.seashell)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onItemClickListener: ((ResponseProductSize) -> Unit)?): SizeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SizeItemBinding.inflate(layoutInflater, parent, false)
            return SizeViewHolder(binding, onItemClickListener)
        }
    }
}