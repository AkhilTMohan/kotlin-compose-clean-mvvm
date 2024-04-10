package com.interview.planets.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.interview.planets.core.helpers.PlanetConstants.HOME
import com.interview.planets.presentation.home.PlanetsScreen

@Composable
fun BaseUI(
    uiState: MainViewModel.BaseUIState,
    updateBaseUI: (MainViewModel.BaseUIState) -> Unit,
    getPlanets: () -> Unit
) {

    NavHost(navController = rememberNavController(), startDestination = HOME) {
        composable(HOME) {
            PlanetsScreen(uiState)
        }
    }
}