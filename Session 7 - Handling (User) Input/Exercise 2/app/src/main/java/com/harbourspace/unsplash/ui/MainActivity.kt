package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.api.UnsplashApiProvider
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.cb.UnsplashResult
import com.harbourspace.unsplash.ui.exercises.ExerciseActivity
import com.harbourspace.unsplash.ui.theme.Purple80
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private val unsplashViewModel: UnsplashViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            UnsplashTheme {

                val items = unsplashViewModel.items.observeAsState()
                unsplashViewModel.fetchImages()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(id = R.string.app_name))
                            },
                            actions = {
                                IconButton(onClick = {
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            ExerciseActivity::class.java
                                        )
                                    )
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_edit_square),
                                        contentDescription = stringResource(id = R.string.description_exercises)
                                    )
                                }
                            })
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(it)
                    ) {
                        items(items.value ?: emptyList()) { image ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { openDetailsActivity(image) }
                            ) {

                                val painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(image.urls.regular)
                                        .build()
                                )

                                Image(
                                    painter = painter,
                                    contentDescription = stringResource(id = R.string.description_image_preview),
                                    contentScale = ContentScale.Crop
                                )

                                when (painter.state) {
                                    is AsyncImagePainter.State.Success -> {
                                        //Do nothing
                                    }

                                    is AsyncImagePainter.State.Loading -> {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.DarkGray),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_download),
                                                contentDescription = stringResource(id = R.string.description_image_preview),
                                                colorFilter = ColorFilter.tint(Color.Blue)
                                            )
                                        }
                                    }

                                    else -> {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.DarkGray),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_error),
                                                contentDescription = stringResource(id = R.string.description_image_preview),
                                                colorFilter = ColorFilter.tint(Color.Red)
                                            )
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Text(image.user.name)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    image.description?.let { description ->
                                        Text(
                                            text = description,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openDetailsActivity(item: UnsplashItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, item)
        startActivity(intent)
    }
}