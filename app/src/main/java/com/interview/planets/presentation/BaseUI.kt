package com.interview.planets.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.interview.planets.core.helpers.PlanetConstants.HOME
import com.interview.planets.core.helpers.PlanetConstants.PLANET_DETAILS
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.home.PlanetDetailsScreen
import com.interview.planets.presentation.home.PlanetListScreen

@Composable
fun BaseUI(
    baseUIState: MainViewModel.BaseUIState,
    updateBaseUIState: (MainViewModel.BaseUIState) -> Unit,
    fetchPlanetDataFromServer: (Planet?) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HOME) {
        composable(HOME) {
            PlanetListScreen(baseUIState, navigateToDetails = {
                updateBaseUIState(
                    baseUIState.copy(
                        data = it
                    )
                )
                navController.navigate(PLANET_DETAILS)
            })
        }
        composable(PLANET_DETAILS) {
            PlanetDetailsScreen(
                updateBaseUIState = updateBaseUIState,
                baseUIState = baseUIState,
                onBackPressed = {
                    updateBaseUIState(
                        baseUIState.copy(
                            data = null,
                            planetData = null,
                            isError = null
                        )
                    )
                    navController.popBackStack()
                },
                fetchPlanetDataFromServer = fetchPlanetDataFromServer
            )
        }
    }
}