package com.example.lessnon3_igor.presentation.ui.product.details

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.recyclerview.widget.RecyclerView
import com.example.lessnon3_igor.databinding.ProductDetailsItemBinding

class ProductDetailsViewHolder(private val binding: ProductDetailsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(detailsText: String) {
        val dotString = SpannableString(detailsText)
        dotString.setSpan(
            BulletSpan(16),
            0,
            detailsText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.root.text = dotString
    }
}