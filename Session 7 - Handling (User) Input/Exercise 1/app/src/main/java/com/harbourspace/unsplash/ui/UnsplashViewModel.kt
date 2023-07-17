package com.harbourspace.unsplash.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harbourspace.unsplash.ui.api.UnsplashApiProvider
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.cb.UnsplashResult

private const val TAG = "UnsplashViewModel"
class UnsplashViewModel : ViewModel(), UnsplashResult {

    private val _items = MutableLiveData<List<UnsplashItem>>()
    val items: LiveData<List<UnsplashItem>> = _items

    private val provider by lazy {
        UnsplashApiProvider()
    }

    fun fetchImages() {
        provider.fetchImages(this)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
        _items.value = images
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
    }
}