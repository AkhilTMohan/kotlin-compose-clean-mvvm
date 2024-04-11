package com.interview.planets.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.interview.planets.R
import com.interview.planets.core.helpers.PlanetConstants.HOME
import com.interview.planets.core.helpers.PlanetConstants.PLANET_DETAILS
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.Response
import com.interview.planets.presentation.home.PlanetDetailsScreen
import com.interview.planets.presentation.home.PlanetListScreen

@Composable
fun BaseUI(
    baseUIState: MainViewModel.BaseUIState,
    updateBaseUIState: (MainViewModel.BaseUIState) -> Unit,
    fetchPlanetDataFromServer: (Planet?) -> Unit
) {

    CheckError(baseUIState)

    val navController = rememberNavController()
    NavHost(navController, startDestination = HOME) {
        composable(HOME) {
            PlanetListScreen(
                baseUIState = baseUIState,
                updateBaseUIState = updateBaseUIState,
                navigateToDetails = {
                    /** Resetting the state to clear the value set.
                     * This is for a safe case only. We can go with the previousBackstack-SavedState,
                     * but this is an alternative to achieve the data passing. */
                    updateBaseUIState(
                        baseUIState.copy(
                            data = it,
                            planetData = null,
                            isError = null
                        )
                    )
                    navController.navigate(PLANET_DETAILS)
                })
        }
        composable(PLANET_DETAILS, enterTransition = {
            scaleIn(
                animationSpec = tween(durationMillis = 1000)
            )
        },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(durationMillis = 1000)
                )
            }) {
                PlanetDetailsScreen(
                    updateBaseUIState = updateBaseUIState,
                    baseUIState = baseUIState,
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    fetchPlanetDataFromServer = fetchPlanetDataFromServer
                )
            }
    }
}

/** Function Will show error to the user. Currently only no internet message is handled,
 * invoke this function by using updateBaseUIState */
@Composable
fun CheckError(baseUIState: MainViewModel.BaseUIState) {
    val context = LocalContext.current
    if (baseUIState.isError == Response.ERROR_TYPE.NO_NETWORK) {
        val noInternetMessage =
            stringResource(R.string.you_are_not_connected_to_the_internet_showing_offline_data)
        LaunchedEffect(key1 = baseUIState.isError) {
            Toast.makeText(
                context,
                noInternetMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
