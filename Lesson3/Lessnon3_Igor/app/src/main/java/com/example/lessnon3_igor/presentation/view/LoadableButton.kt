package com.example.lessnon3_igor.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.ViewLoadableButtonBinding

class LoadableButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private var binding: ViewLoadableButtonBinding? = null

    init {
        binding = ViewLoadableButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.view_loadable_button, this, true)
        )
    }

    fun setStateLoading() = binding?.run {
        buttonLoadable.text = ""
        progressBar.isVisible = true
    }

    fun setStateData() = binding?.run {
        buttonLoadable.text = resources.getText(R.string.sign_in)
        progressBar.isVisible = false
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding?.buttonLoadable?.setOnClickListener(l)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    fun setText(text: String) {
        binding?.buttonLoadable?.text = text
    }

}