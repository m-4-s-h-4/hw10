package com.harbourspace.unsplash.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.ui.data.UnsplashItem

@Composable
fun MainScreen(
    unsplashViewModel: UnsplashViewModel,
    openDetailsActivity: (UnsplashItem) -> Unit
) {
    val selected = remember { mutableStateOf(0) }

    Column {

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