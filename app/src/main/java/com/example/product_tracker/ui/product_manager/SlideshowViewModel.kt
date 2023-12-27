package com.example.product_tracker.ui.product_manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {
    private val mText: MutableLiveData<String?>

    init {
        mText = MutableLiveData()
        mText.value = "This is slideshow fragment"
    }

    val text: LiveData<String?>
        get() = mText
}