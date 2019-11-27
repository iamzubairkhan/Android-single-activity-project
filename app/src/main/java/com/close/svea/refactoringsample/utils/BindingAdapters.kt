package com.close.svea.refactoringsample.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, isLoading: Boolean) {
        if (isLoading)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}
