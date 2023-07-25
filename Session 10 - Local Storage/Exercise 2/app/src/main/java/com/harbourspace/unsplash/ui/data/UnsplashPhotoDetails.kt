package com.harbourspace.unsplash.ui.data

import com.squareup.moshi.Json

data class UnsplashPhotoDetails(
    val id: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    @Json(name = "blur_hash") val blurHash: String,
    val downloads: Int,
    val likes: Int,
    @Json(name = "liked_by_user") val likedByUser: Boolean,
    @Json(name = "public_domain") val publicDomain: Boolean,
    val description: String,
    val exif: Exif,
    val location: Location,
    val tags: List<Tag>,
)