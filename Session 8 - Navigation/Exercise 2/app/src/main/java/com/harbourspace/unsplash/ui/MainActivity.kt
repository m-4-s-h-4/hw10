package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.data.UnsplashItem
import com.harbourspace.unsplash.ui.exercises.ExerciseActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

private enum class Tab(@StringRes val tab: Int) {
    HOME(R.string.main_tab_images),
    COLLECTIONS(R.string.main_tab_collections)
}

class MainActivity : ComponentActivity() {

    private val unsplashViewModel: UnsplashViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unsplashViewModel.fetchImages()
        unsplashViewModel.fetchCollections()

        setContent {

            UnsplashTheme {
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

                    val selected = remember { mutableStateOf(0) }

                    Column(
                        modifier = Modifier.padding(it)
                    ) {

                        val actions = listOf(Tab.HOME, Tab.COLLECTIONS)
                        TabRow(
                            selectedTabIndex = selected.value,
                            modifier = Modifier.height(48.dp),
                            backgroundColor = Color.Transparent,
                            indicator = @Composable { tabPositions: List<TabPosition> ->
                                TabRowDefaults.Indicator(
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[selected.value]),
                                    color = Color.White
                                )
                            }
                        ) {
                            actions.forEachIndexed { index, tab ->
                                Tab(
                                    selected = selected.value == index,
                                    onClick = { selected.value = index }
                                ) {
                                    Text(
                                        text = stringResource(id = Tab.values()[index].tab)
                                    )
                                }
                            }
                        }

                        when(selected.value) {
                            Tab.HOME.ordinal -> {
                                MainContent(
                                    unsplashViewModel = unsplashViewModel,
                                    openDetailsActivity = { item -> openDetailsActivity(item) }
                                )
                            }

                            Tab.COLLECTIONS.ordinal -> {
                                CollectionsContent(
                                    unsplashViewModel = unsplashViewModel
                                )
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