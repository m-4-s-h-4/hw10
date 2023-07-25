package com.harbourspace.unsplash.ui.api

import com.harbourspace.unsplash.ui.data.UnsplashCollection
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.UnsplashSearch
import com.harbourspace.unsplash.ui.data.UnsplashPhotoDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "ngak5Lv2ZsDvYfnAJjyMP0mnV23pWs5hcvOBXceV3Wc"

interface UnsplashApi {

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos")
    fun fetchPhotos(): Call<List<UnsplashItem>>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("search/photos")
    fun searchPhotos(@Query(value = "query") keyword: String): Call<UnsplashSearch>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("collections")
    fun fetchCollections(): Call<List<UnsplashCollection>>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos/{id}")
    fun getPhotoDetails(@Path("id") id: String): Call<UnsplashPhotoDetails>
}