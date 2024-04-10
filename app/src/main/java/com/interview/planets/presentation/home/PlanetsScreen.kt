package com.interview.planets.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.interview.components.FullScreenLoader
import com.interview.components.ListItemLoader
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.components.PlanetCardItem

@Preview
@Composable
fun PlanetsScreen(
    uiState: MainViewModel.BaseUIState? = null
) {
    val scrollState = rememberLazyGridState()
    uiState?.planets?.collectAsLazyPagingItems()?.let { lazyPagingItems ->
        LoadList(lazyPagingItems, scrollState)
    }
}

@Composable
fun LoadList(lazyPagingItems: LazyPagingItems<Planet>, scrollState: LazyGridState) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val itemWidth = screenWidth.div(3.5)

    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Error -> {
            LoadVerticalGrid(
                scrollState = scrollState, lazyPagingItems = lazyPagingItems, itemWidth = itemWidth
            )
        }

        is LoadState.Loading -> {
            FullScreenLoader()
        }

        else -> {
            LoadVerticalGrid(
                scrollState = scrollState, lazyPagingItems = lazyPagingItems, itemWidth = itemWidth
            )
        }
    }
}

@Composable
fun LoadVerticalGrid(
    scrollState: LazyGridState, lazyPagingItems: LazyPagingItems<Planet>, itemWidth: Double
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(1), state = scrollState
    ) {
        items(count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.index },
            contentType = lazyPagingItems.itemContentType { "contentType" }) { index ->
            PlanetCardItem(lazyPagingItems[index], itemWidth)

        }

        when (lazyPagingItems.loadState.append) {
            is LoadState.Error -> {
            }

            is LoadState.Loading -> {
                item {
                    ListItemLoader()
                }
            }

            else -> {}
        }
    }
}
