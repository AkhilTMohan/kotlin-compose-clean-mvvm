package com.interview.planets.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

/***
 * Home UI of the application which will show all the planets in server, if no data available at
 * the first load it will show a retry button. If the data once fetched it will be
 * cashed on the local db. On next launch if the user don't have valid internet connection then the
 * cached data will be displayed.
 *
 * It uses pagination to fetch the data from the server*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlanetListScreen(
    baseUIState: MainViewModel.BaseUIState? = null,
    navigateToDetails: ((Planet) -> Unit)? = null,
    updateBaseUIState: ((MainViewModel.BaseUIState) -> Unit)? = null,
    fetchAllPlanetsFromServer: (() -> Unit)? = null
) {
    val scrollState = rememberLazyGridState()
    Column {
        TopAppBar(title = {
            Text(
                text = "PLANETS",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        })
        baseUIState?.planets?.collectAsLazyPagingItems()?.let { lazyPagingItems ->
            LoadList(
                baseUIState = baseUIState,
                updateBaseUIState = updateBaseUIState,
                lazyPagingItems = lazyPagingItems,
                scrollState = scrollState,
                navigateToDetails = navigateToDetails,
                fetchAllPlanetsFromServer = fetchAllPlanetsFromServer
            )
        }
    }
}

@Composable
fun LoadList(
    lazyPagingItems: LazyPagingItems<Planet>,
    scrollState: LazyGridState,
    navigateToDetails: ((Planet) -> Unit)? = null,
    baseUIState: MainViewModel.BaseUIState,
    updateBaseUIState: ((MainViewModel.BaseUIState) -> Unit)?,
    fetchAllPlanetsFromServer: (() -> Unit)? = null
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
                navigateToDetails = navigateToDetails,
                fetchAllPlanetsFromServer = fetchAllPlanetsFromServer
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
                navigateToDetails = navigateToDetails,
                fetchAllPlanetsFromServer = fetchAllPlanetsFromServer
            )
        }
    }
}

@Composable
fun LoadVerticalGrid(
    scrollState: LazyGridState, lazyPagingItems: LazyPagingItems<Planet>, itemWidth: Double,
    navigateToDetails: ((Planet) -> Unit)? = null,
    fetchAllPlanetsFromServer: (() -> Unit)? = null
) {
    if (lazyPagingItems.itemCount != 0) {
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
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sorry, No Data Available.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
            Button(onClick = { fetchAllPlanetsFromServer?.invoke() }) {
                Text(text = stringResource(com.interview.planets.R.string.retry))
            }
        }
    }
}
