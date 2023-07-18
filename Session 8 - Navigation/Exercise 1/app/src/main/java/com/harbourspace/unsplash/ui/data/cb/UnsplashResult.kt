package com.harbourspace.unsplash.ui.data.cb

import com.harbourspace.unsplash.ui.data.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onDataFetchedFailed()
}