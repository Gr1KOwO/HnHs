package com.example.lessnon3_igor.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.ErorsLayoutBinding

class ErrorLayout@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private var binding: ErorsLayoutBinding? = null

    init {
        binding = ErorsLayoutBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.erors_layout, this, true)
        )
    }

    fun error(detailError:String) = binding?.run{
        textErrorTitle.text = resources.getText(R.string.errorTitle)
        textErrorDescription.text = detailError
        layoutLoading.isVisible = false
        layoutNotice.isVisible = true
    }

    fun replay()=binding?.run{
        layoutNotice.isVisible = false
        layoutLoading.isVisible= true
    }

    fun succsess()=binding?.run {
        layoutNotice.isVisible = false
        layoutLoading.isVisible= false
    }


    fun setOnRefreshClickListener(onClick: () -> Unit) {
        binding?.buttonRefresh?.setOnClickListener { onClick() }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

}