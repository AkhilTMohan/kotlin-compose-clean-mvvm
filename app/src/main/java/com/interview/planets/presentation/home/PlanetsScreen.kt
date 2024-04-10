package com.interview.planets.presentation.home

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.interview.components.FullScreenLoader
import com.interview.components.ListItemLoader
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.components.PlanetItem

@Preview
@Composable
fun PlanetsScreen(
    uiState: MainViewModel.BaseUIState? = null
) {
    uiState?.planets?.collectAsLazyPagingItems()?.let { lazyPagingItems ->
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Error -> {
                LoadList(lazyPagingItems = lazyPagingItems)
            }

            is LoadState.Loading -> {
                FullScreenLoader()
            }

            else -> {
                LoadList(lazyPagingItems)
            }
        }
    }
}

@Composable
fun LoadList(lazyPagingItems: LazyPagingItems<Planet>) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.index },
            contentType = lazyPagingItems.itemContentType { "contentType" }
        ) { index ->
            PlanetItem(lazyPagingItems[index])

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
