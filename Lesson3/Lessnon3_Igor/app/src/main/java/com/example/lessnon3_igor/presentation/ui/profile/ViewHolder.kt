package com.example.lessnon3_igor.presentation.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lessnon3_igor.databinding.SizeItemBinding

class ViewHolder private constructor(
    private val binding: SizeItemBinding,
    private val onItemClickListener: ((String) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.root.text = item
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onItemClickListener: ((String) -> Unit)?): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SizeItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, onItemClickListener)
        }
    }
}