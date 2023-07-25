package com.harbourspace.unsplash.ui.data.cb

import com.harbourspace.unsplash.ui.data.UnsplashCollection
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.UnsplashPhotoDetails

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onCollectionsFetchedSuccess(collections: List<UnsplashCollection>)

    fun onDataFetchedFailed()

    fun onPhotoDetailsFetchedSuccess(photoDetails: UnsplashPhotoDetails)
}