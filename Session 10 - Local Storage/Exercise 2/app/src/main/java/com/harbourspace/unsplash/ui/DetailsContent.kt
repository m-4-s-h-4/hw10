package com.harbourspace.unsplash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.UnsplashPhotoDetails

@Composable
fun DetailsContent(
    image: UnsplashItem,
    photoDetails: UnsplashPhotoDetails?
) {
    LazyColumn {
        item {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.urls.regular)
                    .build()
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.description_image_preview)
            )
        }

        item {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
                    photoDetails?.exif?.make?.let {
                        AddImageInformation(
                            title = stringResource(id = R.string.details_camera_title),
                            subtitle = it
                        )
                    }

                }

                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
                    photoDetails?.exif?.aperture?.let {
                        AddImageInformation(
                            title = stringResource(id = R.string.details_aperture_title),
                            subtitle = it
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
                    photoDetails?.exif?.focal_length?.let {
                        AddImageInformation(
                            title = stringResource(id = R.string.details_focal_title),
                            subtitle = it
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
                    photoDetails?.exif?.exposure_time?.let {
                        AddImageInformation(
                            title = stringResource(id = R.string.details_shutter_title),
                            subtitle = it
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
//                    AddImageInformation(
//                        title = stringResource(id = R.string.details_iso_title),
//                        subtitle = photoDetails?.exif?.iso
//                    )
                }

                Column(
                    modifier = Modifier.weight(1.0f)
                ) {
                    AddImageInformation(
                        title = stringResource(id = R.string.details_dimensions_title),
                        subtitle = " ${image.height} x ${image.width}"
                    )
                }
            }
        }

        item {
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AddImageInformation(
                        title = stringResource(id = R.string.details_views_title),
                        subtitle = "-"
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AddImageInformation(
                        title = stringResource(id = R.string.details_downloads_title),
                        subtitle = "${photoDetails?.downloads}"
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AddImageInformation(
                        title = stringResource(id = R.string.details_likes_title),
                        subtitle = "${image.likes}"
                    )
                }
            }
        }
    }
}