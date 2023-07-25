package com.harbourspace.unsplash.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harbourspace.unsplash.ui.api.UnsplashApiProvider
import com.harbourspace.unsplash.ui.data.UnsplashCollection
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.UnsplashPhotoDetails
import com.harbourspace.unsplash.ui.data.cb.UnsplashResult
import com.harbourspace.unsplash.ui.repository.UnsplashRepository

private const val TAG = "UnsplashViewModel"
class UnsplashViewModel(
    private val repository: UnsplashRepository
) : ViewModel(), ViewModelProvider.Factory, UnsplashResult {

    private val _items = MutableLiveData<List<UnsplashItem>>()
    val items: MediatorLiveData<List<UnsplashItem>> = MediatorLiveData<List<UnsplashItem>>().apply {
        addSource(_items) { this.value = _items.value }
        addSource(repository.items) { this.value =repository.items.value }
    }

    private val _collections = MutableLiveData<List<UnsplashCollection>>()
    val collections: LiveData<List<UnsplashCollection>> = _collections

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnsplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnsplashViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

    fun searchImages(keyword: String) {
        provider.searchImages(keyword, this)
    }

    private val provider by lazy {
        UnsplashApiProvider()
    }

    fun fetchImages() {
        _loading.value = true
        provider.fetchImages(this)
    }

    fun combineLatestData(
        networkResult: LiveData<List<UnsplashItem>>,
        databaseResult: LiveData<List<UnsplashItem>>
    ): List<UnsplashItem> {

        val network = networkResult.value
        val database = databaseResult.value

        if (network == null || database == null) {
            return emptyList()
        }

        return network.plus(database)
    }

    fun fetchCollections() {
        provider.fetchCollections(this)
    }

    fun getPhotoDetails(id:String) {
        provider.getPhotoDetails(id,this)
    }


    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")

        for (image in images) {
            repository.insert(image)
        }

        _items.value = images
        _loading.value = false
    }

    override fun onCollectionsFetchedSuccess(collections: List<UnsplashCollection>) {
        Log.d(TAG, "onCollectionsFetchedSuccess | Received ${collections.size} collections")
        _collections.value = collections
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
        _loading.value = false
    }

    private val _photoDetails = MutableLiveData<UnsplashPhotoDetails>()
    val photoDetails: LiveData<UnsplashPhotoDetails> = _photoDetails

//    override fun onPhotoDetailsFetchedSuccess(photoDetails: UnsplashPhotoDetails) {
//        Log.d(TAG, "onPhotoDetailsFetchedSuccess | Received photo details")
//        _photoDetails.value = photoDetails
//    }

    override fun onPhotoDetailsFetchedSuccess(photoDetails: UnsplashPhotoDetails) {
        _photoDetails.value = photoDetails
    }

}
