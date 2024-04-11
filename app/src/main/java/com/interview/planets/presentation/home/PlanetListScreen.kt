package com.interview.planets.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.interview.components.FullScreenLoader
import com.interview.components.ListItemLoader
import com.interview.components.R
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.Response
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.components.PlanetCardItem

@Preview
@Composable
fun PlanetListScreen(
    baseUIState: MainViewModel.BaseUIState? = null,
    navigateToDetails: ((Planet) -> Unit)? = null,
    updateBaseUIState: ((MainViewModel.BaseUIState) -> Unit)? = null
) {
    val scrollState = rememberLazyGridState()
    baseUIState?.planets?.collectAsLazyPagingItems()?.let { lazyPagingItems ->
        LoadList(
            baseUIState = baseUIState,
            updateBaseUIState = updateBaseUIState,
            lazyPagingItems = lazyPagingItems,
            scrollState = scrollState,
            navigateToDetails = navigateToDetails
        )
    }
}

@Composable
fun LoadList(
    lazyPagingItems: LazyPagingItems<Planet>,
    scrollState: LazyGridState,
    navigateToDetails: ((Planet) -> Unit)? = null,
    baseUIState: MainViewModel.BaseUIState,
    updateBaseUIState: ((MainViewModel.BaseUIState) -> Unit)?
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val itemWidth = screenWidth.div(3.5)

    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Error -> {
            updateBaseUIState?.invoke(
                baseUIState.copy(
                    isError = Response.ErrorTypes.ERROR
                )
            )
            LoadVerticalGrid(
                scrollState = scrollState,
                lazyPagingItems = lazyPagingItems,
                itemWidth = itemWidth,
                navigateToDetails = navigateToDetails
            )
        }

        is LoadState.Loading -> {
            FullScreenLoader(stringResource(R.string.planets_loading))
        }

        else -> {
            LoadVerticalGrid(
                scrollState = scrollState,
                lazyPagingItems = lazyPagingItems,
                itemWidth = itemWidth,
                navigateToDetails = navigateToDetails
            )
        }
    }
}

@Composable
fun LoadVerticalGrid(
    scrollState: LazyGridState, lazyPagingItems: LazyPagingItems<Planet>, itemWidth: Double,
    navigateToDetails: ((Planet) -> Unit)? = null
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(1), state = scrollState
    ) {
        items(count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.index },
            contentType = lazyPagingItems.itemContentType {}) { index ->
            PlanetCardItem(lazyPagingItems[index], itemWidth) {
                lazyPagingItems[index]?.let { navigateToDetails?.invoke(it) }
            }
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
