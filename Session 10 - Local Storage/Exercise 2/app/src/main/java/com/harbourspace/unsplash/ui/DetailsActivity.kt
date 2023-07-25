package com.harbourspace.unsplash.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.data.UnsplashPhotoDetails
import com.harbourspace.unsplash.ui.repository.AppPreferences
import com.harbourspace.unsplash.ui.repository.UnsplashRepository
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(private val repository: UnsplashRepository) : ViewModel() {
    private val _photoDetails = MutableLiveData<UnsplashPhotoDetails?>()
    val photoDetails: LiveData<UnsplashPhotoDetails?> get() = _photoDetails

    fun getPhotoDetails(id: String) {
        viewModelScope.launch {
//            val result = repository.getPhotoDetails(id)
//            _photoDetails.value = result
        }
    }
}
class DetailsActivity : ComponentActivity() {

    // Assuming you have a ViewModelFactory or Hilt/Dagger for ViewModel injection
    private val unsplashViewModel: UnsplashViewModel by viewModels {
        UnsplashViewModel((application as AppApplication).repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val image = intent.extras!!.get(EXTRA_IMAGE) as UnsplashItem

        // Get photo details
        unsplashViewModel.getPhotoDetails(image.id)

        setContent {
            UnsplashTheme(
                darkTheme = AppPreferences(this).isDarkTheme()
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.description_back)
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {

                        // Here you observe the LiveData from the ViewModel
                        val photoDetails = unsplashViewModel.photoDetails.observeAsState(null)

                        DetailsContent(image = image, photoDetails = photoDetails.value)
                    }
                }
            }
        }
    }
}

@Composable
fun AddImageInformation(
    title: String,
    subtitle: String
) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = subtitle ?: "-",
        fontSize = 15.sp
    )
}
