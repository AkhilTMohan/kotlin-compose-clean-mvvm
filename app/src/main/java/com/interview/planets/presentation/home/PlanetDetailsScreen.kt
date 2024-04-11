package com.interview.planets.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.interview.components.FullScreenLoader
import com.interview.planets.R
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.components.FilmRow
import com.interview.planets.presentation.home.components.ResidentsPager

/**
 *
 *Planet details screen includes all details of the planet. This screen will fetch details from
 * server if data available with server, if no data available or network fail it will show the
 * db data available.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailsScreen(
    baseUIState: MainViewModel.BaseUIState,
    updateBaseUIState: (MainViewModel.BaseUIState) -> Unit,
    fetchPlanetDataFromServer: (Planet?) -> Unit,
    onBackPressed: () -> Unit
) {

    DisposableEffect(key1 = Unit) {
        onDispose {
            updateBaseUIState(
                baseUIState.copy(
                    data = null,
                    planetData = null,
                    isError = null
                )
            )
        }

    }
    if (baseUIState.data !is Planet) {
        onBackPressed()
    } else {
        if (baseUIState.planetData == null && baseUIState.isError == null) {
            fetchPlanetDataFromServer((baseUIState.data as? Planet))
            FullScreenLoader("Fetching ${(baseUIState.data as? Planet)?.name}'s Details..")
        }
    }


    BackHandler {
        onBackPressed()
    }
    if (baseUIState.planetData != null) {
        val planet = baseUIState.planetData
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = planet?.name ?: stringResource(R.string.na)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                contentPadding = it
            ) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Name: ${planet?.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Rotation Period: ${planet?.rotationPeriod}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Orbital Period: ${planet?.orbitalPeriod}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Diameter: ${planet?.diameter}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Climate: ${planet?.climate}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Gravity: ${planet?.gravity}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Terrain: ${planet?.terrain}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Surface Water: ${planet?.surfaceWater}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Population: ${planet?.population}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Created: ${planet?.created}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Edited: ${planet?.edited}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.residents_),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    ResidentsPager(residentUrls = planet?.residents)
                }

                // Films
                item {
                    Text(
                        text = stringResource(R.string.featured_in_films),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    FilmRow(filmUrls = planet?.films)
                }
            }
        }
    }
}
