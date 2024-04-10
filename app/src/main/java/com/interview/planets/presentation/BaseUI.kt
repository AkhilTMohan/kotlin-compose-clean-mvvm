package com.interview.planets.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.interview.planets.core.helpers.PlanetConstants.HOME
import com.interview.planets.presentation.screens.PlanetsScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BaseUI(
    uiState: StateFlow<MainViewModel.BaseUIState>, updateBaseUI: (MainViewModel.BaseUIState) -> Unit
) {
    val navController = rememberNavController()

    @Composable
    fun PlanetNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        NavHost(navController = navController, startDestination = HOME) {
            composable(HOME) {
                PlanetsScreen()
            }
        }
    }
}