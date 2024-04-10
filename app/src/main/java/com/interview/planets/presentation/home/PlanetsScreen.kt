package com.interview.planets.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.components.PlanetItem

@Preview
@Composable
fun PlanetsScreen(
    uiState: MainViewModel.BaseUIState? = null
) {
    val context = LocalContext.current
    uiState?.planets?.collectAsLazyPagingItems()?.let { lazyPagingItems ->
        when (lazyPagingItems.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                LoadList(lazyPagingItems = lazyPagingItems)
            }

            is LoadState.Loading -> { // Loading UI
                
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

        when (lazyPagingItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                //Toast.makeText(context, "End Of Page", Toast.LENGTH_LONG).show()
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }
    }
}
